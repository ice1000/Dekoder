package view

import MainDecoder
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

/**
 * @author ice1000
 * Created by asus1 on 2016/5/21.
 */

class View: Application() {
    override fun start(primaryStage: Stage?) {
        val decoder = MainDecoder("./raw.wav")
        println(decoder.name)

        val parent = FXMLLoader.load<Parent>(this.javaClass.getResource("activity_main.fxml"))

        val scene = Scene(parent, 600.0, 480.0)
        primaryStage?.initStyle(StageStyle.DECORATED)
        primaryStage?.scene = scene
        primaryStage?.title = "Dekoder by ice1000"
    }
}