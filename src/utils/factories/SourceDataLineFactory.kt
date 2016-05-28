package utils.factories

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/5/29.
 */

fun getLine(audioFormat: AudioFormat): SourceDataLine {
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