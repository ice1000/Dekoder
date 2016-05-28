package decoder

import utils.DSInputStreamReader
import utils.Echoer
import java.io.File
import javax.media.Manager
import javax.media.Player

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface(echoer: Echoer) : Echoer by echoer {

    protected abstract var reader: DSInputStreamReader

    abstract var path: String
    protected var player: Player? = null

    open fun init() {
        player = Manager.createRealizedPlayer(File(path).toURL())
    }

    open fun play() = player?.start()

    open fun stop() {
        player?.stop()
        player?.close()
    }

    abstract fun getTotalTime(): Long
}
