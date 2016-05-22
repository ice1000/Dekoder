package view.gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/21.
 */

class View: Application() {
    override fun start(primaryStage: Stage?) {
//        Thread(Runnable {
//            SVGGlyphLoader.loadGlyphsFont(View::class.java.getResourceAsStream("/src/view/res/kotlin.png"),"kotlin.png");
//        })
        val parent = FXMLLoader.load<Parent>(View::class.java.getResource("activity_main.fxml"))

        val scene = Scene(parent, 800.0, 600.0)
        primaryStage?.initStyle(StageStyle.DECORATED)
        primaryStage?.scene = scene
        primaryStage?.title = "Dekoder by ice1000"
        primaryStage?.isResizable = false
        primaryStage?.show()

    }

    fun onCreate() {
        launch(View::class.java)
    }
}