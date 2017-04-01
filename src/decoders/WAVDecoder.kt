package decoders

import data.Fact
import utils.DSInputStreamReader
import utils.Echoer
import utils.threads.PlayMusicThread
import java.io.File

/**
 * @author ice1000
 * decoder for wave files
 * Created by asus1 on 2016/5/22.
 */

open class WAVDecoder : DecoderInterface {

    override var reader: DSInputStreamReader
    private var channels: Int = 0
    private var size: Long = 0
    private var samplePSec: Int = 0
    private var bytePSec: Int = 0
    private var bytePSample: Int = 0
    private var bitPSample: Int = 0
    private var extraData: Int? = null
    private var fact: Fact? = null
    override var path: String

    private var playThread: PlayMusicThread

    override fun getTotalTime(): Long {
        try {
            return size / bytePSec
        } catch (e: Exception) {
            return 0
        }
    }

    override fun onCreate() {
//        sound = JavaSoundAudioClip(File(path).inputStream())
    }

    override fun onStop() {
        try {
            playThread.playData.threadExit = true
            playThread.playData.isPaused = true
            playThread.join()
        } catch (e: Exception) {
        }
//        sound?.stop()
    }

    override fun onPause() {
        playThread.playData.isPaused = true
//        playThread.join()
    }

    override fun onStart() {
        try {
            playThread.start()
        } catch (e: NullPointerException) {
            playThread = PlayMusicThread(path)
            playThread.start()
        } catch (e: IllegalThreadStateException) {
            // 妈蛋线程异常抓错了
            playThread.playData.isPaused = false
        }
//        playThread.join()
//        sound?.play()
    }

    constructor (fileName: String, echoer: Echoer) : super(echoer) {
        path = fileName
        playThread = PlayMusicThread(path)
        reader = DSInputStreamReader(File(path))
        val metaSize = reader.readToInt(1, 3)
        // ======================================================== 0
        if ("RIFF" != reader.readToString()) {
            echo("warning: RIFF not found")
            return
        }
        // ======================================================== 4
        size = reader.readToLong()
        echo("size = $size")
        // ======================================================== 8
        if ("WAVE" != reader.readToString()) {
            echo("warning: WAVE not found")
            return
        }
        // ======================================================== 12
        if ("fmt " != reader.readToString()) {
            echo("fmt not found")
            echo("progress bar will not appear")
            return
        }
        // ======================================================== 16
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
