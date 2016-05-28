package data

/**
 * @author ice1000
 * Created by asus1 on 2016/5/29.
 */

data class PlayData(
        var isStopped: Boolean = false,
        var isPaused: Boolean = false,
        var isPlaying: Boolean = false,
        var threadExit: Boolean = false
)