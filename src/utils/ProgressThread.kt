package utils

/**
 * @author ice1000
 * Created by ice1000 on 2016/5/25.
 */

class ProgressThread : Thread() {
    override fun run() {
    }
}

//            stop = true
//            Thread (Runnable {
//                var startTime = System.currentTimeMillis()
//                var nowTime: Long
//                while (stop) {
//                    nowTime = System.currentTimeMillis() - startTime
//                    setProgress(nowTime.toDouble() /
//                            (dekoder.getTotalTime() * 100))
////                    println("nowTime = $nowTime, total = ${dekoder.getTotalTime()}")
//                }
//            }).start()
