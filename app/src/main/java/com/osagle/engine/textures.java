package com.osagle.engine;

import android.util.Log;

import com.osagle.osagle;

public class textures extends Thread {
    private long mThreadSleep;
    private String mRendererName;

    public textures(String pRendererName, long pThreadSleep) {
        mRendererName = pRendererName;
        mThreadSleep = pThreadSleep;
        this.start();
    }

    public void add() {

    }
    public void run() {
        try {
            while(true) { this.load(); sleep(osagle.speed); }
        }
        catch(Exception e) {
            Log.d("ERROR", "texture.run()", e);
        }
    }
    public void load() {
        Log.d("OSAGLE", "load");
    }
}
