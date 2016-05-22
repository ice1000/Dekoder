# WAVDecoder

这是一个wav音频解码器。<br/>
目前的进度:<br/>
+ [X] 读取元数据
+ [X] GUI
+ [ ] 播放音频
+ [ ] 识别音频

有两个artifact，一个是纯Kotlin的CUI版，一个是基于JavaFX的GUI版。<br/>
CUI的话音频未上传，可以使用自制的音频文件。<br/>
修改[Run文件夹](./src/Run.kt)中CUIDecoder的构造方法即可。<br/>
GUI比较强大，选择文件即可。