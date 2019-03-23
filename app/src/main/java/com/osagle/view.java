package com.osagle;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.holeinone.R;
import com.osagle.renderers.renderer;

public class view extends GLSurfaceView {
    String mName = "";
    String mPath = "";

    public view(Context context) {
        super(context);
        this.init();
    }

    public view(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.view, 0, 0);
        mName = a.getString(R.styleable.view_name);
        mPath = a.getString(R.styleable.view_path);
        a.recycle();

        this.init();
    }

    private void init() {
        renderer aRenderer;

        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        aRenderer = osagle.init(mName, mPath, getContext());
        setRenderer(aRenderer);
    }
}