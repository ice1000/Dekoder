package utils.factories

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * factory method to generate a line
 * Created by asus1 on 2016/5/29.
 */

object SourceDataLineFactory {
    fun getLine(audioFormat: AudioFormat): SourceDataLine {
        val info = DataLine.Info(
                SourceDataLine::class.java,
                audioFormat
        )
        val res = AudioSystem.getLine(info) as SourceDataLine
        res.open(audioFormat)
        return res
    }
}