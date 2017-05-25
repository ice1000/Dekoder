package org.ice1000.dekoder.cui

import org.ice1000.dekoder.decoders.MP3Decoder
import org.ice1000.dekoder.decoders.WAVDecoder
import org.ice1000.dekoder.utils.Echoer
import java.util.*

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

fun main(args: Array<String>) {
	println("start")
	val a = System.currentTimeMillis()
	Thread.sleep(1000)
	println("a = ${System.currentTimeMillis() - a}")

//	val view = View()
//	view.onCreate()
	println("please input music file path :")
	val s = Scanner(System.`in`)
	val decoder = CUIMP3Decoder(s.nextLine())
	println(decoder.path)
	s.close()
	decoder.onStart()
}

class CUIWAVDecoder(fileName: String) : WAVDecoder(fileName, Printer())
class CUIMP3Decoder(fileName: String) : MP3Decoder(fileName, Printer())

class Printer : Echoer {
	override fun echo(msg: String) = println(msg)
}
