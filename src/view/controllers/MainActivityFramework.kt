package view.controllers

import com.jfoenix.controls.JFXButton
import data.database.DatabaseManager
import decoders.DecoderInterface
import decoders.MP3Decoder
import decoders.WAVDecoder
import javafx.stage.FileChooser
import utils.Echoer
import utils.threads.ProgressThread
import java.io.File
import java.util.*

/**
 * @author ice1000
 * the framework of MainActivity
 * Created by asus1 on 2016/5/22.
 */

abstract class MainActivityFramework {

    private val PAUSE = "Pause"
    private val STOP = "Stop"
    private val PLAY = "Play"
    private val OPEN = "Open"
    private var fileList: ArrayList<String> = ArrayList()

    abstract var dekoder: DecoderInterface?

    protected var manager = DatabaseManager()

    val chooser: FileChooser
        get() = FileChooser()

    private var progressThread = ProgressThread (
            { setProgress(it) },
            { changeSong(true) }
    )

    private var progress = 0L

    open fun openGitHub() = Runtime.getRuntime().exec(
            "rundll32 url.dll,FileProtocolHandler " +
                    "https://github.com/ice1000/Dekoder")

    /**
     * I think I`d better find a way to use less thread instances
     * this might take too much memory.
     * but I can only trust JVM`s GC now. : )
     */
    open protected fun playMusic() {
        if (PLAY == getPlayButton().text) {
            progressThread = ProgressThread (
                    { setProgress(it) },
                    { changeSong(true) }
            )
            if (progress > 0)
                progressThread.storedTime = progress
            progressThread.start()
            try {
                dekoder?.onStart()
            } catch (e: IllegalStateException) {
                return
            }
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
        progress = 0
        dekoder?.onStop()
        getPlayButton().text = PLAY
        progressThread.running = false
        progressThread.join()
        setProgress(progress)
    }

    /**
     * @param i now time
     * this method will automatically divide i by the total time.
     * so just input the time which is already spent.
     */
    abstract protected fun setProgress(i: Long)

    /**
     * because I have to set the text on the button
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
        val path = file.path
        // stop the latest opened file
        try {
            stopPlaying()
        } catch(e: NullPointerException) {
        }
        // echo the name of this file
        clearPropertiesShown()
        propertiesPrinter().echo(file.name)
        showFilesInTheSamePath(file.path)
        // write this file`s path to the database
        manager.write(path)
        // give a value to dekoder, to choose a type
        dekoder = choose(path)
        try {
            dekoder?.onCreate()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    open protected fun initialize() {
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
        clearFilesShown()
        fileList.removeAll(fileList)
        File(path).parentFile.list().forEach {
            if (it.endsWith("wav")) {
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
        val path = File(dekoder?.path)
        val currentFileIndex = fileList.indexOf(path.name)
        openFile(File(
                path.parent + File.separator +
                        fileList[(currentFileIndex + if (next) 1 else -1)
                                % fileList.size]
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
}