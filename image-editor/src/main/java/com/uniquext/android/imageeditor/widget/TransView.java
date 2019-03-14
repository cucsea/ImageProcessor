package com.uniquext.android.imageeditor.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.uniquext.android.imageeditor.util.ImageUtils;

import java.util.Iterator;
import java.util.Stack;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

public class TransView extends AppCompatImageView {

    /**
     * 透明度
     */
    private float mRate = 1f;

    /**
     * 模糊度是否有变化
     * 复用bitmap
     */
    private boolean mIsRateChanged = false;


    public TransView(Context context) {
        this(context, null);
    }

    public TransView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = getContext().getResources().getDisplayMetrics().density;

    }


    /**
     * 设置模糊度
     *
     * @param rate 模糊值
     */
    public void setRate(float rate) {
        mIsRateChanged = true;
        this.mRate = rate * 0.1f;
        mRate = mRate <= 0 ? 0f : mRate;
        mRate = mRate > 1 ? 1f : mRate;
    }

    public Bitmap getTransBitmap() {
        Bitmap bitmap = ImageUtils.View2Bitmap(this);
        return bitmap;
    }
}
