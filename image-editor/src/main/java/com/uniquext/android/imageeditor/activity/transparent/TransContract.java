package com.uniquext.android.imageeditor.activity.transparent;

import android.graphics.Bitmap;

import com.uniquext.android.imageeditor.core.BaseContract;

/**
 * @author liu_bjtu
 * @version 1.0
 * @date 2019/03/14 16:58
 * @description 控制界面的契约管理
 */

public interface TransContract {

    interface View extends BaseContract.BaseView<TransContract.Presenter> {

        /**
         * 设置透明度
         *
         * @param progress 进度
         */
        void setRate(float progress);

    }

    interface Presenter extends BaseContract.BasePresenter{
        /**
         * 取消
         */
        void cancel();

        /**
         * 确认
         *
         * @param bitmap bitmap
         */
        void confirm(Bitmap bitmap);

    }

}
