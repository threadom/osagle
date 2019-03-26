package com.osagle.engine.core.collections;

import com.osagle.engine.debug;
import com.osagle.engine.iCallBack;
import com.osagle.engine.loader.loader;
import com.osagle.osagle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class geometries extends Thread implements iCallBack {
    private long mThreadSleep;
    private osagle mOsagle;

    private Map mMapTodo = new HashMap();
    private Map mMapDoing = new HashMap();
    private Map mMapCreated = new HashMap();
    private Map mMapOld = new HashMap();

    public geometries(osagle pOsagle, long pThreadSleep) {
        mOsagle = pOsagle;
        mThreadSleep = pThreadSleep;

        this.start();
    }
    public void run() {
        try {
            debug.log("OSAGLE.geometries", "add");
            while(true) { this.doing(); sleep(mThreadSleep); }
        }
        catch(Exception e) {
            debug.log("OSAGLE.geometries", "run.Exception : "+e.toString());
        }
    }
    public void todo(JSONObject pJSON) {
        debug.log("OSAGLE.geometries", "todo : " + pJSON.toString());

        try  {
            Iterator<String> keys = pJSON.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if ((mMapDoing.get(key) == null) && (mMapCreated.get(key) == null)) {
                    if (mMapOld.get(key) != null) {
                        mMapCreated.put(key, mMapOld.get(key));
                    }
                    else if (mMapTodo.get(key) == null) {
                        mMapTodo.put(key, pJSON.get(key));
                    }
                } else { mMapTodo.remove(key); }
            }
        }
        catch(Exception e) {
            debug.log("OSAGLE.geometries", "todo.Exception : "+e.toString());
        }
    }
    public void doing() {
        debug.log("OSAGLE.geometries", "doing");
        if (mMapTodo.size() > 0) {
            Iterator<Map.Entry<String, JSONObject>> todos = mMapTodo.entrySet().iterator();
            while (todos.hasNext()) {
                Map.Entry<String, JSONObject> todo = todos.next();
                if (mMapDoing.get(todo.getKey()) == null) {
                    mMapDoing.put(todo.getKey(), todo.getValue());
                    loader.getJSON(this,"create",todo.getKey(),todo.getValue());
                }
            }
        }
    }
    public void parse(String pKey, JSONObject pJSON) {}
    public void create(String pKey, JSONObject pJSON) {
        debug.log("OSAGLE.geometries", "create : " + pJSON.toString());

        setup(null, pJSON);
    }
    public void setup(Object pObject, JSONObject pJSON) {
        debug.log("OSAGLE.geometries", "setup");
    }
}