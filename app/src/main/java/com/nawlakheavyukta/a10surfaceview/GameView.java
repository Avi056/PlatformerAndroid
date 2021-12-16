package com.nawlakheavyukta.a10surfaceview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements Runnable {

    private boolean mRunning;
    private Thread mGameThread = null;
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    int x = 0;
    Paint p = new Paint();
    int dY = 0;
    int dX = 0;
    Player player = new Player();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        player.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.potatospritesheet));
        player.grow(65);
    }

    @Override
    public void run() {
        Canvas canvas;
        long frameStartTime;
        long frameTime;
        final int FPS = 60;
        while (mRunning) {
            if (mSurfaceHolder == null){return;}
            // If valid drawing surface...
            if (mSurfaceHolder.getSurface().isValid()) {
                //record start time for current run
                frameStartTime = System.nanoTime();
                //access the canvas
                canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.save();
                    // clear the screen using black
                    canvas.drawARGB(255,135,206,235);
                    try {
                        // Your drawing here
                        drawMe(canvas);
                    } finally {  //unlock and release canvas
                        canvas.restore();
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                // calculate the time required to draw the frame in ms
                frameTime = (System.nanoTime() - frameStartTime) / 1000000;

                if (frameTime < (1000/FPS)) // if faster than the FPS -> wait until FPS matched
                {
                    try {
                        Thread.sleep((int)(1000/FPS) - frameTime);
                    } catch (InterruptedException e) {}
                }
            }
        }
    }

    private void drawMe(Canvas canvas) {
        p.setColor(Color.BLUE);

        player.update(canvas);
        player.draw(canvas);
        invalidate();
    }


    /**
     * Called by MainActivity.onPause() to stop the thread.
     */
    public void pause() {
        mRunning = false;
        try {
            // Stop the thread == rejoin the main thread.
            mGameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called by MainActivity.onResume() to start a thread.
     */
    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }
    @Override  //resets x to 0 on touch
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x = 0;
        }
        return true;
    }

    public void left(View view){
        player.setdX(-2);
    }
    public void right(View view){
        player.setdX(2);
    }

    public void up(View view) {
        player.jump();
    }
}
