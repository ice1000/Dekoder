package decoder

import utils.Echoer

/**
 * @author ice1000
 * TODO: implement mp3 decoding
 * Created by ice1000 on 2016/5/24.
 */

class MP3Decoder: DecoderInterface {
    override fun getTotalTime(): Long {
        return 60
    }

    override var path: String

    constructor(path: String, echoer: Echoer) : super(echoer) {
        this.path = path
    }
}