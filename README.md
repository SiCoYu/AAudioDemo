# AAudioDemo

## AAudio 验证低Android原生延迟音频

## 修改内容
*  MainActivity.java

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (mEngineHandle != INVALID_PTR) {
                            nativeAAudioEngineStop(mEngineHandle);
                            nativeDestroyAAudioEngine(mEngineHandle);
                            mEngineHandle = INVALID_PTR;
                        }
                        if (mEngineHandle == INVALID_PTR) {
                            mEngineHandle = nativeCreateAAudioEngine(getAssets(), TEST_FILE_PATH, AUDIO_SAMPLERATE, AUDIO_CHANNELS, AUDIO_FORMAT);
                        }
                        if (mEngineHandle != INVALID_PTR) {
                            nativeAAudioEnginePlay(mEngineHandle);
                        }
                        break;
                    default:break;
                }
                return true;
            }
        });

* Project build.gradle
   
        dependencies {    
            classpath "com.android.tools.build:gradle:7.0.2"

            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }

* gradle-wrapper.properties

        distributionBase=GRADLE_USER_HOME
        distributionPath=wrapper/dists
        distributionUrl=https\://services.gradle.org/distributions/gradle-7.2-bin.zip
        zipStoreBase=GRADLE_USER_HOME
        zipStorePath=wrapper/dists

## ffmpeg修改wav格式为pcm格式

   *   将wav转换为48kHz单声道pcm
                
            ffmpeg -y -i test.wav -acodec pcm_s16le -f s16le -ac 1 -ar 48000 test.pcm

   *  将wav转换为48kHz双声道pcm
            
            ffmpeg -y -i test.wav -acodec pcm_s16le -f s16le -ac 2 -ar 48000 test.pcm

## 何为pcm格式


# 解决问题
## AndroidStudio版本
* Android Studio Giraffe | 2022.3.1 Patch 2
## 解决ANDROID_SDK_HOME问题
* 环境变量中将ANDROID_SDK_HOME改为ANDROID_HOME
## 解决NDK路径问题
* local.properties修改如下：
            
        sdk.dir=C\:\\Users\\xxxx\\AppData\\Local\\Android\\Sdk
        ndk.dir=C\:\\Users\\xxxx\\AppData\\Local\\Android\\Sdk\\ndk\\21.4.7075529

# 参考连接
## [①音频文件格式转换](https://www.xfyun.cn/doc/asr/voicedictation/Audio.html)
## [②Android SDK环境变量配置](https://blog.csdn.net/ai52learn/article/details/132371531?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7EPayColumn-1-132371531-blog-129711935.235%5Ev38%5Epc_relevant_default_base&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7EPayColumn-1-132371531-blog-129711935.235%5Ev38%5Epc_relevant_default_base&utm_relevant_index=1)
## [③Android SDK环境总结](https://blog.csdn.net/qq_41923691/article/details/129711935)
## [④Android Studio生成正式签名apk](https://blog.csdn.net/wyg1230/article/details/77529465)
## [⑤Android setOnTouchListener使用](https://blog.csdn.net/weixin_47139560/article/details/115211132)
## [⑥FFMPEG Git (ffmpeg-master-latest-win64-gpl.zip)](https://github.com/BtbN/FFmpeg-Builds/releases)
## [⑦音频文件转码](https://ai.baidu.com/ai-doc/SPEECH/7k38lxpwf)
