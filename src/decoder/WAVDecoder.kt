package decoder

import com.sun.media.sound.JavaSoundAudioClip
import data.Fact
import utils.DSInputStreamReader
import decoder.DecoderInterface
import utils.Echoer
import java.io.File
import java.io.InputStream

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

open class WAVDecoder : DecoderInterface {
    private var file: File
    private var reader: DSInputStreamReader
    private var channels: Int = 0
    private var size: Long = 0
    private var samplePSec: Int = 0
    private var bytePSec: Int = 0
    private var bytePSample: Int = 0
    private var bitPSample: Int = 0
    private var extraData: Int? = null
    private var fact: Fact? = null
    override var path: String
    override var echoer: Echoer

    constructor(fileName: String, echoer: Echoer) {
        this.echoer = echoer
        path = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        val metaSize: Int
        // ======================================================== 0
        if ("RIFF".equals(reader.readToString()))
            echo("RIFF字段正常。")
        else {
            echo("警告：RIFF字段异常")
            return
        }
        // ======================================================== 4
        size = reader.readToLong()
        echo("大小 = $size")
        // ======================================================== 8
        echo(
                if ("WAVE".equals(reader.readToString()))
                    "WAVE字段正常。"
                else "警告：WAVE字段异常")
        // ======================================================== 12
        if (!"fmt ".equals(reader.readToString())) {
            echo("格式数据错误")
            return
        }
        // ======================================================== 16
        metaSize = reader.readToInt(1, 3)
        echo("元数据大小： $metaSize")
        // ======================================================== 20
        echo("编码方式： ${reader.readToLong(1, 1)}")
        // ======================================================== 22
        channels = reader.readToInt(1, 1)
        echo(if (channels == 1) "单" else "双" + "声道")
        // ======================================================== 24
        samplePSec = reader.readToInt()
        echo("采样频率：$samplePSec")
        // ======================================================== 28
        bytePSec = reader.readToInt()
        echo("每秒需要的字节数：$bytePSec")
        // ======================================================== 32
        bytePSample = reader.readToInt(2)
        echo("每个采样需要的字节数：$bytePSample")
        // ======================================================== 34
        bitPSample = reader.readToInt(2)
        echo("每秒需要的bit数：$bitPSample")
        // ======================================================== 36
        extraData = if (metaSize == 18) reader.readToInt(2) else null
        echo("附加数据：$extraData")
        // ======================================================== 36 or 38
        val s = reader.readToString()
        if ("fact".equals(s)) {
            val a = reader.readToInt()
            val b = reader.readToString(a)
            fact = Fact(a, b)
            echo("FACT数据存在，值为$b")
            // 把fact之后的data也读取一次
            reader.read()
        } else {
            // data已经读取过了，直接给fact赋值null
            fact = null
            echo("FACT数据不存在")
        }
        // ======================================================== end
        // 现在读完了元数据
    }

    constructor(echoer: Echoer) : this("./raw.wav", echoer)

    fun echo(msg: String = "") {
        echoer.echo(msg)
    }

}