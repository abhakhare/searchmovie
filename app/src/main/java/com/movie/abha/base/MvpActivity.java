package com.movie.abha.base;

import android.os.Bundle;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity{
    protected P mvpPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }



}
