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
            // when a information is useless enough, it will be ignored.
            // ignored information will be println() into console
            when (flag) {
                "TEXT" -> echo("text author: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "URL " -> echo("url: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TCOP" -> echo("copyright: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TOPE" -> echo("original artist: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TCOM" -> echo("music author: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TDAT" -> echo("date: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TPE4" -> echo("translator: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TPE3" -> echo("zhihui: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TPE2" -> echo("band: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TPE1" -> echo("artist: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TYER" -> println("year: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TSIZ" -> echo("size: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TBLE" -> echo("zhuanji: ${
                reader.readToStringButSkipFirst(
                        reader.readToIntFromLast())}")
                "TIT2" -> echo("title: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TIT3" -> echo("sub title: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TCON" -> echo("style: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "AENC" -> echo("encode tech: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TBPM" -> echo("jiepai per second: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "COMM" -> println("comment: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                "TSSE" -> echo("encoder: ${
                reader.readToStringButSkipFirst(2,
                        reader.readToIntFromLast())}")
                else -> break@flag1
            }
        }
    }
}