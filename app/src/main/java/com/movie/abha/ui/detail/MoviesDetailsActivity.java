package com.movie.abha.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.movie.abha.base.MvpActivity;
import com.movie.abha.ui.model.details.FilmDetails;
import com.movie.user.R;

public class MoviesDetailsActivity extends MvpActivity<MoviesDetailsPresenter> implements MoviesDetailsView, View.OnClickListener {

    private SwipeRefreshLayout swiperefresh;

    ImageView movie_image;
    TextView name,rating_votes,votes,duration,details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setToolbar();
        init();
    }

    private void init(){
        swiperefresh = findViewById(R.id.swiperefresh);
        movie_image = findViewById(R.id.movie_image);
        name = findViewById(R.id.name);
        rating_votes = findViewById(R.id.rating_votes);
        votes = findViewById(R.id.votes);
        duration = findViewById(R.id.duration);
        details = findViewById(R.id.details);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mvpPresenter.getMoviesDetails(getIntent().getStringExtra("id"));
            }
        });

        findViewById(R.id.bookmark).setOnClickListener(this);

        name.setText(getIntent().getStringExtra("title"));
        movie_image.setAdjustViewBounds(true);

        Glide.with(context)
                .load(getIntent().getStringExtra("image"))
                .centerCrop()
                .placeholder(R.drawable.ic_local_movies)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_local_movies)
                .into(movie_image);

        swiperefresh.setRefreshing(true);
        mvpPresenter.getMoviesDetails(getIntent().getStringExtra("id"));

    }


    private void setToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected MoviesDetailsPresenter createPresenter() {
        return new MoviesDetailsPresenter(this,this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess(FilmDetails filmDetails) {
        swiperefresh.setRefreshing(false);
        try{

            name.setText(getIntent().getStringExtra("title"));
            rating_votes.setText(filmDetails.getRating());
            votes.setText(filmDetails.getRatingVotes()+" votes");
            duration.setText("Duration: "+filmDetails.getLength());
            details.setText(filmDetails.getPlot());
            findViewById(R.id.bookmark).setOnClickListener(this);

            movie_image.setAdjustViewBounds(true);
            Glide.with(context)
                    .load(getIntent().getStringExtra("image"))
                    .centerCrop()
                    .placeholder(R.drawable.ic_local_movies)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_local_movies)
                    .into(movie_image);

        }catch (Exception e){e.printStackTrace();}
    }


    @Override
    public void onFailure(String mesage) {
        snackBarBottom(R.id.main_layout,mesage);
        swiperefresh.setRefreshing(false);
    }

    @Override
    public String getMovieId() {
        return getIntent().getStringExtra("id");
    }

    @Override
    public String getMovieImage() {
        return getIntent().getStringExtra("image");
    }

    @Override
    public String getMovieTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bookmark) {
            mvpPresenter.addMoviesBookmark();
        }
    }
}
