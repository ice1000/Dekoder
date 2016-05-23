package view.cui

import decoder.WAVDecoder
import utils.Echoer
import java.util.*

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

fun main(args: Array<String>) {
    //    val view = View()
    //    view.onCreate()
    println("please input music file path :")
    var s = Scanner(System.`in`).nextLine()
    val decoder = CUIDecoder(s!!)
    println(decoder.name)
    decoder.play()
}

class CUIDecoder(fileName: String) : WAVDecoder(fileName, Printer())

class Printer() : Echoer() {
    override fun echo(msg: String) {
        println(msg)
    }

}