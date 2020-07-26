package com.movie.abha.Util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.movie.abha.ui.model.search.TitlesItem;

import java.util.ArrayList;
import java.util.List;

public class Bookmark {

    private static boolean isLoaded = false;
    private static List<TitlesItem> titlesItems = new ArrayList<TitlesItem>();
    private static final String KEY_BOOKMARK = "KEY_BOOKMARK";

    public static void addBookmark(Context context, TitlesItem titlesItem){

        boolean alreadyAdded = false;
        for (int i = 0; i < titlesItems.size(); i++){
            if (titlesItems.get(i).getId().equals(titlesItem.getId())){
                alreadyAdded = true;
            }
        }

        if(!alreadyAdded){
            titlesItems.add(titlesItem);
            JsonArray saveData = new Gson().toJsonTree(titlesItems).getAsJsonArray();
            Utility.setSharedPreference(context,KEY_BOOKMARK,saveData.toString());
        }

    }

    public static void removeBookmark(Context context, TitlesItem titlesItem){

        boolean alreadyAdded = false;
        int removeItem = 0;
        for (int i = 0; i < titlesItems.size(); i++){
            if (titlesItems.get(i).getId().equals(titlesItem.getId())){
                alreadyAdded = true;
                removeItem = i;
            }
        }

        if(alreadyAdded){
            titlesItems.remove(removeItem);
            JsonArray saveData = new Gson().toJsonTree(titlesItems).getAsJsonArray();
            Utility.setSharedPreference(context,KEY_BOOKMARK,saveData.toString());
        }

    }

    public static List<TitlesItem> getBookmarks(Context context){

        if(!isLoaded){
            Gson gson = new Gson();
            String listData = Utility.getSharedPreferences(context,KEY_BOOKMARK);
            List<TitlesItem>  bookmarks = gson.fromJson(listData,  new TypeToken<List<TitlesItem>>(){}.getType());

            if(bookmarks!=null){
                titlesItems.clear();
                titlesItems.addAll(bookmarks);
            }

        }

        return titlesItems;
    }

}
