package utils.threads

import data.PlayData
import org.tritonus.share.sampled.file.TAudioFileFormat
import utils.factories.SourceDataLineFactory
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/29.
 */
class MPEGPlayThread// MPEG1L3转PCM_SIGNED
(fileToPlay: String) : Thread() {

    private var ais: AudioInputStream
    private var format: AudioFormat
    private var line: SourceDataLine
    private var file: File

    var playData = PlayData()

    /**
     * I cannot get duration from properties so~
     */
    fun getDuration(): Long {
        return (AudioSystem.getAudioFileFormat(file) as TAudioFileFormat).properties()["duration"] as Long
    }

    override fun run() {
        PlayerRunSupporter().run(playData, ais, line)
    }

    init {
        file = File(fileToPlay)
        ais = AudioSystem.getAudioInputStream(file)
        format = ais.format
        if (format.encoding != AudioFormat.Encoding.PCM_SIGNED) {
            format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    format.sampleRate,
                    16,
                    format.channels,
                    format.channels * 2,
                    format.sampleRate,
                    false
            )
            ais = AudioSystem.getAudioInputStream(
                    format,
                    ais
            )
        }
        line = SourceDataLineFactory.getLine(format)
    }
}