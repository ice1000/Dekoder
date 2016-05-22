package view.controller

import javafx.stage.FileChooser

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

open class MainActivityFramework {

    val chooser: FileChooser
        get() = FileChooser()

    open fun openGitHub() {
        Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " +
                        "https://github.com/ice1000/Dekoder"
        )
    }
}