package data

import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/23.
 */

class DatabaseManager() {

    val saveFile = "save.db"
    val out = FileInputStream(saveFile).bufferedReader()
    val `in` = FileOutputStream(saveFile).bufferedWriter()

    fun read(): ArrayList<String> {
        return out.readLines() as ArrayList<String>
    }

    fun write(name: String) {
        `in`.newLine()
        `in`.append(name)
    }
}