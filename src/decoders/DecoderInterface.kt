package decoders

import utils.DSInputStreamReader
import utils.Echoer
import javax.media.Player

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface(echoer: Echoer) : Echoer by echoer {

    protected abstract var reader: DSInputStreamReader

    abstract var path: String
    protected var player: Player? = null

    abstract fun init()

    abstract fun play()

    abstract fun stop()

    abstract fun pause()

    abstract fun getTotalTime(): Long
}
