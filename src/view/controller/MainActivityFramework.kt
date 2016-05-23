package view.controller

import com.jfoenix.controls.JFXButton
import decoder.DecoderInterface
import javafx.event.ActionEvent
import javafx.stage.FileChooser

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val STOP = "Stop"
    private val PLAY = "Play"

    abstract var dekoder: DecoderInterface?

    val chooser: FileChooser
        get() = FileChooser()

    open fun openGitHub() {
        Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " +
                        "https://github.com/ice1000/Dekoder"
        )
    }

    open protected fun playMusic(event: ActionEvent) {
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