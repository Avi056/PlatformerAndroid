package com.nawlakheavyukta.a10surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Player extends RectF {
    private static final int BMP_COLUMNS = 4;
    private static final int BMP_ROWS = 2;
    private static final int DOWN=0, LEFT=1, RIGHT=0, UP=3;
    private double g = -9.81;
    private int dX, dY, color;
    private boolean onGround;
    private Bitmap bitmap;
    private int currentFrame=0, iconWidth, iconHeight, animationDelay=20;
    public Player() {
        this(2,2, Color.RED);
    }
    public Player(int dX, int dY, int color) {
        this(1,11,11,22,dX,dY,color);
    }

    public Player(float left, float top, float right, float bottom) {
        this(left, top, right, bottom,2,0, Color.RED);
    }
    public Player(RectF r){
        super(r);
    }
    public Player(float left, float top, float right, float bottom, int dX, int dY, int color) {
        super(left, top, right, bottom);
        this.dX = dX;
        this.dY = dY;
        this.color = color;
    }
    public void update(Canvas canvas){
        if(left <= 0){
            offsetTo(0,top);
//            dX = 0;
        }else if (right >= canvas.getWidth()) {
            offsetTo(canvas.getWidth() - this.width(),top);
//            dX = 0;
        }
        if(top <= 0){
            offsetTo(left,0);
//            dY = 0;
        }else if (bottom >= 720) {
            offsetTo(left,720 - this.height());
            onGround = true;
//            dY = 0;
        }
        offset(dX,dY);//moves dX to the right and dY downwards
        if(!(dX==0 && dY==0)) {
            if (animationDelay-- < 0) {//increment to next sprite image after delay
                currentFrame = ++currentFrame % BMP_COLUMNS;//cycles current image with boundary protection
                animationDelay = 10;//arbitrary delay before cycling to next image
            }
        }
        if(onGround == true) {
            setdY(0);
        }
        if(onGround == false){
            int y = (int) (getdY()+0.1);
            setdY(y);
        }
    }

    public void draw(Canvas canvas){
        if(bitmap==null) {//if no bitmap exists draw a red circle
            Paint paint = new Paint();
            paint.setColor(color);//sets its color
            canvas.drawCircle(centerX(), centerY(), width() / 2, paint);//draws circle
        }else {
            iconWidth = bitmap.getWidth() / BMP_COLUMNS;//calculate width of 1 image
            iconHeight = bitmap.getHeight() / BMP_ROWS; //calculate height of 1 image
            int srcX = currentFrame * iconWidth;       //set x of source rectangle inside of bitmap based on current frame
            int srcY = getAnimationRow() * iconHeight; //set y to row of bitmap based on direction
            Rect src = new Rect(srcX, srcY, srcX + iconWidth, srcY + iconHeight);  //defines the rectangle inside of heroBmp to displayed
            canvas.drawBitmap(bitmap,src, this,null); //draw an image
        }
    }

    private int getAnimationRow() {
        if (Math.abs(dX) == dX)
            return RIGHT;  //if x is positive return row 2 for right
        else
            return LEFT;   //if x is negative return row 1 for left
    }

    public int getdX() {
        return dX;
    }

    public void setdX(int dX) {
        this.dX = dX;
    }

    public int getdY() {
        return dY;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void grow(int i) {
        right=right+i;
        bottom=bottom+i;
    }

    public void reset() {
        this.dY=0;
        this.dX=0;
        this.left=1;
        this.right=11;
        this.top=551;
        this.bottom=561;
    }

    public void jump() {
        onGround = false;
    double t = 1.0/60; // (1/FPS)
        while(onGround == false)
        dY = (int) (g*t*t/2);
        onGround = true;
    }
}