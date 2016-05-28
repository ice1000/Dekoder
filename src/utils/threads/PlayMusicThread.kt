package utils.threads

import data.PlayData
import utils.factories.getLine
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

class PlayMusicThread : Thread {

    var line: SourceDataLine? = null

    constructor(fileToPlay: String) : super() {
        val ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null)
            line = getLine(ais.format)
    }

    var playData = PlayData()

    override fun run() {
        while (!playData.threadExit) {
            if (!playData.isStopped) {

            }
        }
    }
}
