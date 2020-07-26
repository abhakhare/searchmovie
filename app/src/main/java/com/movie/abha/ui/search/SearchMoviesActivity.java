package com.movie.abha.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.movie.abha.Constants.Constant;
import com.movie.user.R;
import com.movie.abha.base.MvpActivity;
import com.movie.abha.ui.action.SelectMovie;
import com.movie.abha.ui.adapter.SearchMovieAdapter;
import com.movie.abha.ui.detail.MoviesDetailsActivity;
import com.movie.abha.ui.model.search.TitlesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchMoviesActivity extends MvpActivity<SearchMoviesPresenter> implements SearchMoviesView, SelectMovie, View.OnClickListener {

    private static Timer timer;
    private static SearchMovieAdapter searchMovieAdapter;
    private static ArrayList<TitlesItem> itemList = new ArrayList<>();
    private static boolean onSearchtxt=true;

    private RecyclerView recyclerView;
    private TextView noData;
    private EditText searchText;
    private SwipeRefreshLayout swiperefresh;
    public static boolean isBookmarkSelected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        init();
    }

    private void init(){

         recyclerView = findViewById(R.id.recyclerView);
         noData  = findViewById(R.id.no_data);
         searchText = findViewById(R.id.search_text);
         swiperefresh = findViewById(R.id.swiperefresh);
         findViewById(R.id.bookmark).setOnClickListener(this);

        searchMovieAdapter=new SearchMovieAdapter(itemList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(searchMovieAdapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if (editable.length() > 2) {
                    try {
                        isBookmarkSelected = false;
                        findViewById(R.id.bookmark).setBackgroundResource(R.drawable.ic_bookmark_border_black);
                    }catch (Exception e){e.printStackTrace();}
                    if(onSearchtxt) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                               runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiperefresh.setRefreshing(true);
                                        mvpPresenter.getMovies(editable.toString());
                                    }
                                });

                            }
                        }, Constant.ADDRESS_SEARCH_DELAY);
                    }
                } else {

                    if (timer != null) {
                        timer.cancel();
                    }

                    itemList.clear();
                    searchMovieAdapter.notifyDataSetChanged();
                }
            }
        });

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(false);
            }
        });
        // lower version crash handle
        try{
            findViewById(R.id.bookmark).setBackgroundResource(R.drawable.ic_bookmark_border_black);
        }catch (Exception e){e.printStackTrace();}
    }


    @Override
    protected SearchMoviesPresenter createPresenter() {
        return new SearchMoviesPresenter(this,this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess(List<TitlesItem> mitemList) {
        swiperefresh.setRefreshing(false);
        itemList.clear();
        if (mitemList.size() > 0) {
            itemList.addAll(mitemList);
            noData.setVisibility(View.GONE);
        }else {
            noData.setVisibility(View.VISIBLE);
        }
        searchMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String mesage) {
        snackBarBottom(R.id.main_layout,mesage);
        swiperefresh.setRefreshing(false);
    }

    @Override
    public void onSelectMovie(TitlesItem titlesItem) {
        Intent intent = new Intent(this, MoviesDetailsActivity.class);
        intent.putExtra("id",titlesItem.getId());
        intent.putExtra("image",titlesItem.getImage());
        intent.putExtra("title",titlesItem.getTitle());
        startActivity(intent);
    }

    @Override
    public void onBookmarkAdded() {
        snackBarBottom(R.id.main_layout,"Added Bookmark");
    }

    @Override
    public void onBookmarkRemoved() {
        snackBarBottom(R.id.main_layout,"Removed Bookmark");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bookmark){
            // lower version crash handle
            try{
                if(!isBookmarkSelected) {
                    v.setBackgroundResource(R.drawable.ic_bookmark);
                }else{
                    v.setBackgroundResource(R.drawable.ic_bookmark_border_black);
                }
            }catch (Exception e){e.printStackTrace();}

            mvpPresenter.getBookmarkMovies(isBookmarkSelected);
            isBookmarkSelected = !isBookmarkSelected;
        }
    }
}
