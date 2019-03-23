package com.osagle;

import android.content.Context;

import com.osagle.engine.alphamaps;
import com.osagle.engine.animations;
import com.osagle.engine.bumpmaps;
import com.osagle.engine.cameras;
import com.osagle.engine.fonts;
import com.osagle.engine.geometries;
import com.osagle.engine.groups;
import com.osagle.engine.lights;
import com.osagle.engine.materials;
import com.osagle.engine.meshes;
import com.osagle.engine.samples;
import com.osagle.engine.scenes;
import com.osagle.engine.texts;
import com.osagle.engine.textures;
import com.osagle.engine.tilesmaps;
import com.osagle.engine.variables;
import com.osagle.renderers.*;

public class osagle  {
    private static renderers mRenderers;
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

    public static long speed = 100;

    public static renderer init(String pName, String pPath, Context pContext) {
        mRenderers = new renderers();
        mScenes = new scenes();
        mCameras = new cameras();
        mLights = new lights();
        mTextures = new textures(pName,100);
        mBumpmaps = new bumpmaps();
        mAlphamaps = new alphamaps();
        mMaterials = new materials();
        mGeometries = new geometries();
        mMeshes = new meshes();
        mGroups = new groups();
        mVariables = new variables();
        mAnimations = new animations();
        mTilesMaps = new tilesmaps();
        mSamples = new samples();
        mFonts = new fonts();
        mTexts = new texts();

        return mRenderers.add(pName, pPath, pContext);
    }
    public static renderer getRenderer(String pName) {
        return mRenderers.get(pName);
    }
    public static void killRenderer(String pName) {
        mRenderers.kill(pName);
    }
}
