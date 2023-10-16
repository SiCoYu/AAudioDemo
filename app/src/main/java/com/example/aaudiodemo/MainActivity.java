package com.example.aaudiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.aaudiodemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int INVALID_PTR = 0;

    // Used to load the 'aaudiodemo' library on application startup.
    static {
        System.loadLibrary("aaudiodemo");
    }

    private ActivityMainBinding binding;
    private long mEngineHandle = INVALID_PTR;
    private static final String TEST_FILE_PATH = "ka.pcm";
    private static final int AUDIO_SAMPLERATE = 48000;
    private static final int AUDIO_CHANNELS = 1;
    private static final int AUDIO_FORMAT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mEngineHandle = nativeCreateAAudioEngine(getAssets(), TEST_FILE_PATH, AUDIO_SAMPLERATE, AUDIO_CHANNELS, AUDIO_FORMAT);
        binding.initAudio.setOnClickListener(v -> {
            if (mEngineHandle == INVALID_PTR) {
                mEngineHandle = nativeCreateAAudioEngine(getAssets(), TEST_FILE_PATH, AUDIO_SAMPLERATE, AUDIO_CHANNELS, AUDIO_FORMAT);
            }
        });

        binding.playAudio.setOnTouchListener(new View.OnTouchListener() {
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

        binding.playAudio.setOnClickListener((v) -> {
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
        });
        binding.pauseAudio.setOnClickListener((v) -> {
            if (mEngineHandle != INVALID_PTR) {
                nativeAAudioEnginePause(mEngineHandle);
            }
        });
        binding.stopAudio.setOnClickListener((v) -> {
            if (mEngineHandle != INVALID_PTR) {
                nativeAAudioEngineStop(mEngineHandle);
                nativeDestroyAAudioEngine(mEngineHandle);
                mEngineHandle = INVALID_PTR;
            }
        });
    }

    public native long nativeCreateAAudioEngine(AssetManager assetMgr, String filePath, int sampleRate, int audioChannel, int audioFormat);

    public native void nativeDestroyAAudioEngine(long engineHandle);

    public native void nativeAAudioEnginePlay(long engineHandle);

    public native void nativeAAudioEnginePause(long engineHandle);

    public native void nativeAAudioEngineStop(long engineHandle);
}