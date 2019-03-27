package com.osagle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.osagle.engine.core.collections.alphamaps;
import com.osagle.engine.core.collections.animations;
import com.osagle.engine.core.collections.bumpmaps;
import com.osagle.engine.core.collections.cameras;
import com.osagle.engine.core.collections.fonts;
import com.osagle.engine.core.collections.geometries;
import com.osagle.engine.core.collections.groups;
import com.osagle.engine.core.collections.lights;
import com.osagle.engine.core.collections.materials;
import com.osagle.engine.core.collections.meshes;
import com.osagle.engine.core.collections.samples;
import com.osagle.engine.core.collections.scenes;
import com.osagle.engine.core.collections.texts;
import com.osagle.engine.core.collections.textures;
import com.osagle.engine.core.collections.tilesmaps;
import com.osagle.engine.core.collections.variables;
import com.osagle.engine.core.maps.alphamap;
import com.osagle.engine.core.maps.bumpmap;
import com.osagle.engine.core.maps.texturemap;
import com.osagle.engine.core.utils.texturecube;
import com.osagle.engine.debug;
import com.osagle.renderers.renderer;
import com.osagle.renderers.renderers;

import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class osagle extends GLSurfaceView {
    private renderers mRenderers = new renderers();
    private scenes mScenes;
    private cameras mCameras;
    private lights mLights;
    private textures mTextures;
    private bumpmaps mBumpmaps;
    private alphamaps mAlphamaps;
    private materials mMaterials;
    private geometries mGeometries;
    private meshes mMeshes;
    private groups mGroups;
    private variables mVariables;
    private animations mAnimations;
    private tilesmaps mTilesMaps;
    private samples mSamples;
    private fonts mFonts;
    private texts mTexts;

    private static long speed = 300;

    String mName;
    String mPath;
    String mCurrentScene;

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

        this.start(mName, this);
        this.waitRenderer();
        setRenderer(this.getRenderer());
    }

    public void waitRenderer() {
        try {
            while (this.getRenderer() == null) {
                debug.log("OSAGLE", "waitRenderer");
                sleep(speed);
            }
        }
        catch (Exception e) {
            debug.log("OSAGLE", "waitRenderer.Exception : "+e.toString());
        }
    }

    public void start(String pName, osagle pOsagle) {
        debug.log("OSAGLE", "start : " + pName);

        mScenes = new scenes(this,speed/2);
        mCameras = new cameras(this,speed/2);
        mMeshes = new meshes(this,speed/2);
        mLights = new lights(this,speed/2);
        mTextures = new textures(this,speed/2);
        mBumpmaps = new bumpmaps(this,speed/2);
        mAlphamaps = new alphamaps(this,speed/2);
        mMaterials = new materials(this,speed/2);
        mTilesMaps = new tilesmaps(this,speed/2);
        mSamples = new samples(this,speed/2);
        mFonts = new fonts(this,speed/2);
        mTexts = new texts(this,speed/2);
        mGeometries = new geometries(this,speed/2);
        mGroups = new groups(this,speed/2);
        mVariables = new variables();
        mAnimations = new animations();

        mRenderers.addRenderer(pName, pOsagle, speed);
    }
    public renderer getRenderer() {
        debug.log("OSAGLE", "getRenderer");
        return mRenderers.getRenderer(mName);
    }
    public void killRenderer(String pName) {
        debug.log("OSAGLE", "killRenderer");
        mRenderers.kill(pName);
    }

    public void setScene(String pFile) {
        debug.log("OSAGLE", "setScene : " + pFile);
        mScenes.todo(pFile);
    }
    public void setCameras(JSONObject pJSON) {
        debug.log("OSAGLE", "setCameras : " + pJSON.toString());
        mCameras.todo(pJSON);
    }
    public void setMaterials(JSONObject pJSON) {
        debug.log("OSAGLE", "setMaterials : " + pJSON.toString());
        mMaterials.todo(pJSON);
    }
    public void setGeometries(JSONObject pJSON) {
        debug.log("OSAGLE", "setGeometries : " + pJSON.toString());
        mGeometries.todo(pJSON);
    }
    public void setMeshes(JSONObject pJSON) {
        debug.log("OSAGLE", "setMeshes : " + pJSON.toString());
        mMeshes.todo(pJSON);
    }
    public texturemap getTexturemap(String pName) { return null; }
    public bumpmap getBumpmap(String pName) { return null; }
    public alphamap getAlphamap(String pName) { return null; }
    public texturecube getTexturecube(String pName) { return null; }
//    public void resize() {
//        debug.log("OSAGLE", "resize: " + this.getLayoutParams().height);
//    }
}
