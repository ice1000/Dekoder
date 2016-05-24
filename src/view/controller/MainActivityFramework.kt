package view.controller

import com.jfoenix.controls.JFXButton
import data.DatabaseManager
import decoder.DecoderInterface
import decoder.MP3Decoder
import decoder.WAVDecoder
import javafx.stage.FileChooser
import utils.Echoer
import java.io.File
import kotlin.properties.Delegates

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val STOP = "Stop"
    private val PLAY = "Play"
    private var thread: Thread = Thread()
    private var startTime: Long = 0
    protected var nowTime: Long = 0
        set

    abstract var dekoder: DecoderInterface

    protected var file: File? = null
    protected var manager: DatabaseManager = DatabaseManager()

    val chooser: FileChooser
        get() = FileChooser()

    open fun openGitHub() {
        Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " +
                        "https://github.com/ice1000/Dekoder"
        )
    }

    open protected fun playMusic() {
//        if (manager == null)
//            manager = DatabaseManager()
        startTime = System.currentTimeMillis()
        nowTime = startTime
        thread = Thread(Runnable {
            while (true)
                nowTime = System.currentTimeMillis() - startTime
        })
        if (PLAY == getPlayButton().text) {
            thread.start()
            dekoder.play()
            getPlayButton().text = STOP
        } else {
            thread.interrupt()
            dekoder.stop()
            getPlayButton().text = PLAY
        }
    }
    abstract fun getPlayButton(): JFXButton
    abstract fun printer() : Echoer

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
        dekoder = choose(path)?: WAVDecoder(path, printer())
        dekoder.init()
    }

}