package com.uniquext.android.imageeditor.activity.transparent;


import android.graphics.Bitmap;

import com.uniquext.android.imageeditor.helper.DrawableManager;

public class TransPresenter implements TransContract.Presenter{

    /**
     * Contract View
     */
    private TransContract.View mContractView;

    TransPresenter(TransContract.View view) {
        mContractView = view;
    }


    @Override
    public void cancel() {
        mContractView.detach();
    }





    @Override
    public void confirm(Bitmap bitmap) {
        DrawableManager.getInstance().pushBitmap(bitmap);
        mContractView.detach();
    }


    @Override
    public void start() {
        mContractView.init();
    }

    @Override
    public void recycle() {
        mContractView = null;
    }

}
