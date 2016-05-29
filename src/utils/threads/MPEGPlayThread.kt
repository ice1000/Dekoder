package utils.threads

import utils.factories.getLine
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

/**
 * @author ice1000
 * Created by asus1 on 2016/5/29.
 */
class MPEGPlayThread : PlayMusicThread {

    constructor(fileToPlay: String) : super() {
        // MPEG1L3转PCM_SIGNED
        ais = AudioSystem.getAudioInputStream(File(fileToPlay))
        if (ais != null) {
            format = ais!!.format
            line = getLine(format!!)
        }
        if (format!!.encoding != AudioFormat.Encoding.PCM_SIGNED) {
            format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    format!!.sampleRate,
                    16,
                    format!!.channels,
                    format!!.channels * 2,
                    format!!.sampleRate,
                    false
            )
            ais = AudioSystem.getAudioInputStream(
                    format,
                    ais
            );
        }
    }
}