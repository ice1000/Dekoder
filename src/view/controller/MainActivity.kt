package view.controller;

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.stage.FileChooser
import java.io.File

/**
 * I really don`t know how to use java`s annotation in Kotlin.
 * So I use inheritance.
 */

class MainActivity : BaseActivity {

    constructor() {
        playButton.styleClass.add("fab")
        fileButton.styleClass.add("fab")
        helpButton.styleClass.add("fab")
    }

    private var file: File? = null
    private val chooser = FileChooser()

    @FXML
    fun playMusic(event: ActionEvent) {
    }

    @FXML
    fun openFile(event: ActionEvent) {
        propertiesList.items.removeAll()
        file = chooser.showOpenDialog(window.scene.window)
        Dekoder(file?.path as String)
    }

    @FXML
    fun openHelp(event: ActionEvent) {
    }

}
