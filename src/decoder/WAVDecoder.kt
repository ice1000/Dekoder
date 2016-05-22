package decoder

import data.Fact
import utils.DSInputStreamReader
import utils.DecoderInterface
import java.io.File

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

abstract class WAVDecoder:DecoderInterface {
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
    var name: String

    constructor(fileName: String) {
        name = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        val metaSize: Int
        // ======================================================== 0
        if("RIFF".equals(reader.readToString()))
            echo("RIFF字段正常。")
        else {
            echo("警告：RIFF字段异常")
            return
        }
        // ======================================================== 4
        size = reader.readToLong()
        echo("除去元数据总大小 = $size")
        // ======================================================== 8
        echo(
                if ("WAVE".equals(reader.readToString()))
                    "WAVE字段正常。"
                else "警告：WAVE字段异常")
        // ======================================================== 12
        if (!"fmt ".equals(reader.readToString())){
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

    constructor() : this("./raw.wav")

    abstract fun echo(msg: String = "")
    /**
     * 开始读取声波数据
     */
    override fun play() {
//        echo("开始读取声波数据。")
//        for (i in 0..size) {
//            val data = reader.read(if (bitPSample == 16) 4 else 2)
//            echo(data.toString())
//        }
//        // 双声道的话每次读两个数据，所以总的读取数量减半
//        for (i in 0..size / 2) {
//            val dataL = reader.read(if (bitPSample == 16) 4 else 2)
//            val dataR = reader.read(if (bitPSample == 16) 4 else 2)
//            echo("left: $dataL")
//            echo("right: $dataR")
//        }
    }

    override fun save() {
        // TODO: save data
    }
}