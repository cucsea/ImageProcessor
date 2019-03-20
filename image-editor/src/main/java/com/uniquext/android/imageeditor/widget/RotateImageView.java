package com.uniquext.android.imageeditor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author penghaitao
 * @version 1.0
 * @date 2018/7/24 9:55
 * @description 旋转
 */
public class RotateImageView extends View {

    /**
     * 图片
     */
    private Bitmap bitmap;
    /**
     * 旋转角度
     */
    private float rotateDegree = 0;

    public RotateImageView(Context context) {
        super(context);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        canvas.rotate(rotateDegree, getWidth() * 0.5f, getHeight() * 0.5f);
        canvas.translate(offsetX, offsetY);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        canvas.drawBitmap(bitmap, matrix, new Paint());
    }

    /**
     * 获取旋转后的bitmap
     *
     * @return bitmap
     */
    public Bitmap getImageBitmap() {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(rotateDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 设置图像
     *
     * @param bm 图像
     */
    public void setImageBitmap(Bitmap bm) {
        this.bitmap = bm;
        rotateDegree = 0;
        invalidate();
    }

    /**
     * 左旋
     */
    public void rotateLeft() {
        rotateDegree = (rotateDegree - 90) % 360;
        invalidate();
    }

    /**
     * 右旋
     */
    public void rotateRight() {
        //不按当前的角度进行计算
        //rotateDegree = (rotateDegree + 90) % 360;
        rotateDegree =testAngle( rotateDegree);
        rotateDegree = (rotateDegree + 90) % 360;
        invalidate();
    }

    /**
     * 任意角度旋转
     */
    public void rotateAngle(float angle) {
        rotateDegree = angle;
        invalidate();
    }

    float testAngle(float curAngle)
    {
        if(curAngle>=0&&curAngle<90)
        {
            return 0;
        }else if(curAngle>=90&&curAngle<180)
        {
            return 90;
        }else if(curAngle>=180&&curAngle<270)
        {
            return 180;
        }else{ return 270;}
    }
}
