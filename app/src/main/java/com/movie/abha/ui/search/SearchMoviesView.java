package com.movie.abha.ui.search;

import android.content.Context;


import com.movie.abha.base.BaseView;
import com.movie.abha.ui.model.search.TitlesItem;
import java.util.List;

public interface SearchMoviesView extends BaseView {
    Context getContext();
    void onSuccess(List<TitlesItem> itemList);
    void onFailure(String message);
}
