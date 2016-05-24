package decoder

import com.sun.media.sound.JavaSoundAudioClip
import utils.Echoer
import java.io.File
import java.io.IOException

/**
 * @author ice1000
 *
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface(echoer: Echoer): Echoer by echoer {

    abstract var path: String
    var sound : JavaSoundAudioClip? = null

    fun init() {
        try {
            sound = JavaSoundAudioClip(File(path).inputStream())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun play() = sound?.play()

    fun stop() = sound?.stop()

    abstract fun getTime(): Long
}
