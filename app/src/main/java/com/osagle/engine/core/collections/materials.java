package com.osagle.engine.core.collections;

import com.osagle.engine.core.abstracts.material;
import com.osagle.engine.core.materials.flatMaterial;
import com.osagle.engine.core.materials.phongMaterial;
import com.osagle.engine.core.materials.wireMaterial;
import com.osagle.engine.core.utils.color;
import com.osagle.engine.debug;
import com.osagle.engine.iCallBack;
import com.osagle.engine.loader.loader;
import com.osagle.osagle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class materials extends Thread implements iCallBack {
    private long mThreadSleep;
    private osagle mOsagle;

    private Map mMapTodo = new HashMap();
    private Map mMapDoing = new HashMap();
    private Map mMapCreated = new HashMap();
    private Map mMapOld = new HashMap();

    public materials(osagle pOsagle, long pThreadSleep) {
        mOsagle = pOsagle;
        mThreadSleep = pThreadSleep;

        this.start();
    }
    public void run() {
        try {
            debug.log("OSAGLE.materials", "add");
            while(true) { this.doing(); sleep(mThreadSleep); }
        }
        catch(Exception e) {
            debug.log("OSAGLE.materials", "run.Exception : "+e.toString());
        }
    }
    public void todo(JSONObject pJSON) {
        debug.log("OSAGLE.materials", "todo : " + pJSON.toString());

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
            debug.log("OSAGLE.materials", "todo.Exception : "+e.toString());
        }
    }
    public void doing() {
        debug.log("OSAGLE.materials", "doing");
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
        debug.log("OSAGLE.materials", "create : " + pJSON.toString());

        try {
            if (pJSON.has("type")) {
                String aType = pJSON.getString("type");
                switch (aType) {
                    case "flat": this.setup(new flatMaterial(), pJSON); break;
                    case "phong": this.setup(new phongMaterial(), pJSON); break;
                    case "wireframe": this.setup(new wireMaterial(), pJSON); break;
                    default:
                }
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE.materials", "create.Exception : " + e.toString());
        }
    }
    public void setup(Object object, JSONObject pJSON) {}
    public void setup(material pMaterial, JSONObject pJSON) {
        debug.log("OSAGLE.materials", "setup");

        try {
            if (pJSON.has("side")) {
                if (pJSON.getString("side") == "double") { pMaterial.side = material.doubleSide; }
                if (pJSON.getString("side") == "front") { pMaterial.side = material.frontSide; }
                if (pJSON.getString("side") == "back") { pMaterial.side = material.backSide; }
            }
            if (pJSON.has("texture")) {
                pMaterial.texturemap = mOsagle.getTexturemap(pJSON.getString("texturemap"));
            }
            else {
                if (pJSON.has("color")) { pMaterial.color = new color(pJSON.getJSONArray("color")); }
                else { pMaterial.color = new color(new int[]{1,1,1}); }
            }
            if (pJSON.has("bumpmap")) {
                pMaterial.bumpmap = mOsagle.getBumpmap(pJSON.getString("bumpmap"));
                if (pJSON.has("bumpscale")) {
                    pMaterial.bumpscale = (float) pJSON.getDouble("bumpscale");
                }
            }
            if (pJSON.has("alphamap")) {
                pMaterial.alphamap = mOsagle.getAlphamap(pJSON.getString("alphamap"));
                pMaterial.transparent = true;
            }
            if (pJSON.has("ambient")) {
                pMaterial.ambient = new color(pJSON.getJSONArray("ambient"));
            }
            if (pJSON.has("emissive")) {
                pMaterial.emissive = new color(pJSON.getJSONArray("emissive"));
            }
            if (pJSON.has("specular")) {
                pMaterial.specular = new color(pJSON.getJSONArray("specular"));
            }
            if (pJSON.has("shininess")) {
                pMaterial.shininess = (float) pJSON.getDouble("shininess");
            }
            if (pJSON.has("reflectivity") || pJSON.has("opacity")) {
                if (pJSON.has("reflectivity")) {
                    pMaterial.reflectivity = (float) pJSON.getDouble("reflectivity");
                }
                if (pJSON.has("opacity")) {
                    pMaterial.opacity = (float) pJSON.getDouble("opacity");
                }
                pMaterial.texturecube = mOsagle.getTexturecube(pJSON.getString("texturecube"));
            }
        }
            catch (Exception e) {
            debug.log("OSAGLE.materials", "create.Exception : " + e.toString());
        }
    }
}