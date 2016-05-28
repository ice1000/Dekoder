package decoder

import utils.DSInputStreamReader
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/26.
 */

class APEDecoder : DecoderInterface {

    override var reader: DSInputStreamReader
    override var path: String

    override fun getTotalTime(): Long {
        return 60
    }

    constructor(path: String, echoer: Echoer) : super(echoer) {
        this.path = path
        reader = DSInputStreamReader(File(path))
    }
}