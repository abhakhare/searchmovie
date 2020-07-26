package com.movie.abha.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.movie.abha.Util.KeyboardUtils;
import com.movie.abha.Util.NetworkUtils;
import com.movie.abha.Util.SnackbarUtils;
import com.movie.abha.network.ApiClient;
import com.movie.abha.network.ApiStores;
import com.movie.abha.ui.SplashActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;

public class BaseActivity extends AppCompatActivity implements BaseView{


    public ProgressDialog progressDialog;
    public Activity mActivity;
    private List<Call> calls;
    public Context context;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
        context = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
        context = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
        context = this;

    }


    @Override
    protected void onDestroy() {
        callCancel();
        super.onDestroy();
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit(getActivityContext()).create(ApiStores.class);
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    public ProgressDialog showProgressDialog(CharSequence message, boolean cancelable) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
        return progressDialog;
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isNetworkConnected(){
        return NetworkUtils.isNetworkConnected(this);
    }

    @Override
    public void hideSoftKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void showSoftKeyboard(EditText editText) {
        KeyboardUtils.showSoftInput(editText,this);
    }

    @Override
    public Context getActivityContext() {
        return context;
    }

    @Override
    public void snackBarBottom(int view_id, String message) {
        SnackbarUtils.snackBarBottom(findViewById(view_id),message);
    }

    @Override
    public void snackBarTop(int view_id, String message) {
        SnackbarUtils.snackBarTop(findViewById(view_id),message);
    }

    @Override
    public void onTokenExpire() {

        Intent intent=new Intent(getActivityContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showError(String message){
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

}
