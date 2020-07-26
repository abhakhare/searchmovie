package com.movie.abha.ui.search;


import android.content.Context;

import com.movie.abha.Util.Bookmark;
import com.movie.abha.Util.NetworkUtils;
import com.movie.abha.network.ApiCallback;
import com.movie.abha.ui.model.search.SearchResponse;
import com.movie.abha.ui.model.search.TitlesItem;
import com.movie.abha.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class SearchMoviesPresenter extends BasePresenter<SearchMoviesView> {

    public SearchMoviesPresenter(SearchMoviesView view , Context context) {
        attachView(view,context);
    }

    public void getMovies(final String text){
        if (!NetworkUtils.isNetworkConnected(mvpView.getContext())) {
            mvpView.onFailure("Please check internet");
        }else {

            Observable<SearchResponse> search = apiStores.search(text);

            addSubscription(search, new ApiCallback<SearchResponse>() {

                @Override
                public void onSuccess(SearchResponse searchResponse) {
                    try {

                        List<TitlesItem> titlesItems = searchResponse.getTitles();
                        List<TitlesItem> bookmarkItem = Bookmark.getBookmarks(mvpView.getContext());

                        for (int i = 0; i < titlesItems.size(); i++){
                            for (int j = 0; j < bookmarkItem.size(); j++){
                                if(titlesItems.get(i).getId().equals(bookmarkItem.get(j).getId())){
                                    titlesItems.get(i).setBookmark(true);
                                    break;
                                }
                            }
                        }

                        mvpView.onSuccess(titlesItems);



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
    public void getBookmarkMovies(boolean selected){
        if(selected){
            mvpView.onSuccess(new ArrayList<TitlesItem>());
        }else {
            mvpView.onSuccess(Bookmark.getBookmarks(mvpView.getContext()));
        }
    }

}
