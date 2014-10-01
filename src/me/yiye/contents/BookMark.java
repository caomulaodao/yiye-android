package me.yiye.contents;

import android.graphics.Bitmap;

public class BookMark {
	private String title;
	private String summary;
	private String url;
	private String imgurl;
	private String uploaddate;
	private Bitmap img;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return imgurl;
	}

	public void setImg(String img) {
		this.imgurl = img;
	}

	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public Bitmap loadImage() {
		return img;
	}
}
