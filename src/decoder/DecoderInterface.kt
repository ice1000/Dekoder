package decoder

import com.sun.media.sound.JavaSoundAudioClip
import data.DatabaseManager
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface {

    abstract var path: String
    abstract var echoer: Echoer
    var sound : JavaSoundAudioClip? = null

    fun init() {
        sound = JavaSoundAudioClip(File(path).inputStream())
    }

    fun play() {
        sound?.play()
    }

    fun stop() {
        sound?.stop()
    }

    abstract fun getTime(): Long
}
