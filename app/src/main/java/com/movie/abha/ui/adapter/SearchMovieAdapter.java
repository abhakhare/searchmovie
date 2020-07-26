package com.movie.abha.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.movie.abha.Util.Bookmark;
import com.movie.abha.ui.search.SearchMoviesActivity;
import com.movie.user.R;
import com.movie.abha.ui.action.SelectMovie;
import com.movie.abha.ui.model.search.TitlesItem;

import java.util.ArrayList;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {

    private ArrayList<TitlesItem> mItemList;
    private Context context;
    private SelectMovie selectMovie;

    public SearchMovieAdapter(ArrayList<TitlesItem> mItemList , SelectMovie selectMovie) {
        this.mItemList = mItemList;
        this.selectMovie = selectMovie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        final View view = LayoutInflater.from(context).inflate(R.layout.custom_search_movie, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final TitlesItem titlesItem = mItemList.get(position);

            holder.main_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectMovie.onSelectMovie(titlesItem);
                }
            });

            holder.bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!titlesItem.isBookmark()) {
                            titlesItem.setBookmark(true);
                            Bookmark.addBookmark(context, titlesItem);
                            selectMovie.onBookmarkAdded();
                            holder.bookmark.setBackgroundResource(R.drawable.ic_bookmark);
                        } else {
                            if(SearchMoviesActivity.isBookmarkSelected){
                                Bookmark.removeBookmark(context, titlesItem);
                                mItemList.remove(position);
                                notifyDataSetChanged();
                            }else {
                                titlesItem.setBookmark(false);
                                Bookmark.removeBookmark(context, titlesItem);
                                selectMovie.onBookmarkRemoved();
                                holder.bookmark.setBackgroundResource(R.drawable.ic_bookmark_border_black);
                            }

                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            });


            holder.name.setText(titlesItem.getTitle());

            Glide.with(context)
                    .load(titlesItem.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_local_movies)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_local_movies)
                    .into(holder.movie_image);
            holder.movie_image.setAdjustViewBounds(true);

            if(titlesItem.isBookmark()){
                holder.bookmark.setBackgroundResource(R.drawable.ic_bookmark);
            }else{
                holder.bookmark.setBackgroundResource(R.drawable.ic_bookmark_border_black);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movie_image,bookmark;
        TextView name;
        RelativeLayout main_list;
        public ViewHolder(View parent) {
            super(parent);
            name = parent.findViewById(R.id.name);
            movie_image = parent.findViewById(R.id.movie_image);
            bookmark = parent.findViewById(R.id.bookmark);
            main_list = parent.findViewById(R.id.main_list);
        }
    }

}
