package com.movie.abha.ui.model.search;

public class TitlesItem{
	private String image;
	private String id;
	private String title;
	private boolean bookmark;

	public String getImage(){
		return image;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isBookmark() {
		return bookmark;
	}

	public void setBookmark(boolean bookmark) {
		this.bookmark = bookmark;
	}
}
