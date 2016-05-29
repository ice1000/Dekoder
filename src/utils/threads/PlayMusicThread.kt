package utils.threads

import data.PlayData
import utils.factories.getLine
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

open class PlayMusicThread : Thread {

    protected var ais: AudioInputStream? = null
    protected var line: SourceDataLine? = null
    protected var format: AudioFormat? = null

    var playData = PlayData()
    val BUFFER_SIZE = 0xF

    constructor(fileToPlay: String) : super() {
        ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null) {
            format = ais!!.format
            line = getLine(format!!)
        }
    }

    override fun run() {
        line!!.open()
        line!!.start()
        var inBytes = 0;
        while (inBytes != -1 && !playData.threadExit)
            if (!playData.isPaused) {
                val audioData = ByteArray(BUFFER_SIZE)
                inBytes = ais!!.read(audioData, 0, BUFFER_SIZE);
                if (inBytes >= 0)
                    line!!.write(audioData, 0, inBytes);
            }
        line!!.drain()
        line!!.close()
    }

    constructor()
}
