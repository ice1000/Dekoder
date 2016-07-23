package utils.threads

import data.PlayData
import utils.factories.SourceDataLineFactory
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

open class PlayMusicThread() : Thread() {

    protected var ais: AudioInputStream? = null
    protected var line: SourceDataLine? = null
    protected var format: AudioFormat? = null

    var playData = PlayData()

    constructor (fileToPlay: String) : this() {
        ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null) {
            format = ais!!.format
            line = SourceDataLineFactory.getLine(format!!)
        }
    }

    override fun run() {
        PlayerRunSupporter().run(playData, ais, line)
    }
}
