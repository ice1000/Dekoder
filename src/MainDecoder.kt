import data.Fact
import utils.DSInputStreamReader
import java.io.File

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

class MainDecoder {
    private var file: File
    private var reader: DSInputStreamReader
    private var channels: Int
    private var size: Long
    private var name: String
    private var samplePSec: Int
    private var bytePSec: Int
    private var bytePSample: Int
    private var bitPSample: Int
    private var extraData: Int?
    private var fact: Fact?

    constructor(fileName: String) {
        name = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        var metaSize: Int
        println("检查元数据。")
        // ======================================================== 0
        println(
                if ("RIFF".equals(reader.readToString()))
                    "RIFF字段正常。"
                else "警告：RIFF字段异常！")
        // ======================================================== 4
        size = reader.readToLong()
        println("除去元数据总大小 = $size")
        // ======================================================== 8
        println(
                if ("WAVE".equals(reader.readToString()))
                    "WAVE字段正常。"
                else "警告：WAVE字段异常！")
        // ======================================================== 12
        if ("fmt ".equals(reader.readToString()))
            println("\n格式数据：")
        // ======================================================== 16
        metaSize = reader.readToInt(1, 3)
        println("元数据大小： $metaSize")
        // ======================================================== 20
        println("编码方式： ${reader.readToLong(1, 1)}")
        // ======================================================== 22
        channels = reader.readToInt(1, 1)
        println(if (channels == 1) "单" else "双" + "声道。")
        // ======================================================== 24
        samplePSec = reader.readToInt()
        println("采样频率：$samplePSec")
        // ======================================================== 28
        bytePSec = reader.readToInt()
        println("每秒需要的字节数：$bytePSec")
        // ======================================================== 32
        bytePSample = reader.readToInt(2)
        println("每个采样需要的字节数：$bytePSample")
        // ======================================================== 34
        bitPSample = reader.readToInt(2)
        println("每秒需要的bit数：$bitPSample")
        // ======================================================== 36
        extraData = if(metaSize == 18) reader.readToInt(2) else null
        // ======================================================== 36 or 38
        var s = reader.readToString()
        if("fact".equals(s)) {
            var a = reader.readToInt()
            var b = reader.readToString(a)
            fact = Fact(a, b)
            println("FACT数据存在，值为$b")
            // 把fact之后的data也读取一次
            reader.read()
        } else {
            // data已经读取过了，直接给fact赋值null
            fact = null
            println("FACT数据不存在")
        }
        // ======================================================== end
        // 现在读完了元数据，开始读取声波数据
        println("开始读取声波数据。")
    }

    constructor() : this("./raw.wav")

}