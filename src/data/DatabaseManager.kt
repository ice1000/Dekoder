package data

import java.io.File

/**
 * @author ice1000
 * d
 * Created by ice1000 on 2016/5/23.
 */

class DatabaseManager {

    private val saveFile = "save.db"
    private var file: File = File(saveFile)

    constructor() {
    }

    fun read(): List<String> = file.readLines()

    /**
     * have got a built-in repeat check.
     */
    fun write(path: String) {
        file = File(saveFile)
        if (!file.exists())
            file.createNewFile()
        var b = read()
        if (path !in b)
            file.writeText(path + "\n")
        println("path is $path")
        b.forEach { file.writeText(it + "\n") }
    }
}
