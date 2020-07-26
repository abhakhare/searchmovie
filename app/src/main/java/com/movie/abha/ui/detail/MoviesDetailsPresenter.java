package com.movie.abha.ui.detail;

import android.content.Context;

import com.movie.abha.Util.Bookmark;
import com.movie.abha.Util.NetworkUtils;
import com.movie.abha.network.ApiCallback;
import com.movie.abha.ui.model.search.TitlesItem;
import com.movie.abha.base.BasePresenter;
import com.movie.abha.ui.model.details.FilmDetails;

import io.reactivex.Observable;

public class MoviesDetailsPresenter extends BasePresenter<MoviesDetailsView> {

    public MoviesDetailsPresenter(MoviesDetailsView view , Context context) {
        attachView(view,context);
    }

    public void getMoviesDetails(final String id){
        if (!NetworkUtils.isNetworkConnected(mvpView.getContext())) {
            mvpView.onFailure("Please check internet");
        }else {

            Observable<FilmDetails> film = apiStores.film(id);

            addSubscription(film, new ApiCallback<FilmDetails>() {

                @Override
                public void onSuccess(FilmDetails filmDetails) {
                    try {
                        mvpView.onSuccess(filmDetails);
                    }catch (Exception e){
                        mvpView.onFailure(e.getMessage());
                    }
                }

                @Override
                public void onFailure(String msg) {
                    mvpView.onFailure(msg);
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onTokenExpire() {
                    mvpView.onTokenExpire();
                }

            });
        }
    }

    public void addMoviesBookmark(){
        TitlesItem titlesItem = new TitlesItem();
        titlesItem.setId(mvpView.getMovieId());
        titlesItem.setTitle(mvpView.getMovieTitle());
        titlesItem.setImage(mvpView.getMovieImage());
        titlesItem.setBookmark(true);
        Bookmark.addBookmark(mvpView.getContext(),titlesItem);
        mvpView.onFailure("Added Bookmark");
    }
}
