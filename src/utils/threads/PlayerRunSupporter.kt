package utils.threads

import data.PlayData
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/7/1.
 */

class PlayerRunSupporter {

    val BUFFER_SIZE = 0x10000

    fun run(playData: PlayData, ais: AudioInputStream?, line: SourceDataLine?) {
        line!!.open()
        line.start()
        var inBytes = 0
        var cnt = 0
        while (inBytes != -1 && !playData.threadExit)
            if (!playData.isPaused) {
                println("loop ${cnt++}")
                val audioData = ByteArray(BUFFER_SIZE)
                inBytes = ais!!.read(audioData, 0, BUFFER_SIZE)
                if (inBytes >= 0)
                    line.write(audioData, 0, inBytes)
            }
        line.drain()
        line.close()
        println("Ended playing")
    }
}