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
 * Created by asus1 on 2016/5/29.
 */
class MPEGPlayThread : Thread {

    private var ais: AudioInputStream? = null
    private var line: SourceDataLine? = null
    private var format: AudioFormat? = null

    var playData = PlayData()

    constructor (fileToPlay: String, next: (Boolean) -> Unit) {
        // MPEG1L3转PCM_SIGNED
        ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null) {
            format = ais!!.format
            line = getLine(format!!)
        }
        if (format!!.encoding != AudioFormat.Encoding.PCM_SIGNED) {
            format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    format!!.sampleRate,
                    16 ,
                    format!!.channels,
                    format!!.channels * 2,
                    format!!.sampleRate,
                    false
            )
            ais = AudioSystem.getAudioInputStream(
                    format,
                    ais
            );
        }
    }

    override fun run() {
        PlayerRunSupporter().run(playData, ais, line)
    }
}