package me.yiye.contents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	
	private Bitmap pic;
	private static Bitmap defaultpic;
	
	public static void setDefaultPic(Context context,int res){ 
		defaultpic = BitmapFactory.decodeResource(context.getResources(), res);
	}
	
	public Bitmap getPic() {
		if(pic != null) {
			return pic;
		}
		
		return defaultpic;
	}
}
