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

open class PlayMusicThread(fileToPlay: String) : Thread() {

	protected var ais: AudioInputStream
	protected var line: SourceDataLine
	protected var format: AudioFormat

	var playData = PlayData()

	override fun run() = PlayerRunSupporter().run(playData, ais, line)

	init {
		ais = AudioSystem.getAudioInputStream(File(fileToPlay))
		format = ais.format
		line = SourceDataLineFactory.getLine(format)
	}
}
