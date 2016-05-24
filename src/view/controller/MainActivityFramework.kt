package view.controller

import com.jfoenix.controls.JFXButton
import data.DatabaseManager
import decoder.DecoderInterface
import javafx.stage.FileChooser
import java.io.File
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val STOP = "Stop"
    private val PLAY = "Play"
    private var thread: Thread? = null
    private var startTime: Long = 0
    protected var nowTime: Long = 0
    set

    abstract var dekoder: DecoderInterface?

    protected var file: File? = null
    protected var manager: DatabaseManager by Delegates.notNull<DatabaseManager>()
    val chooser: FileChooser
        get() = FileChooser()

    open fun openGitHub() {
        Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " +
                        "https://github.com/ice1000/Dekoder"
        )
    }

    open protected fun playMusic() {
        startTime = System.currentTimeMillis()
        nowTime = startTime
        if (thread == null) thread = Thread(Runnable {
            while (true)
                nowTime = System.currentTimeMillis() - startTime
        })
        if (PLAY == getPlayButton().text) {
            dekoder?.play()
            getPlayButton().text = STOP
        } else {
            dekoder?.stop()
            getPlayButton().text = PLAY
        }
    }

    abstract fun getPlayButton(): JFXButton
}