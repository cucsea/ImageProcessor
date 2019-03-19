package com.uniquext.android.imageeditor.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class TransView extends AppCompatImageView {

    /**
     * 图片
     */
    private Bitmap bitmap;
    /**
     * 透明度
     */
    private static int mRate =100;

    private int[] mRgb;    //预先保存RGB的信息
    private float rotateDegree=0;


    public TransView(Context context) {
        this(context, null);
    }

    public TransView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        float widthView = getWidth();
        float heightView = getHeight();
        float widthBitmap = bitmap.getWidth();
        float heightBitmap = bitmap.getHeight();

        float scaleX, scaleY, scale;

        if (rotateDegree % 180 == 0) {
            scaleX = widthView / widthBitmap;
            scaleY = heightView / heightBitmap;
        } else {
            scaleX = widthView / heightBitmap;
            scaleY = heightView / widthBitmap;
        }

        scale = Math.min(scaleX, scaleY);
        float offsetX = (widthView - widthBitmap * scale) * 0.5f;
        float offsetY = (heightView - heightBitmap * scale) * 0.5f;

        canvas.rotate(rotateDegree , getWidth() * 0.5f, getHeight() * 0.5f);
        canvas.translate(offsetX, offsetY);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        //第一种方法通过drawcanvas改变透明度
        // Paint
        Paint vPaint = new Paint();
        //vPaint .setStyle( Paint.Style.STROKE ); //空心
        //vPaint .setAlpha( (int)mRate );
        //vPaint.setAlpha(mRate * 255 / 100);
        canvas.drawBitmap ( bitmap, matrix ,vPaint );
       // canvas.drawBitmap ( bitmap, matrix , new Paint(0) );

      //  Log.d("hailong,trans,ondraw", Integer.toString(mRate));
    }



    public void setTransparentBitmap(){
        int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());// 获得图片的ARGB值
        //如果第一次加载图片，保存bitmap的RGB信息于RGBinfo数组中
        //if (first == false){
           // first = true;
            //RGBinfo = argb;
        //}
        for (int i = 0; i < argb.length; i++) {
            if(mRgb[i]!=0)
            argb[i] = ((mRate * 255 / 100) << 24) | (mRgb[i] & 0x00FFFFFF);    //将RGBinfo保存的RGB信息重新填入有透明度信息的数组中
        }
        bitmap = Bitmap.createBitmap(argb, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
       // Log.d("hailong,trans", Integer.toString(mRate));
    }


    /**
     * 设置透明度
     *
     * @param rate 透明度值
     */
    public void setRate(int  rate) {
        mRate = rate ;
        mRate = mRate <= 5? 5:mRate;
        mRate = mRate >=100 ? 100: mRate;
        setTransparentBitmap();
        invalidate();
    }

    /**
     * 获取修改透明度后的bitmap
     *
     * @return bitmap
     */
    public Bitmap getTransBitmap() {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(rotateDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 设置图像,需要知道当前图像的透明度值
     *
     * @param bm 图像
     */
    public void setImageBitmap(Bitmap bm) {
        this.bitmap = bm;
        mRgb = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(mRgb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());// 获得图片的ARGB值;
        invalidate();
    }


    public int getRate()
    {
        return mRate;
    }
}
