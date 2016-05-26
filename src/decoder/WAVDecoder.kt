package decoder

import com.sun.media.sound.JavaSoundAudioClip
import data.Fact
import utils.DSInputStreamReader
import utils.Echoer
import java.io.File

/**
 * @author ice1000
 * Created by asus1 on 2016/5/22.
 */

open class WAVDecoder : DecoderInterface {

    private var sound: JavaSoundAudioClip? = null
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
    override fun getTotalTime(): Long = size / bytePSec
    override fun init() {
        sound = JavaSoundAudioClip(File(path).inputStream())
    }

    override fun stop() = sound?.stop()

    override fun play() = sound?.play()

    constructor(fileName: String, echoer: Echoer) : super(echoer) {
        path = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        val metaSize: Int
        // ======================================================== 0
        if (!"RIFF".equals(reader.readToString())) {
            echo("warning: RIFF not found")
            return
        }
        // ======================================================== 4
        size = reader.readToLong()
        echo("size = $size")
        // ======================================================== 8
        if (!"WAVE".equals(reader.readToString())) {
            echo("warning: WAVE not found")
            return
        }
        // ======================================================== 12
        if (!"fmt ".equals(reader.readToString())) {
            echo("fmt not found")
            return
        }
        // ======================================================== 16
        metaSize = reader.readToInt(1, 3)
        echo("metadata: $metaSize bytes")
        // ======================================================== 20
        echo("decoding: ${reader.readToLong(1, 1)}")
        // ======================================================== 22
        channels = reader.readToInt(1, 1)
        echo(if (channels == 1) "single" else "double" + " channel")
        // ======================================================== 24
        samplePSec = reader.readToInt()
        echo("$samplePSec samples per second")
        // ======================================================== 28
        bytePSec = reader.readToInt()
        echo("$bytePSec bytes per second")
        // ======================================================== 32
        bytePSample = reader.readToInt(2)
        echo("$bytePSample bytes per sample")
        // ======================================================== 34
        bitPSample = reader.readToInt(2)
        echo("$bitPSample bits per second")
        // ======================================================== 36
        extraData = if (metaSize == 18) reader.readToInt(2) else null
        echo("extra data: $extraData")
        // ======================================================== 36 or 38
        val s = reader.readToString()
        if ("fact".equals(s)) {
            val a = reader.readToInt()
            val b = reader.readToString(a)
            fact = Fact(a, b)
            echo("FACT exists, values $b")
            // 把fact之后的data也读取一次
            reader.read()
        } else {
            // data已经读取过了，直接给fact赋值null
            fact = null
            echo("FACT does not exist")
        }
        // ======================================================== end
        // 现在读完了元数据
    }
}
