package me.yiye.contents;

import java.util.ArrayList;
import java.util.List;

public class ChannelSet {
	private String title;
	private List<String> labels = new ArrayList<String>();
	private String picurl;
	
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
}
