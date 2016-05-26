package decoder

import utils.DSInputStreamReader
import utils.Echoer
import java.io.File
import javax.sound.midi.MidiSystem
import javax.sound.midi.Sequence
import javax.sound.midi.Sequencer

/**
 * @author ice1000
 * TODO: implement midi decoding
 * Created by ice1000 on 2016/5/24.
 */

class MIDIDecoder : DecoderInterface {

    override var reader: DSInputStreamReader
    override var path: String
    private var seq: Sequence? = null
    private var seqr: Sequencer? = null

    override fun play(): Unit? = seqr?.start()

    override fun stop(): Unit? = Unit

    override fun init() = Unit

    override fun getTotalTime(): Long = 60L

    constructor(path: String, echoer: Echoer) : super(echoer) {
        this.path = path
        reader = DSInputStreamReader(File(path))

        seq = MidiSystem.getSequence(File(path))
        seqr = MidiSystem.getSequencer()
        seqr?.open()
        seqr?.sequence = seq
        seqr?.addMetaEventListener {
            if (it.type == 47)
                println("done playing")
        }

    }
}