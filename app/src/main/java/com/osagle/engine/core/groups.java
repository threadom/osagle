package com.osagle.engine.core;

import com.osagle.engine.debug;
import com.osagle.engine.iCallBack;
import com.osagle.engine.loader.loader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class groups extends Thread implements iCallBack {
    private long mThreadSleep;
    private String mRendererName;

    private Map mMapTodo = new HashMap();
    private Map mMapDoing = new HashMap();
    private Map mMapCreated = new HashMap();
    private Map mMapOld = new HashMap();

    public groups(String pRendererName, long pThreadSleep) {
        mRendererName = pRendererName;
        mThreadSleep = pThreadSleep;

        this.start();
    }
    public void run() {
        try {
            debug.log("OSAGLE.groups", "add");
            while(true) { this.doing(); sleep(mThreadSleep); }
        }
        catch(Exception e) {
            debug.log("OSAGLE.groups", "run.Exception : "+e.toString());
        }
    }
    public void todo(JSONObject pJSON) {
        debug.log("OSAGLE.groups", "add");

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
            debug.log("OSAGLE.groups", "todo.Exception : "+e.toString());
        }
    }
    public void doing() {
        debug.log("OSAGLE.groups", "doing");
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
        debug.log("OSAGLE.groups", "create");

        setup(null, pJSON);
    }
    public void setup(Object pObject, JSONObject pJSON) {
        debug.log("OSAGLE.groups", "setup");
    }
}