package com.nawlakheavyukta.a10surfaceview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    GameView gem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gem = findViewById(R.id.game);
    }
    /**
     * Pauses game when activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gem.pause();
    }

    /**
     * Resumes game when activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gem.resume();
    }

    public void left(View view) {
        gem.left(view);
    }

    public void right(View view) {
        gem.right(view);
    }

    public void up(View view) {
        gem.up(view);
    }
}