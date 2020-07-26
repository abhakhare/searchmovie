package com.movie.abha.base;


import android.content.Context;

import com.movie.abha.network.ApiClient;
import com.movie.abha.network.ApiStores;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeDisposable mcompositeDisposable;

    public void attachView(V mvpView, Context context) {
        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit(context).create(ApiStores.class);

    }

    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    //RXjava unregisters to avoid memory leaks
    public void onUnsubscribe() {
        if (mcompositeDisposable != null && mcompositeDisposable.isDisposed()) {
            mcompositeDisposable.clear();
        }
    }


    public void addSubscription(final Observable observable, final Observer observer) {

        if (mcompositeDisposable == null) {
            mcompositeDisposable = new CompositeDisposable();
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mcompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Object o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });

    }

}
