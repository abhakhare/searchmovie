package com.movie.abha.network;

import android.util.Log;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class ApiCallback<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M successResult);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    public abstract void onTokenExpire();


    @Override
    public void onComplete() {
        onFinish();
    }


    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            onFailure(msg);
            if(code==401){
                onTokenExpire();
            }
            Log.e("code : ",code+"  ");
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M m) {
        try {
            onSuccess(m);
        } catch (Exception e) {
            onFailure(e.getMessage());
            e.printStackTrace();
        }

    }
}
