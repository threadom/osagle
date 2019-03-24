package com.osagle.engine.loader;

import android.content.Context;
import android.content.res.Resources;

import com.example.holeinone.holeinone;
import com.osagle.engine.debug;
import com.osagle.engine.iCallBack;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class loader {
    public static void getJSON(iCallBack pObject, String pCallBackMethod, String pKey, JSONObject pJSON) {

    }
    public static void getJSON(iCallBack pObject, String pCallBackMethod, String pKey, String pFile) {
            debug.log("OSAGLE.loader", "getJSON");
            try {
                Context context = holeinone.getAppContext();
                Resources res = context.getResources();
                int resId = res.getIdentifier(pFile, "raw", context.getPackageName());
                if (resId > 0) {
                    InputStream inputStream = context.getResources().openRawResource(resId);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = inputStream.read();
                    while (i != -1) {
                        baos.write(i);
                        i = inputStream.read();
                    }
                    inputStream.close();
                    JSONObject aJSON = new JSONObject(baos.toString());
                    debug.log("OSAGLE.loader", "getJSON : " + aJSON);
                    if (pCallBackMethod == "create") { pObject.create(pKey, aJSON); }
                    else if (pCallBackMethod == "parse") { pObject.parse(pKey, aJSON); }
                }
            }
            catch (Exception e) {
                debug.log("OSAGLE.loader", "getJSON.Exception : " + e.toString());
            }
        }
}
