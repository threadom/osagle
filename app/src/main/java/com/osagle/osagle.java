package com.osagle;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.example.holeinone.R;
import com.osagle.engine.core.animations;
import com.osagle.engine.core.meshes;
import com.osagle.engine.core.cameras;
import com.osagle.engine.core.alphamaps;
import com.osagle.engine.core.bumpmaps;
import com.osagle.engine.core.geometries;
import com.osagle.engine.core.materials;
import com.osagle.engine.core.scenes;
import com.osagle.engine.core.textures;
import com.osagle.engine.debug;
import com.osagle.engine.core.fonts;
import com.osagle.engine.core.groups;
import com.osagle.engine.core.lights;
import com.osagle.engine.core.samples;
import com.osagle.engine.core.texts;
import com.osagle.engine.core.tilesmaps;
import com.osagle.engine.core.variables;
import com.osagle.renderers.renderer;
import com.osagle.renderers.renderers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

public class osagle extends GLSurfaceView {
    private static renderers mRenderers = new renderers();
    private static scenes mScenes;
    private static cameras mCameras;
    private static lights mLights;
    private static textures mTextures;
    private static bumpmaps mBumpmaps;
    private static alphamaps mAlphamaps;
    private static materials mMaterials;
    private static geometries mGeometries;
    private static meshes mMeshes;
    private static groups mGroups;
    private static variables mVariables;
    private static animations mAnimations;
    private static tilesmaps mTilesMaps;
    private static samples mSamples;
    private static fonts mFonts;
    private static texts mTexts;

    private static long speed = 300;

    String mName = "";
    String mPath = "";

    public osagle(Context context) {
        super(context);
        debug.log("OSAGLE", "osagle");
        this.init();
    }

    public osagle(Context context, AttributeSet attrs) {
        super(context, attrs);
        debug.log("OSAGLE", "osagle");

        mName =  this.getResources().getResourceEntryName(this.getId());

        this.init();
    }

    private void init() {
        debug.log("OSAGLE", "init");
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        osagle.start(mName, this);
        this.waitRenderer();
        setRenderer(osagle.getRenderer(mName));
    }

    public void waitRenderer() {
        try {
            while (osagle.getRenderer(mName) == null) {
                debug.log("OSAGLE", "waitRenderer");
                sleep(speed);
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE", "waitRenderer.Exception : "+e.toString());
        }
    }

    public void setClearColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        debug.log("OSAGLE", "setClearColor");
        osagle.getRenderer(mName).setClearColor(pRed,pGreen,pBlue,pAlpha);
    }

    public void setScene(String pName, String pFile) {
        mScenes.todo(pName, pFile);
        this.waitRenderer();
        osagle.getRenderer(mName).render(pName);
    }

    public static void start(String pName, osagle pOsagle) {
        debug.log("OSAGLE", "start : " + pName);

        mScenes = new scenes(pName,speed/2);
        mCameras = new cameras(pName,speed/2);
        mMeshes = new meshes(pName,speed/2);
        mLights = new lights(pName,speed/2);
        mTextures = new textures(pName,speed/2);
        mBumpmaps = new bumpmaps(pName,speed/2);
        mAlphamaps = new alphamaps(pName,speed/2);
        mMaterials = new materials(pName,speed/2);
        mTilesMaps = new tilesmaps(pName,speed/2);
        mSamples = new samples(pName,speed/2);
        mFonts = new fonts(pName,speed/2);
        mTexts = new texts(pName,speed/2);
        mGeometries = new geometries(pName,speed/2);
        mGroups = new groups(pName,speed/2);
        mVariables = new variables();
        mAnimations = new animations();

        mRenderers.addRenderer(pName, pOsagle, speed);
    }
    public static renderer getRenderer(String pName) {
        debug.log("OSAGLE", "getRenderer : " + pName);
        return mRenderers.getRenderer(pName);
    }
    public static void killRenderer(String pName) {
        debug.log("OSAGLE", "killRenderer");
        mRenderers.kill(pName);
    }
    public void resize() {
        debug.log("OSAGLE", "resize: " + this.getLayoutParams().height);
    }
}
