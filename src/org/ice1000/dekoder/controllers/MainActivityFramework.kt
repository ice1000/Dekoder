package org.ice1000.dekoder.controllers

import com.jfoenix.controls.JFXButton
import javafx.stage.FileChooser
import org.ice1000.dekoder.database.DatabaseManager
import org.ice1000.dekoder.decoders.DecoderInterface
import org.ice1000.dekoder.decoders.MP3Decoder
import org.ice1000.dekoder.decoders.WAVDecoder
import org.ice1000.dekoder.utils.Echoer
import org.ice1000.dekoder.utils.threads.ProgressThread
import java.io.File
import java.util.*
import kotlin.concurrent.thread

/**
 * @author ice1000
 * the framework of MainActivity
 * MainActivity is actually
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

	private val PAUSE = "Pause"
	private val PLAY = "Play"
	private val OPEN = "Open"
	private var fileList: ArrayList<String> = ArrayList()

	abstract var dekoder: DecoderInterface?

	protected var manager = DatabaseManager()

	val chooser: FileChooser
		get() = FileChooser()

	private var progressThread = ProgressThread { setProgress(it) }

	private var progress = 0L

	private var stopped = false

	private var waiting = true
		set(value) {
			field = value
			if (!value) {
				println("waiting is set to be false")
				changeSong(true)
			}
		}

	open fun openGitHub() {
		Thread({
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " +
							"https://github.com/ice1000/Dekoder")
		}).start()
	}

	/**
	 * I think I`d better find a way to use less thread instances
	 * this might take too much memory.
	 * but I can only trust JVM`s GC now. : )
	 */
	open protected fun playMusic() {
		println("playMusic in MainActivity called.")
		waiting = true
		if (PLAY == getPlayButton().text) {
			progressThread = ProgressThread(
					{ setProgress(it) }
			)
			try {
				if (stopped) {
					clearPropertiesShown()
					propertiesPrinter().echo(File(dekoder!!.path).name)
					dekoder = choose(dekoder!!.path)
					stopped = false
				}
				dekoder?.onStart()
			} catch (e: IllegalStateException) {
				return
			}
			if (progress > 0)
				progressThread.storedTime = progress
			progressThread.start()
			getPlayButton().text = PAUSE
		} else if (PAUSE == getPlayButton().text) {
			dekoder?.onPause()
			progress = progressThread.storedTime
			progressThread.running = false
			progressThread.join()
			getPlayButton().text = PLAY
		} else {
			// OPEN
			showOpenFileDialog()
		}
	}

	open protected fun stopPlaying() {
		println("stopPlaying in MainActivity called.")
		progress = 0
		if (!stopped) {
			stopped = true
			dekoder?.onStop()
		}
		getPlayButton().text = PLAY
		stop()
	}

	private fun stop() {
		progressThread.running = false
		progressThread.join()
		try {
			setProgress(progress)
		} catch(e: ArithmeticException) {
			setProgress(1)
		}
	}

	/**
	 * @param i now time
	 * this method will automatically divide i by the total time.
	 * so just input the time which is already spent.
	 */
	abstract protected fun setProgress(i: Long)

	/**
	 * because I have to set the text on the button
	 * so MainActivity must provide this
	 */
	abstract protected fun getPlayButton(): JFXButton

	/**
	 * get a printer to show information in the properties field..
	 * in the very beginning I used inner fun in this framework.
	 * but to save code, I chose to implement this in the java interface.
	 * because java8 supports lambda : )
	 */
	abstract protected fun propertiesPrinter(): Echoer

	/**
	 * the same as that one ↑
	 * get a printer to show information in the files field.
	 */
	abstract protected fun filesPrinter(): Echoer

	/**
	 * to show a open file dialog
	 */
	abstract protected fun showOpenFileDialog()

	/**
	 * select a decoders to decode the music file.
	 * currently only WAVDecoder can work.
	 */
	protected fun choose(filePath: String): DecoderInterface {
		val p = propertiesPrinter()
		println(filePath)
		return if (filePath.endsWith("wav"))
			WAVDecoder(filePath, p)
		else if (filePath.endsWith("mp3"))
			MP3Decoder(filePath, p)
		//        else if (filePath.endsWith("mid"))
		//            MIDIDecoder(filePath, p)
		//        else if (filePath.endsWith("ape"))
		//            APEDecoder(filePath, p)
		else
			WAVDecoder(filePath, p)
	}

	/**
	 * this will stop playing
	 */
	open protected fun openFile(file: File) {
		println("openFile in MainActivity called.")
		val path = file.path
		// stop the latest opened file
		try {
			stopPlaying()
		} catch (e: NullPointerException) {
			e.printStackTrace()
		}
		// echo the name of this file
		println("stopped.")
		clearPropertiesShown()
		propertiesPrinter()
				.echo(file.name)
		showFilesInTheSamePath(file.path)
		// write this file`s path to the database
		manager.write(path)
		println("written.")
		// give a value to dekoder, to choose a type
		dekoder = choose(path)
		println("written.")
		try {
			dekoder?.onCreate()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}

	open protected fun initialize() {
		println("initialize in MainActivity called.")
		try {
			openFile(File(manager.read()[0]))
		} catch (ignored: Exception) {
			getPlayButton().text = OPEN
		}
	}

	/**
	 * @param path is the path of current file, not parent file
	 * this will call the parent file automatically
	 * and will clear the files which are already shown
	 */
	protected fun showFilesInTheSamePath(path: String) {
		println("showFilesInTheSamePath in MainActivity called.")
		clearFilesShown()
		fileList.removeAll(fileList)
		File(path).parentFile.list().forEach {
			if (it.endsWith("wav") || it.endsWith("mp3")) {
				filesPrinter().echo(it)
				fileList.add(it)
			}
		}
	}

	/**
	 * change the current playing song
	 * @param next true means go next, false means go previous
	 */
	protected fun changeSong(next: Boolean) {
		println("changeSong in MainActivity called.")
		val path = File(dekoder?.path)
		val currentFileIndex = fileList.indexOf(path.name)
		openFile(File(
				path.parent + File.separator + fileList[
						(currentFileIndex
								+ if (next) 1 else -1)
								% fileList.size
						]
		))
		playMusic()
	}

	/**
	 * clear the files section which are shown
	 * !!!attention!!! this is not properties section
	 * this is files section!!!!
	 */
	protected abstract fun clearFilesShown()

	/**
	 * clear the properties shown
	 */
	protected abstract fun clearPropertiesShown()

	/**
	 * exit
	 */
	protected fun onDestroyed() = thread {
		try {
			stopPlaying()
			dekoder = null
		} catch(e: Exception) {
		}
		System.exit(0)
	}
}
