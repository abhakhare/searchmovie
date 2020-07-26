package com.movie.abha.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;

public interface BaseView {

    //Progressbar
    ProgressDialog showProgressDialog(CharSequence message, boolean cancelable);
    void hideProgressDialog();

    //check network connectivity
    boolean isNetworkConnected();

    //toggle keyboard
    void hideSoftKeyboard();
    void showSoftKeyboard(EditText view);

    Context getActivityContext();


    void snackBarBottom(int view_id, String message);
    void snackBarTop(int view_id, String message);

    void onTokenExpire();

}
