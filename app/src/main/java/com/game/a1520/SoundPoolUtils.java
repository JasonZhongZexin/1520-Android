package com.game.a1520;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPoolUtils {
    private static SoundPoolUtils soundPoolUtils = null;
    private Context mContext;
    private SoundPool soundPool;
    private HashMap<String, Integer> soundPoolMap;// audio hash
    private SoundPoolUtils(Context context){
        this.mContext = context;
        if (soundPool == null){
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
            soundPoolMap = new HashMap<String, Integer>();
            AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            soundPoolMap.put("bgm",soundPool.load(context,R.raw.bgm,1));
            soundPoolMap.put("win",soundPool.load(context,R.raw.youwin,5));
            soundPoolMap.put("lost",soundPool.load(context,R.raw.youlose,5));
        }
    }

    public static SoundPoolUtils getInstnce(Context context){
        if(soundPoolUtils == null){
            synchronized (SoundPoolUtils.class){
                if(soundPoolUtils == null){
                    soundPoolUtils = new SoundPoolUtils(context);
                }
            }
        }
        return soundPoolUtils;
    }

    /**
     * play the sound
     * @param sound the key of sound in map
     * @param priority the priority of sound
     * @param uLoop -1: loop 0:no loop
     */
    public void play(String sound,float streamVolume, int priority, int uLoop) {
        soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, priority, uLoop, 1);
    }

    public void stop() {
        soundPool.autoPause();
    }
}
