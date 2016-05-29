package decoders

import utils.DSInputStreamReader
import utils.Echoer

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

abstract class DecoderInterface(echoer: Echoer) : Echoer by echoer {

    protected abstract var reader: DSInputStreamReader

    abstract var path: String

    abstract fun onCreate()

    abstract fun onStart()

    abstract fun onStop()

    abstract fun onPause()

    abstract fun getTotalTime(): Long
}
