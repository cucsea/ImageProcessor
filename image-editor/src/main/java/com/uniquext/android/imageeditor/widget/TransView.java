package com.uniquext.android.imageeditor.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.uniquext.android.imageeditor.util.ImageUtils;

public class TransView extends AppCompatImageView {

    /**
     * 图片
     */
    private Bitmap bitmap;
    /**
     * 透明度
     */
    private float mRate = 1f;



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

        Matrix matrix = new Matrix();
        matrix.reset();

        // Paint
        Paint vPaint = new Paint();
        vPaint .setStyle( Paint.Style.STROKE ); //空心
        vPaint .setAlpha( (int)mRate );

        canvas.drawBitmap ( bitmap , matrix , vPaint );  //有透明

    }


    /**
     * 设置透明度
     *
     * @param rate 透明度值
     */
    public void setRate(float rate) {

        this.mRate = rate*2.5f ;
        mRate = mRate <= 0 ? 0f : mRate;
        mRate = mRate > 255 ? 255f : mRate;
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
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 设置图像
     *
     * @param bm 图像
     */
    public void setImageBitmap(Bitmap bm) {
        this.bitmap = bm;
        mRate = 1f;
        invalidate();
    }
}
