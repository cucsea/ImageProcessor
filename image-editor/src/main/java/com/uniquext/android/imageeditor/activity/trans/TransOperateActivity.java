package com.uniquext.android.imageeditor.activity.trans;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.uniquext.android.imageeditor.R;
import com.uniquext.android.imageeditor.core.AbstractMVPActivity;
import com.uniquext.android.imageeditor.helper.DrawableManager;
import com.uniquext.android.imageeditor.widget.TransView;

import java.util.Locale;

public class TransOperateActivity extends AbstractMVPActivity<TransPresenter> implements TransContract.View {

    /**
     * 显示图
     */
    private TransView  mTransView;
    /**
     * 透明度
     */
    private SeekBar mSeekTrans;
    /**
     * 透明度值
     */
    private TextView mTvTransRate;
    /**
     * 取消
     */
    private AppCompatImageView mIvCancel;
    /**
     * 确认
     */
    private AppCompatImageView mIvConfirm;

    @Override
    protected TransPresenter getPresenter() {
        return new TransPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trans;
    }

    @Override
    protected void initView() {
        mTransView = findViewById(R.id.view_image_trans);
        mSeekTrans= findViewById(R.id.seek_bar_rate_trans);
        mTvTransRate = findViewById(R.id.tv_rate_trans);

        mIvCancel = findViewById(R.id.iv_cancel);
        mIvConfirm = findViewById(R.id.iv_confirm);

    }
    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
    }

    @Override
    protected void initEvent() {

        mIvCancel.setOnClickListener(v -> mPresenter.cancel());
        mIvConfirm.setOnClickListener(v -> mPresenter.confirm(mTransView.getTransBitmap()));

        //滑动条事件监听
        mSeekTrans.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //图片透明度改变（canvas）
                mTvTransRate.setText(String.format(Locale.CHINA, "%d%%", progress));
                setRate(seekBar.getProgress());

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
    public void setRate(int progress) {
        mTransView.setRate(progress);
    }

    @Override
    public void init() {
        //设置初始图像、透明度
        mTransView.setImageBitmap(DrawableManager.getInstance().getDrawableBitmap());
        mSeekTrans.setProgress( mTransView.getRate());
        mTvTransRate.setText(String.format(Locale.CHINA, "%d%%", mSeekTrans.getProgress()));
        setRate(mSeekTrans.getProgress());

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void detach() {
        onBackPressed();
    }
}
