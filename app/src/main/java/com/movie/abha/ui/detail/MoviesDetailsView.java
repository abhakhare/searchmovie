package com.movie.abha.ui.detail;

import android.content.Context;

import com.movie.abha.base.BaseView;
import com.movie.abha.ui.model.details.FilmDetails;

public interface MoviesDetailsView extends BaseView {
    Context getContext();
    void onSuccess(FilmDetails filmDetails);
    void onFailure(String message);
    String getMovieTitle();
    String getMovieId();
    String getMovieImage();
}
