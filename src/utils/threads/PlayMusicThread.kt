package utils.threads

import data.PlayData
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

class PlayMusicThread : Thread {

    constructor(fileToPlay: String) : super() {
        var ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null) {
            var baseFormat = ais.format
            var line = getLine(baseFormat)
        }
    }

    var playData = PlayData()

    override fun run() {
        while (!playData.threadExit) {
            if (!playData.isStopped) {

            }
        }
    }

    private fun getLine(audioFormat: AudioFormat): SourceDataLine {
        var res: SourceDataLine? = null
        val info = DataLine.Info(SourceDataLine::class.java,
                audioFormat)
        try {
            res = AudioSystem.getLine(info) as SourceDataLine
            res.open(audioFormat)
        } catch (e: Exception) {
        }
        return res!!
    }
}
