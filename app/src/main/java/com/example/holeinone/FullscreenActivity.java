package com.example.holeinone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.osagle.view;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private view openGLView1;
    private view openGLView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        openGLView1 = (view) findViewById(R.id.openGLView1);
        openGLView2 = (view) findViewById(R.id.openGLView2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openGLView1.onResume();
        openGLView2.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLView1.onPause();
        openGLView2.onPause();
    }
}
