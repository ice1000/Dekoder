package utils

import data.DatabaseManager

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

interface DecoderInterface {

    var name: String

    fun play()

    fun save() {
        val db = DatabaseManager()
        db.write(name)
    }
}