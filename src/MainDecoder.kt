import java.io.File

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/18.
 */

class MainDecoder {
    private var file: File
    private var reader: DSInputStreamReader
    var size: Long
    var channels: Int
    var name: String

    constructor(fileName: String) {
        name = fileName
        file = File(fileName)
        reader = DSInputStreamReader(file)
        var metaSize: Long
        println("Trace on.")
        // ==========================
        if ("RIFF".equals(reader.readString(4, 0)))
            println("RIFF checked.")
        else
            println("Warning: RIFF unchecked!")
        // ==========================
        size = reader.readToLong(4, 0)
        println("size = $size")
        // ==========================
        if ("WAVE".equals(reader.readString(4, 0)))
            println("WAVE checked.")
        else
            println("Warning: WAVE unchecked!")
        // ==========================
        if ("fmt ".equals(reader.readString(4, 0)))
            println("\nfmt:")
        // ==========================
        metaSize = reader.readToLong(1, 3)
        println("metaSize = $metaSize")
        // ==========================
        println("encoding: ${reader.readToLong(1, 1)}")
        // ==========================
        channels = reader.readToLong(1, 1).toInt()
        println("$channels channels.")
        // ==========================

    }

    constructor() : this("./raw.wav")

}