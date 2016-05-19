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

    constructor(fileName: String) {
        name = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        var metaSize: Long
        println("检查元数据。")
        // ========================================================
        println(
                if ("RIFF".equals(reader.readToString()))
                    "RIFF字段正常。"
                else "警告：RIFF字段异常！")
        // ========================================================
        size = reader.readToLong()
        println("除去元数据总大小 = $size")
        // ========================================================
        println(
                if ("WAVE".equals(reader.readToString()))
                    "WAVE字段正常。"
                else "警告：WAVE字段异常！")
        // ========================================================
        if ("fmt ".equals(reader.readToString()))
            println("\n格式数据：")
        // ========================================================
        metaSize = reader.readToLong(1, 3)
        println("元数据大小： $metaSize")
        // ========================================================
        println("编码方式： ${reader.readToLong(1, 1)}")
        // ========================================================
        channels = reader.readToInt(1, 1)
        println(if (channels == 1) "单" else "双" + "声道。")
        // ========================================================
        samplePSec = reader.readToInt()
        println("采样频率：$samplePSec")
        // ========================================================
        bytePSec = reader.readToInt()
        println("每秒字节数：$bytePSec")
        // ========================================================

    }

    constructor() : this("./raw.wav")

}