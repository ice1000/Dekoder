package view.controller

import com.jfoenix.controls.JFXButton
import data.DatabaseManager
import decoder.APEDecoder
import decoder.DecoderInterface
import decoder.MP3Decoder
import decoder.WAVDecoder
import javafx.stage.FileChooser
import utils.Echoer
import utils.ProgressThread

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val STOP = "Stop"
    private val PLAY = "Play"

    abstract var dekoder: DecoderInterface

    //    protected var file: File? = null
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
            dekoder.play()
            getPlayButton().text = STOP
        } else {
            dekoder.stop()
            progressThread.running = false
            progressThread.join()
            getPlayButton().text = PLAY
        }
    }

    /**
     * @param i now time
     * this method will automatically divide i by the total time.
     * so just input the time which is already spent.
     */
    abstract fun setProgress(i: Double)

    abstract fun getPlayButton(): JFXButton

    /**
     * get a printer.
     * in the very beginning I used inner fun in this framework.
     * but to save code, I chose to implement this in the java interface.
     * because java8 supports lambda : )
     */
    abstract fun printer(): Echoer

    /**
     * select a decoder to decode the music file.
     * currently only WAVDecoder can work.
     */
    protected fun choose(filePath: String): DecoderInterface? {
        val p = printer()
        if (filePath.endsWith("wav"))
            return WAVDecoder(filePath, p)
        else if (filePath.endsWith("mp3"))
            return MP3Decoder(filePath, p)
        else if (filePath.equals("ape"))
            return APEDecoder(filePath, p)
        else
            return null
    }

    protected fun openFile(path: String) {
        manager.write(path)
        dekoder = choose(path) ?: WAVDecoder(path, printer())
        dekoder.init()
    }

}