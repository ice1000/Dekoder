package view.controller

import com.jfoenix.controls.JFXButton
import data.DatabaseManager
import decoder.DecoderInterface
import decoder.WAVDecoder
import javafx.stage.FileChooser
import utils.Echoer
import utils.ProgressThread
import java.io.File

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val STOP = "Stop"
    private val PLAY = "Play"
    private val OPEN = "Open"

    abstract var dekoder: DecoderInterface?

    protected var manager = DatabaseManager()

    val chooser: FileChooser
        get() = FileChooser()

    private var progressThread = ProgressThread { setProgress(it) }

    open fun openGitHub() = Runtime.getRuntime().exec(
            "rundll32 url.dll,FileProtocolHandler " +
                    "https://github.com/ice1000/Dekoder")

    /**
     * I think I`d better find a way to use less thread instances
     * this might take too much memory.
     * but I can only trust JVM`s GC now. : )
     */
    open protected fun playMusic() {
        if (PLAY == getPlayButton().text) {
            progressThread = ProgressThread { setProgress(it) }
            progressThread.start()
            try {
                dekoder?.play()
            } catch (e: IllegalStateException) {

            }
            getPlayButton().text = STOP
        } else if(STOP == getPlayButton().text) {
            dekoder?.stop()
            progressThread.running = false
            progressThread.join()
            getPlayButton().text = PLAY
        } else {
            // OPEN
            openFile()
        }
    }

    /**
     * @param i now time
     * this method will automatically divide i by the total time.
     * so just input the time which is already spent.
     */
    abstract protected fun setProgress(i: Double)

    abstract protected fun getPlayButton(): JFXButton

    /**
     * get a printer.
     * in the very beginning I used inner fun in this framework.
     * but to save code, I chose to implement this in the java interface.
     * because java8 supports lambda : )
     */
    abstract protected fun printer(): Echoer

    /**
     * set the name of the sound
     */
    abstract protected fun setTitleText(string: String)

    abstract protected fun openFile()

    /**
     * select a decoder to decode the music file.
     * currently only WAVDecoder can work.
     */
    protected fun choose(filePath: String): DecoderInterface {
        val p = printer()
        return if (filePath.endsWith("wav"))
            WAVDecoder(filePath, p)
//        else if (filePath.endsWith("mp3"))
//            MP3Decoder(filePath, p)
//        else if (filePath.endsWith("mid"))
//            MIDIDecoder(filePath, p)
//        else if (filePath.endsWith("ape"))
//            APEDecoder(filePath, p)
        else
            WAVDecoder(filePath, p)
    }

    open protected fun openFile(file: File) {
        val path = file.path
        // stop the latest opened file
        dekoder?.stop()
        // echo the name of this file
        printer().echo(file.name)
        // write this file`s path to the database
        manager.write(path)
        // give a value to dekoder, to choose a type
        dekoder = choose(path)
        try {
            dekoder?.init()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    open protected fun initialize() {
        try {
            openFile(File(manager.read()[0]))
        } catch (ignored: IndexOutOfBoundsException) {
            getPlayButton().text = OPEN
        }
    }

}