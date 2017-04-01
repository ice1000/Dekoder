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

class View : Application() {
    override fun start(primaryStage: Stage?) {
//        Thread(Runnable {
//            SVGGlyphLoader.loadGlyphsFont(View::class.java.getResourceAsStream("/src/view/res/kotlin.png"),"kotlin.png");
//        })
        val parent = FXMLLoader.load<Parent>(View::class.java.getResource("activity_main.fxml"))

        val scene = Scene(parent, 800.0, 600.0)
        primaryStage?.let {
            it.initStyle(StageStyle.DECORATED)
            it.scene = scene
            it.title = "Dekoder by ice1000"
            it.isResizable = false
            it.show()
        }

    }

    fun onCreate() = launch(View::class.java)
}