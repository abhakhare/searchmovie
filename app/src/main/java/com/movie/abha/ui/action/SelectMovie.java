package com.movie.abha.ui.action;

import com.movie.abha.ui.model.search.TitlesItem;

public interface SelectMovie {
    void onSelectMovie(TitlesItem titlesItem);
    void onBookmarkAdded();
    void onBookmarkRemoved();
}
