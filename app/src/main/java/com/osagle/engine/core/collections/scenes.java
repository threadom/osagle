package com.osagle.engine.core.collections;

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
    private osagle mOsagle;

    private Map mMapTodo = new HashMap();
    private Map mMapDoing = new HashMap();
    private Map mMapCreated = new HashMap();
    private Map mMapOld = new HashMap();

    public scenes(osagle pOsagle, long pThreadSleep) {
        mOsagle = pOsagle;
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
    public void todo(String pFile) {
        debug.log("OSAGLE.scenes", "add : " + pFile);

        if ((mMapDoing.get(pFile) == null) && (mMapCreated.get(pFile) == null)) {
            if (mMapOld.get(pFile) != null) {
                mMapCreated.put(pFile, mMapOld.get(pFile));
            }
            else if (mMapTodo.get(pFile) == null) {
                mMapTodo.put(pFile, pFile);
            }
        } else { mMapTodo.remove(pFile); }
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
                if (key.equals("scene")) { this.create(pName, pJSON.getJSONObject(key)); }
                else if(key.equals("cameras")) { mOsagle.setCameras(pJSON.getJSONObject(key)); }
                else if(key.equals("materials")) { mOsagle.setMaterials(pJSON.getJSONObject(key)); }
                else if(key.equals("geometries")) { mOsagle.setGeometries(pJSON.getJSONObject(key));}
                else if(key.equals("meshes")) { mOsagle.setMeshes(pJSON.getJSONObject(key)); }
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE.scenes", "parse.Exception : " + e.toString());
        }
    }
    public void create(String pName, JSONObject pJSON) {
        debug.log("OSAGLE.scenes", "create : " + pJSON.toString());

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
                    mOsagle.getRenderer().setClearColor(aRed,aGreen,aBlue,aAlpha);
                }
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE.scenes", "parse.Exception : " + e.toString());
        }
    }
    public void setup(Object pObject, JSONObject pJSON) {}
}