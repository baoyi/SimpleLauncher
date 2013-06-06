package com.inzi123.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.inzi123.launcher.R;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    // //////////////////////轨迹
    // start/////////////////////////////////////////
    private List<Point> pointTouch;
    private Paint touchBackPaint;
    private Paint touchPaint;
    // 坐标增量
    private float deltX;
    private float deltY;

    private Point pointStart;
    private Point pointEnd;

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private float[] region;

    // //////////////////////////轨迹end//////////////////////////////////////////////////
    private boolean flag;
    // //////////////////////////指纹start//////////////////////////////////////////////////
    Paint bitPaint;
//    Bitmap bit;
    float bitX;
    float bitY;
    float bitW;
    float bitH;
    boolean isDrawBit;

    // //////////////////////////指纹end//////////////////////////////////////////////////
    public MySurfaceView(Context context) {
        super(context);
        holder = this.getHolder();// 获取holder
        pointTouch = new ArrayList<Point>(8);
        touchBackPaint = new Paint();
        touchPaint = new Paint();
        holder.addCallback(this);
        setFocusable(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new MyThread()).start();
        flag = true;
        // 设置背景色
        touchBackPaint.setColor(0xff8a9cae);
        touchBackPaint.setAntiAlias(true);
        touchPaint.setColor(0xffffffff);
        touchPaint.setAntiAlias(true);
        bitPaint = new Paint();
//        bit = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_launcher);
//        bitW = bit.getWidth();
//        bitH = bit.getHeight();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
//        bit.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getRawX();
        float y = event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointTouch.clear();
                isDrawBit = true;
                bitX = x;
                bitY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Point point = new Point(x, y);
                if (pointTouch.size() > 8) {
                    pointTouch.remove(0);
                }
                pointTouch.add(point);
                break;
            case MotionEvent.ACTION_UP:
                pointTouch.clear();
                isDrawBit = false;
                break;
        }
//        return super.onTouchEvent(event);
        return false;
    }

    class MyThread implements Runnable {
        public void run() {
            while (flag) {
                long start = System.currentTimeMillis();
                draw();
                long end = System.currentTimeMillis();
                try {
                    if (end - start < 50) {
                        Thread.sleep(50 - (end - start));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 计算线条增量
     * @param x
     * @param y
     * @return
     */
    public float[] getXY(float x, float y) {
        float addY = (float) (3f * y / FloatMath.sqrt(y * y + x * x));
        float addX = (float) (3f * x / FloatMath.sqrt(y * y + x * x));
        float[] rt = new float[2];
        rt[0] = addX;
        rt[1] = addY;
        return rt;
    }

    public void drawFinger(Canvas canvas) {
        if (isDrawBit) {
//            canvas.drawBitmap(bit, bitX - bitW / 2, bitY - bitY / 2,
//                    bitPaint);
        }
    }

    public void drawNXLine(Canvas canvas) {
        try {
            for (int i = 0; i < pointTouch.size() - 1; i++) {
                deltX = 0;
                deltY = 0;
                // 获取起始坐标
                pointStart = pointTouch.get(i);
                pointEnd = pointTouch.get(i + 1);
                startX = pointStart.x;
                startY = pointStart.y;
                // 获取结束坐标
                stopX = pointEnd.x;
                stopY = pointEnd.y;
                // 计算增量
                region = getXY(stopX - startX, stopY - startY);
                deltX = region[0];
                deltY = region[1];
                // 添加增量
                stopY += deltY;
                stopX += deltX;
                if (i > (pointTouch.size() / 2)) {
                    float width = (pointTouch.size() - i) * 3f + 3f;
                    if (width > 15f) {
                        width = 15f;
                    }
                    touchBackPaint.setStrokeWidth(width);
                    width = (pointTouch.size() - i) * 2f;
                    if (width > 10f) {
                        width = 10f;
                    }
                    touchPaint.setStrokeWidth(width);
                } else {
                    float width = i * 3f + 3f;
                    if (width > 15f) {
                        width = 15f;
                    }
                    touchBackPaint.setStrokeWidth(width);
                    width = i * 2f;
                    if (width > 10f) {
                        width = 10f;
                    }
                    touchPaint.setStrokeWidth(width);
                }
                canvas.drawLine(startX, startY, stopX, stopY,
                        touchBackPaint);
                canvas.drawLine(startX, startY, stopX, stopY, touchPaint);
                pointStart.time++;
                if (pointStart.time > 7)
                    pointTouch.remove(i);
            }
        } catch (Exception ex) {
            return;
        }
    }

    private void draw() {
        // 获取画布
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT,
                    android.graphics.PorterDuff.Mode.CLEAR);
            drawNXLine(canvas);
            drawFinger(canvas);
        }
        // 解锁画布，提交画好的图像
        holder.unlockCanvasAndPost(canvas);
    }
    
    class Point {
        private float x;
        private float y;
        private int time;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
