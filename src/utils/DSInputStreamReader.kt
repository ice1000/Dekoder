package utils

import java.io.File
import java.io.FileInputStream

/**
 * a domain specific input stream reader
 * @author ice1000
 * Created by ice1000 on 2016/5/19.
 */

class DSInputStreamReader(r: File) {
    private var index = 0
    private var reader = FileInputStream(r)
    fun read(length: Int = 4, skip: Int = 0): ByteArray {
        val ba = ByteArray(length)
        reader.read(ba, 0, length)
        reader.read(ByteArray(skip), 0, skip)
        index += length
        index += skip
        return ba ;
    }

    fun readToString(length: Int = 4, skip: Int = 0): String {
        return String(read(length, skip))
    }

    fun readToLong(length: Int = 4, skip: Int = 0): Long {
        val ba = read(length, skip)
        var ret: Long = 0
        var pow: Long = 1
        for (i in 0..length - 1) {
            val n = ba[i] * 1
            ret += pow * if (n >= 0) n else (0x100 + n)
            pow *= 0x100
            //            println("ba[$i] = ${ba[i]}")
            //            println("ret = $ret")
            //            println("pow = $pow")
        }
        return ret
    }

    fun readToInt(length: Int = 4, skip: Int = 0): Int {
        return readToLong(length, skip).toInt()
    }
}