package com.osagle.renderers;

import android.content.Context;

import java.util.Map;
import java.util.HashMap;

public class renderers {
    private Map xRenderer;

    public renderer add(String pName, String pPath, Context pContext) {
        renderer aRenderer;

        Map xRenderer = new HashMap();
        aRenderer = new renderer(pContext);
        xRenderer.put(pName, aRenderer);
        return aRenderer;
    }
    public renderer get(String pName) {
        return (renderer) xRenderer.get(pName);
    }

    public void kill(String pName) {

    }
}
