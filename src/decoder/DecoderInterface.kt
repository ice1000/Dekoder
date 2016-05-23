package decoder

import com.sun.media.sound.JavaSoundAudioClip
import data.DatabaseManager
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

interface DecoderInterface {

    var path: String
    var echoer: Echoer

    fun play() {
        var sound = JavaSoundAudioClip(File(path).inputStream())
        sound.play()
    }

    fun save() {
        val db = DatabaseManager()
        db.write(path)
    }
}
