package view.cui

import decoder.MP3Decoder
import decoder.WAVDecoder
import utils.Echoer
import java.util.*

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

fun main(args: Array<String>) {
//    println("start")
//    var a = System.currentTimeMillis()
//    Thread.sleep(1000)
//    println("a = ${System.currentTimeMillis() - a}")

    //    val view = View()
    //    view.onCreate()
    println("please input music file path :")
    var s = Scanner(System.`in`).nextLine()
    val decoder = CUIMP3Decoder(s!!)
    println(decoder.path)
    decoder.play()
}

class CUIWAVDecoder(fileName: String) : WAVDecoder(fileName, Printer())
class CUIMP3Decoder(fileName: String) : MP3Decoder(fileName, Printer())

class Printer : Echoer {
    override fun echo(msg: String) = println(msg)
}