package com.osagle.engine.core;

import com.osagle.engine.debug;
import com.osagle.engine.iCallBack;
import com.osagle.engine.loader.loader;
import com.osagle.osagle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class scenes extends Thread implements iCallBack {
    private long mThreadSleep;
    private String mRendererName;

    private Map mMapTodo = new HashMap();
    private Map mMapDoing = new HashMap();
    private Map mMapCreated = new HashMap();
    private Map mMapOld = new HashMap();

    public scenes(String pRendererName, long pThreadSleep) {
        mRendererName = pRendererName;
        mThreadSleep = pThreadSleep;

        this.start();
    }
    public void run() {
        try {
            debug.log("OSAGLE.scenes", "add");
            while(true) { this.doing(); sleep(mThreadSleep); }
        }
        catch(Exception e) {
            debug.log("OSAGLE.scenes", "run.Exception : "+e.toString());
        }
    }
    public void todo(String pName, String pFile) {
        debug.log("OSAGLE.scenes", "add");

        if ((mMapDoing.get(pName) == null) && (mMapCreated.get(pName) == null)) {
            if (mMapOld.get(pName) != null) {
                mMapCreated.put(pName, mMapOld.get(pName));
            }
            else if (mMapTodo.get(pName) == null) {
                mMapTodo.put(pName, pFile);
            }
        } else { mMapTodo.remove(pName); }
    }
    public void doing() {
        debug.log("OSAGLE.scenes", "doing");
        if (mMapTodo.size() > 0) {
            Iterator<Map.Entry<String, String>> todos = mMapTodo.entrySet().iterator();
            while (todos.hasNext()) {
                Map.Entry<String, String> todo = todos.next();
                if (mMapDoing.get(todo.getKey()) == null) {
                    mMapDoing.put(todo.getKey(), todo.getValue());
                    loader.getJSON(this,"parse",todo.getKey(),todo.getValue());
                }
            }
        }
    }
    public void parse(String pName, JSONObject pJSON) {
        debug.log("OSAGLE.scenes", "parse : " + pName);

        try {
            Iterator<String> keys = pJSON.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                debug.log("OSAGLE.scenes", "key : " + key);
                if (key.equals("scene")) {
                    debug.log("OSAGLE.scenes", "key : " + key);
                    this.create(pName, pJSON.getJSONObject(key));
                }
                else if(key.equals("cameras")) {
                }
                else if(key.equals("materials")) {
                }
                else if(key.equals("geometries")) {
                }
                else if(key.equals("meshes")) {
                }
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE.scenes", "parse.Exception : " + e.toString());
        }
    }
    public void create(String pName, JSONObject pJSON) {
        debug.log("OSAGLE.scenes", "create : " + pJSON);

        try {
            Iterator<String> keys = pJSON.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equals("clearcolor")) {
                    debug.log("OSAGLE.scenes", "clearcolor");
                    JSONArray aColor = pJSON.getJSONArray(key);
                    float aRed = (float) aColor.getDouble(0);
                    float aGreen = (float) aColor.getDouble(1);
                    float aBlue = (float) aColor.getDouble(2);
                    float aAlpha = (float) aColor.getDouble(3);
                    osagle.getRenderer(mRendererName).setClearColor(aRed,aGreen,aBlue,aAlpha);
                }
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE.scenes", "parse.Exception : " + e.toString());
        }
    }
    public void setup(Object pObject, JSONObject pJSON) {}
}