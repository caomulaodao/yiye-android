package me.yiye.contents;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ChannelSet {
	private String title;
	private List<String> labels = new ArrayList<String>();
	private String picurl;
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

	public List<String> getLabels() {
		return labels;
	}

	public void addLabel(String label){ 
		labels.add(label);
	}
	
	public void setLabels(List<String> labels) {
		this.labels = labels;
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
