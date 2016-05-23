package decoder

import utils.Echoer

/**
 * @author ice1000
 * TODO: implement mp3 decoding
 * Created by ice1000 on 2016/5/24.
 */

class MP3Decoder: DecoderInterface {
    override var path: String
    override var echoer: Echoer

    constructor(path: String, echoer: Echoer) : super() {
        this.path = path
        this.echoer = echoer
    }
}