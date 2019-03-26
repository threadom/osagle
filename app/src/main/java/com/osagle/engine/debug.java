package com.osagle.engine;

import android.os.Debug;
import android.util.Log;

public class debug {
    public static void log(String pTag, String pText) {
        if (Debug.isDebuggerConnected()) {
            Log.d(pTag, pText);
        }
    }
}
