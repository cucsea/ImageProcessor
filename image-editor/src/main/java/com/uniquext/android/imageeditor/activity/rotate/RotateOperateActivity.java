package com.uniquext.android.imageeditor.activity.rotate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.graphics.Matrix;
import android.widget.TextView;

import com.uniquext.android.imageeditor.R;
import com.uniquext.android.imageeditor.core.AbstractMVPActivity;
import com.uniquext.android.imageeditor.helper.DrawableManager;
import com.uniquext.android.imageeditor.widget.RotateImageView;

import java.util.Locale;

/**
 * @author penghaitao
 * @version 1.0
 * @date 2018/7/23 15:53
 * @description
 */
public class RotateOperateActivity extends AbstractMVPActivity<RotatePresenter> implements RotateContract.View {

    /**
     * 显示图
     */
    private RotateImageView mRotateView;

    private ImageView mRotateViewCopy;

    /**
     * 左转
     */
    private AppCompatImageView mIvRotateLeft;
    /**
     * 右转
     */
    private AppCompatImageView mIvRotateRight;
    /**
     * 滑动条
     */
    private SeekBar mSeekBarAngle;
    /**
     * 角度显示
     */
    private TextView mTextAngle;
    private int mAngle = 0 ;
    /**
     * 取消
     */
    private AppCompatImageView mIvCancel;
    /**
     * 确认
     */
    private AppCompatImageView mIvConfirm;

    @Override
    protected RotatePresenter getPresenter() {
        return new RotatePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rotate;
    }

    @Override
    protected void initView() {
        mRotateView = findViewById(R.id.view_image_rotate);
        mRotateViewCopy = findViewById(R.id.view_image_rotate_copy);
        mIvRotateLeft = findViewById(R.id.iv_rotate_left);
        mIvRotateRight = findViewById(R.id.iv_rotate_right);
        mIvCancel = findViewById(R.id.iv_cancel);
        mIvConfirm = findViewById(R.id.iv_confirm);
        //任意角度旋转
        mSeekBarAngle = findViewById(R.id.seek_bar_angle);
        mTextAngle=findViewById(R.id.rotate_end);

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mIvRotateLeft.setOnClickListener(v -> mPresenter.left());
        mIvRotateRight.setOnClickListener(v -> mPresenter.right());
        mIvCancel.setOnClickListener(v -> mPresenter.cancel());
        mIvConfirm.setOnClickListener(v -> mPresenter.confirm(mRotateView.getImageBitmap()));
        //任意角度旋转
        mSeekBarAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAngle = progress;
                //mAngle = Math.round(mAngle);
                mTextAngle.setText(mAngle+"°");
                rotateAngle(mAngle);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    public void init() {
        Bitmap rotateBitmap = DrawableManager.getInstance().getDrawableBitmap();
        mRotateView.setImageBitmap(rotateBitmap);
        initCopyImg(rotateBitmap);
    }

    //初始化横竖线
    public void initCopyImg( Bitmap copyBitmap ){
        /*由于在onCreate()方法执行完之前，无法通过getWidth()方法获取到控件的宽高（都是0），无法画线。
            所以可以通过添加延时任务，在一段时间后执行画线的操作*/
        mRotateView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int viewWidth = mRotateViewCopy.getWidth();
                int viewHeight = mRotateViewCopy.getHeight();
                Bitmap tmpBitmap = Bitmap.createBitmap(viewWidth,viewHeight,copyBitmap.getConfig());
                Paint mPaint = new Paint();
                mPaint.setColor(Color.GRAY);        //颜色
                mPaint.setStrokeWidth(2);         //设置线条宽度
                mPaint.setAntiAlias(true);          //抗锯齿
                mPaint.setAlpha(50);                //透明度
                Canvas canvas = new Canvas(tmpBitmap);
                /*画竖线*/
                //canvas.drawLine(viewWidth/2,0,viewWidth/2,viewHeight,mPaint);
                //画5条竖线
                for(int i = 0; i< 5 ; i++){
                    canvas.drawLine(viewWidth* i/5,0,viewWidth* i/5,viewHeight,mPaint);
                }
                /*画横线*/
                //canvas.drawLine(0,viewHeight/2,viewWidth,viewHeight/2,mPaint);
                // 画6条横线
                for(int j = 0; j < 6; j++){
                    canvas.drawLine(0,viewHeight*j/6,viewWidth,viewHeight*j/6,mPaint);
                }
                mRotateViewCopy.setImageBitmap(tmpBitmap);
            }
        },100);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void detach() {
        onBackPressed();
    }

    @Override
    public void rotateLeft() {
        mRotateView.rotateLeft();
        //需要更新滑动条的显示
        mAngle=(mAngle-90)%360;
        mAngle=mAngle<0?mAngle+360:mAngle;
        mSeekBarAngle.setProgress(mAngle);
        mTextAngle.setText(mAngle+"°");
    }

    @Override
    public void rotateRight() {
        mRotateView.rotateRight();
        //需要更新滑动条的显示
        mAngle=(mAngle+90)%360;
        mSeekBarAngle.setProgress(mAngle);
        mTextAngle.setText(mAngle+"°");
    }

    @Override
    public void rotateAngle(int angle) {
        mRotateView.rotateAngle(angle);
    }
}
