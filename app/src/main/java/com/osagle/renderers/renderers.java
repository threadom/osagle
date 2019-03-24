package com.osagle.renderers;

import android.content.Context;

import com.osagle.engine.debug;
import com.osagle.osagle;

import java.util.Map;
import java.util.HashMap;

public class renderers {
    private Map mRenderer = new HashMap();

    public void addRenderer(String pName, osagle pOsagle, long pSleep) {
        debug.log("OSAGLE.renderers", "addRenderer : " + pName);

        renderer aRenderer;
        aRenderer = new renderer(pOsagle, pSleep);
        mRenderer.put(pName, aRenderer);
    }
    public renderer getRenderer(String pName) {
        debug.log("OSAGLE.renderers", "getRenderer : " + mRenderer);
        debug.log("OSAGLE.renderers", "getRenderer : " + pName);
        return (renderer) mRenderer.get(pName);
    }
    public void kill(String pName) {
        debug.log("OSAGLE.renderers", "kill");

    }
}
