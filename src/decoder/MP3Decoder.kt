package decoder

import utils.Echoer

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/26.
 */

class MP3Decoder : DecoderInterface {
    override fun init() {
        throw UnsupportedOperationException()
    }

    override fun play(): Unit? {
        throw UnsupportedOperationException()
    }

    override fun stop(): Unit? {
        throw UnsupportedOperationException()
    }

    override fun getTotalTime(): Long {
        return 60
    }

    override var path: String

    constructor(path: String, echoer: Echoer) : super(echoer) {
        this.path = path
    }

}