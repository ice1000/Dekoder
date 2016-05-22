package view.cui

import decoder.WAVDecoder

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

fun main(args: Array<String>) {
//    val view = View()
//    view.onCreate()
    val decoder = CUIDecoder("./raw.wav")
    println(decoder.name)
    decoder.play()
}

class CUIDecoder(fileName: String) : WAVDecoder(fileName) {
    override fun echo(msg: String) {
        println(msg)
    }
}