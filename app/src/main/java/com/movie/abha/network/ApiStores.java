package com.movie.abha.network;


import com.movie.abha.ui.model.details.FilmDetails;
import com.movie.abha.ui.model.search.SearchResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiStores {

    @GET("search/{name}")
    Observable<SearchResponse> search(@Path("name") String name);


    @GET("film/{movie_id}")
    Observable<FilmDetails> film(@Path("movie_id") String movie_id);

}
