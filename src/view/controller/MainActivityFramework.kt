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

    private val PAUSE = "Pause"
    private val STOP = "Stop"
    private val PLAY = "Play"
    private val OPEN = "Open"

    abstract var dekoder: DecoderInterface?

    protected var manager = DatabaseManager()

    val chooser: FileChooser
        get() = FileChooser()

    private var progressThread = ProgressThread { setProgress(it) }

    private var progress = 0L

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
            if (progress > 0)
                progressThread.storedTime = progress
            progressThread.start()
            try {
                dekoder?.play()
            } catch (e: IllegalStateException) {
                return
            }
            getPlayButton().text = PAUSE
        } else if (PAUSE == getPlayButton().text) {
            // TODO: implement pausing
            dekoder?.stop()
            progress = progressThread.storedTime
            progressThread.running = false
            progressThread.join()
            getPlayButton().text = PLAY
        } else {
            // OPEN
            openFile()
        }
    }

    open protected fun stopPlaying() {
        progress = 0
        setProgress(progress)
        dekoder?.stop()
        progressThread.running = false
        progressThread.join()
        getPlayButton().text = PLAY
    }

    /**
     * @param i now time
     * this method will automatically divide i by the total time.
     * so just input the time which is already spent.
     */
    abstract protected fun setProgress(i: Long)

    abstract protected fun getPlayButton(): JFXButton

    /**
     * get a printer to show information in the properties field..
     * in the very beginning I used inner fun in this framework.
     * but to save code, I chose to implement this in the java interface.
     * because java8 supports lambda : )
     */
    abstract protected fun propertiesPrinter(): Echoer

    /**
     * the same as that one ↑
     * get a printer to show information in the files field.
     */
    abstract protected fun filesPrinter(): Echoer

    abstract protected fun openFile()

    /**
     * select a decoder to decode the music file.
     * currently only WAVDecoder can work.
     */
    protected fun choose(filePath: String): DecoderInterface {
        val p = propertiesPrinter()
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
        file.parentFile.list().forEach {
            if(it.endsWith("wav"))
                filesPrinter().echo(it)
        }
        // stop the latest opened file
        dekoder?.stop()
        // echo the name of this file
        propertiesPrinter().echo(file.name)
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