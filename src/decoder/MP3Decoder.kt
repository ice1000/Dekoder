package decoder

import utils.DSInputStreamReader
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/26.
 */

class MP3Decoder : DecoderInterface {

    override var reader: DSInputStreamReader
    override var path: String
    private var size: Long = 0

    override fun init() = Unit

    override fun play(): Unit? {
        throw UnsupportedOperationException()
    }

    override fun stop(): Unit? {
        throw UnsupportedOperationException()
    }

    override fun getTotalTime(): Long {
        return 60
    }

    constructor(path: String, echoer: Echoer) : super(echoer) {
        this.path = path
        reader = DSInputStreamReader(File(path))
        if ("ID3".equals(reader.read(3, 3))) {
            echo("ID3 not found")
            return
        }
        // ======================================================== 6
        size = reader.readToLong(4)
        echo("size = $size")
        // ======================================================== 10
        outer@ while(true) {
            var flag = reader.readToString()
            // when a information is useless enough, it will be ignored.
            when(flag) {
                "TEXT" -> echo("text author: ${reader.readToString(4, 2)}")
                "URL " -> echo("url: ${reader.readToString(4, 2)}")
                "TCOP" -> echo("copyright: ${reader.readToString(4, 2)}")
                "TOPE" -> echo("original artist: ${reader.readToString(4, 2)}")
                "TCOM" -> echo("music author: ${reader.readToString(4, 2)}")
                "TDAT" -> echo("date: ${reader.readToString(4, 2)}")
                "TPE4" -> echo("translator: ${reader.readToString(4, 2)}")
                "TPE3" -> echo("zhihui: ${reader.readToString(4, 2)}")
                "TPE2" -> echo("band: ${reader.readToString(4, 2)}")
                "TPE1" -> echo("artist: ${reader.readToString(4, 2)}")
//                "TYER" -> echo("year: ${reader.readToString(4, 2)}")
                "TSIZ" -> echo("size: ${reader.readToString(4, 2)}")
                "TBLE" -> echo("zhuanji: ${reader.readToString(4, 2)}")
                "TIT2" -> echo("title: ${reader.readToString(4, 2)}")
                "TIT3" -> echo("sub title: ${reader.readToString(4, 2)}")
                "TCON" -> echo("style: ${reader.readToString(4, 2)}")
                "AENC" -> echo("encode tech: ${reader.readToString(4, 2)}")
                "TBPM" -> echo("jiepai per second: ${reader.readToString(4, 2)}")
                "AENC" -> echo("encode tech: ${reader.readToString(4, 2)}")
                else -> break@outer
            }
        }
    }

}