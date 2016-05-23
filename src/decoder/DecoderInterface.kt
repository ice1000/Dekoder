package decoder

import data.DatabaseManager
import utils.Echoer

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

interface DecoderInterface {

    var name: String
    var echoer: Echoer

    fun play()

    fun save() {
        val db = DatabaseManager()
        db.write(name)
    }
}
