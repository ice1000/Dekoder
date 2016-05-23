package data

import java.io.File
import java.util.*

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/23.
 */

class DatabaseManager {

    private val saveFile = "save.db"
    private val a = File(saveFile)

    constructor() {
        if(!a.exists())
            a.createNewFile()
    }

    fun read(): ArrayList<String> {
        return a.readLines() as ArrayList<String>
    }

    /**
     * have got a built-in repeat check.
     */
    fun write(name: String) {
        var b = read()
        for(s in b)
            if(name.equals(s))
                return
        a.writeText(name + '\n')
    }
}