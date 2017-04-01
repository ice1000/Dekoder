package decoders

import utils.DSInputStreamReader
import utils.Echoer

/**
 * Created by ice1000 on 2016/5/22.
 *
 * @author ice1000
 */

abstract class DecoderInterface(echoer: Echoer) : Echoer by echoer {

    protected abstract var reader: DSInputStreamReader
    abstract var path: String

    abstract fun onCreate()

    abstract fun onStart()

    abstract fun onStop()

    abstract fun onPause()

    open fun getTotalTime(): Long = 60
}
