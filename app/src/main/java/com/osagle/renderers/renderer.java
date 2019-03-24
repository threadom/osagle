package com.osagle.renderers;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.osagle.engine.debug;
import com.osagle.osagle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static java.lang.Thread.sleep;

public class renderer implements GLSurfaceView.Renderer {
    private long mSleep;
    private osagle mOsagle;

    private float mClearColor_Red = 1f;
    private float mClearColor_Green = 0;
    private float mClearColor_Blue = 0;
    private float mClearColor_Alpha = 1f;

    public renderer(osagle pOsagle, long pSleep) {
        debug.log("OSAGLE.renderer", "renderer");
        mOsagle = pOsagle;
        mSleep = pSleep;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        debug.log("OSAGLE.renderer", "onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        debug.log("OSAGLE.renderer", "onSurfaceChanged");
        mOsagle.resize();
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            debug.log("OSAGLE.renderer", "onDrawFrame");
            GLES20.glClearColor(mClearColor_Red,mClearColor_Green,mClearColor_Blue,mClearColor_Alpha);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            sleep(mSleep);
        }
        catch (Exception e) {
            debug.log("OSAGLE.renderer", "onDrawFrame.Exception : "+e.toString());
        }
    }

    public void render(String pName) {

    }

    public void shadowMap(boolean status) { }
    public void setSize(long width, long height) { }

    public void setClearColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        mClearColor_Red = pRed;
        mClearColor_Green = pGreen;
        mClearColor_Blue = pBlue;
        mClearColor_Alpha = pAlpha;
    }
}
