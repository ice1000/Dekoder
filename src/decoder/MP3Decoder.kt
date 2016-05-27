package decoder

import utils.DSInputStreamReader
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/26.
 */

open class MP3Decoder : DecoderInterface {

    override var reader: DSInputStreamReader
    override var path: String
    private var size: Long = 0

    override fun init() = Unit

    override fun play() = Unit

    override fun stop() = Unit

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
        flag1@ while (true) {
            var flag = reader.readToString()
            when (flag) {
                "TEXT" -> echo("text author:")
                "URL " -> echo("url:")
                "TCOP" -> echo("copyright:")
                "TOPE" -> echo("original artist:")
                "TCOM" -> echo("music author:")
                "TDAT" -> echo("date:")
                "TPE4" -> echo("translator:")
                "TPE3" -> echo("zhihui:")
                "TPE2" -> echo("band:")
                "TPE1" -> echo("artist:")
                "TSIZ" -> echo("size:")
                "TBLE" -> echo("zhuanji")
                "TIT2" -> echo("title:")
                "TIT3" -> echo("sub title")
                "TCON" -> echo("style:")
                "AENC" -> echo("encode tech:")
                "TBPM" -> echo("jiepai per second:")
                "TSSE" -> echo("encoder:")
                "TYER" -> println("year:")
                "COMM" -> println("comment:")
                else -> break@flag1
            }
            // same part: every data is in the same format
            echo("${reader.readToStringButSkipFirst(2,
                    reader.readToIntFromLast())}")
        }
    }
}