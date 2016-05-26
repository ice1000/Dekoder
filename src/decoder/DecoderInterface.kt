package decoder

import com.sun.media.sound.JavaSoundAudioClip
import utils.DSInputStreamReader
import utils.Echoer
import java.io.File
import java.io.IOException

/**
 * @author ice1000
 *
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface(echoer: Echoer): Echoer by echoer {

    protected abstract var reader: DSInputStreamReader

    abstract var path: String

    abstract fun init()

    abstract fun play(): Unit?

    abstract fun stop(): Unit?

    abstract fun getTotalTime(): Long
}
