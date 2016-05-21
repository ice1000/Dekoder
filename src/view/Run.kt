package view

import MainDecoder

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

fun main(args: Array<String>) {
    val decoder = MainDecoder("./raw.wav")
    println(decoder.name)
//    decoder.play()
}