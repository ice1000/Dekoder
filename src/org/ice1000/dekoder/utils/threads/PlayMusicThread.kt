package org.ice1000.dekoder.utils.threads

import org.ice1000.dekoder.data.PlayData
import org.ice1000.dekoder.factories.SourceDataLineFactory
import java.io.File
import javax.sound.sampled.AudioSystem

/**
 * @author ice1000
 * Created by asus1 on 2016/5/28.
 */

open class PlayMusicThread(fileToPlay: String) : Thread() {

	protected var ais = AudioSystem.getAudioInputStream(File(fileToPlay))
	protected var format = ais.format
	protected var line = SourceDataLineFactory.getLine(format)

	var playData = PlayData()

	override fun run() = PlayerRunSupporter().run(playData, ais, line)
}
