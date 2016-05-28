package utils.threads

import data.PlayData
import utils.factories.getLine
import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

class PlayMusicThread : Thread {

    var playData = PlayData()
    private var line: SourceDataLine? = null
    private var ais: AudioInputStream? = null
    val BUFFER_SIZE = 0xF

    constructor(fileToPlay: String) : super() {
        ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null)
            line = getLine(ais!!.format)
        line!!.open()
        line!!.start()
    }

    override fun run() {
        while (!playData.threadExit) {
            var inBytes = 0;
            while ((inBytes != -1) && (!playData.threadExit))
                if (!playData.isPaused) {
                    val audioData = ByteArray(BUFFER_SIZE)
                    inBytes = ais!!.read(audioData, 0, BUFFER_SIZE);
                    if (inBytes >= 0)
                        line!!.write(audioData, 0, inBytes);
                }
        }
        line!!.drain()
        line!!.close()
    }
}
