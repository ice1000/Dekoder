package org.ice1000.dekoder.gui

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
//		Thread(Runnable {
//			SVGGlyphLoader.loadGlyphsFont(View::class.java.getResourceAsStream("/src/view/res/kotlin.png"), "kotlin.png");
//		})
		val parent = FXMLLoader.load<Parent>(View::class.java.getResource("./activity_main.fxml"))

		val theScene = Scene(parent, 800.0, 600.0)
		primaryStage?.run {
			initStyle(StageStyle.DECORATED)
			scene = theScene
			title = "Dekoder by ice1000"
			isResizable = false
			show()
		}
	}

	fun onCreate() = launch(View::class.java)
}

fun main(args: Array<String>) = View().onCreate()
