package com.example.holeinone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.osagle.osagle;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class holeinone extends AppCompatActivity {
    private osagle gl_display;
    private osagle gl_control;
    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        holeinone.context = getApplicationContext();

        gl_display = (osagle) findViewById(R.id.gl_display);
        gl_control = (osagle) findViewById(R.id.gl_control);

        gl_display.setScene("example1", "example1");
        gl_control.setScene("example2", "example2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gl_display.onResume();
        gl_control.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gl_display.onPause();
        gl_control.onPause();
    }

    public static Context getAppContext() {
        return holeinone.context;
    }
}
