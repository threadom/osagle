package com.osagle.engine;

import org.json.JSONObject;

public interface iCallBack {
    public void parse(String pKey, JSONObject pJSON);
    public void create(String pKey, JSONObject pJSON);
    public void setup(Object pObject, JSONObject pJSON);
}
