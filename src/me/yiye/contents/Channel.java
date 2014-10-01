package me.yiye.contents;

import me.yiye.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Channel {
	private String picurl;
	private String title;
	private String summary;
	private Bitmap pic;
	private static Bitmap defaultpic;
	
	public static void setDefaultPic(Context context,int res){ 
		defaultpic = BitmapFactory.decodeResource(context.getResources(), res);
	}
	
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
	
	public String getPicurl() {
		return picurl;
	}
	
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	public Bitmap getPic() {
		if(pic != null) {
			return pic;
		}
		
		return defaultpic;
	}
}
