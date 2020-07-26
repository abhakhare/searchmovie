package com.movie.abha.ui.model.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse{
	private List<TitlesItem> titles = new ArrayList<>();

	public List<TitlesItem> getTitles(){
		return titles;
	}
}