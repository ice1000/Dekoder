package utils

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/25.
 */

class ProgressThread(private var setter: (i: Double) -> Unit) : Thread() {

    var running = true

    override fun run() {
        running = true
        setter(0.0)
        var startTime = System.currentTimeMillis()
        var nowTime: Long
        while (running) {
            nowTime = System.currentTimeMillis() - startTime
            setter(nowTime.toDouble())
        }
    }
}
