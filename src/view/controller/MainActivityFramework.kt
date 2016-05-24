package view.controller

import com.jfoenix.controls.JFXButton
import data.DatabaseManager
import decoder.DecoderInterface
import decoder.MP3Decoder
import decoder.WAVDecoder
import javafx.stage.FileChooser
import utils.Echoer

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
    protected var manager: DatabaseManager = DatabaseManager()

    val chooser: FileChooser
        get() = FileChooser()
    private var stop = true

    open fun openGitHub() = Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " +
                        "https://github.com/ice1000/Dekoder")

    open protected fun playMusic() {
        if (PLAY == getPlayButton().text) {
            setProgress(0.0)
            dekoder.play()
            getPlayButton().text = STOP
        } else {
            dekoder.stop()
            getPlayButton().text = PLAY
            setProgress(0.0)
        }
    }

    abstract protected fun setProgress(i: Double)

    abstract fun getPlayButton(): JFXButton
    abstract fun printer(): Echoer

    protected fun choose(filePath: String): DecoderInterface? {
        val p = printer()
        if (filePath.endsWith("wav"))
            return WAVDecoder(filePath, p)
        else if (filePath.endsWith("mp3"))
            return MP3Decoder(filePath, p)
        else
            return null
    }

    protected fun openFile(path: String) {
        manager.write(path)
        dekoder = choose(path) ?: WAVDecoder(path, printer())
        dekoder.init()
    }

}