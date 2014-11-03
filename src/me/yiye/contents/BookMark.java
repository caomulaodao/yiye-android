package me.yiye.contents;


public class BookMark {
	public  String title;
	public  String description;
	public  String url;
	public  String image;
	public  String postUser;
	public  int likeNum;
	public  String postTime;
	public String channelId;
	public String comments;

	@Override
	public String toString() {
		return "[channelId:" + channelId + " title:" + title + " summary:" + description + " url:" + url + " imgurl:" + image
				+ " postTime:" + postTime + " likeNum:" + likeNum + "]";
	}
}
