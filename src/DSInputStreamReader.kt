import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset

/**
 * a domain specific reader
 * Created by ice1000 on 2016/5/19.
 */

class DSInputStreamReader(r: File) {
    private var index = 0
    private var reader = FileInputStream(r)
    fun read(length: Int, skip: Int): ByteArray {
        var ba = ByteArray(length)
        reader.read(ba, 0, length)
        reader.read(ByteArray(skip), 0, skip)
        index += length
        index += skip
        return ba ;
    }

    fun readString(length: Int, skip: Int): String {
        return String(read(length, skip))
    }

    fun readToLong(length: Int, skip: Int): Long {
        var ba = read(length, skip)
        var ret: Long = 0
        var pow: Long = 1
        for (i in 1..length) {
            var n = ba[length - i] * 1
            ret += pow * if(n >= 0) n else (0x100 + n)
            pow *= 0x100
//            println("ba[${length - i}] = ${ba[length - i]}")
//            println("ret = $ret")
//            println("pow = $pow")
        }
        return ret
    }
}