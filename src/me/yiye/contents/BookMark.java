package me.yiye.contents;

import org.json.JSONException;
import org.json.JSONObject;

import me.yiye.utils.MLog;


public class BookMark {
	private final static String TAG = "BookMark";
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
	
	public static BookMark buildBookMarkFromJsonObject(JSONObject o) throws JSONException {
		BookMark b = new BookMark();
		b.channelId = o.getString("channelId");
		b.title = o.getString("title");
		b.description = o.getString("description");
		b.url = o.getString("url");
		b.image = o.getString("image");
		b.likeNum = o.getInt("likeNum");
		b.postTime = o.getString("postTime");
		MLog.d(TAG, b.toString());
		return b;
	}
}
