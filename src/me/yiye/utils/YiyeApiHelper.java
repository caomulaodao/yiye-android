package me.yiye.utils;

import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class YiyeApiHelper {
	private final static String TAG = "YiyeApiHelper";
	public String channelId;
	public String name;
	public String logo;
	public String type;
	public int news;
	public String lastTime;
	
	public static void addChannelToChannelSet(Context context, List<Channel> channelList, String result) {

		Channel c = new Channel();

		MLog.d(TAG, "addChannelToChannelSet### json:" + result);
		JSONArray bookmarkArray = null;
		try {
			bookmarkArray = new JSONArray(result);
			for (int i = 0; i < bookmarkArray.length(); i++) {
				try {
					JSONObject o = bookmarkArray.getJSONObject(i);
					c = new Channel();
					c.channelId = o.getString("channelId");
					c.name = o.getString("name");
					c.logo = o.getString("logo");
					c.type = o.getString("type");
					c.lastTime = o.getString("lastTime");
					c.news = o.getInt("news");
					MLog.d(TAG, c.toString());
					channelList.add(c);
				} catch (JSONException e) {
					e.printStackTrace();
					MLog.e(TAG, "addChannelToChannelSet### json error");
				}
			}
		} catch (JSONException e1) {
			MLog.e(TAG, "addChannelToChannelSet### 解析json失败");
			e1.printStackTrace();
		}
	}
	
	public static void addBookMarkToBookMarkList(Context context, List<BookMark> bookmarkList, String result) {
		BookMark b= new BookMark();

		MLog.d(TAG, "addBookMarkToBookMarkList### json:" + result);
		JSONArray bookmarkArray = null;
		try {
			bookmarkArray = new JSONArray(result);
			for (int i = 0; i < bookmarkArray.length(); i++) {
				try {
					JSONObject o = bookmarkArray.getJSONObject(i);
					b = new BookMark();
					b.channelId = o.getString("channelId");
					b.title = o.getString("title");
					b.description = o.getString("description");
					b.url = o.getString("url");
					b.image = o.getString("image");
					b.likeNum = o.getInt("likeNum");
					b.postTime = o.getString("postTime");
					MLog.d(TAG, b.toString());
					bookmarkList.add(b);
				} catch (JSONException e) {
					e.printStackTrace();
					MLog.e(TAG, "addChannelToChannelSet### json error");
				}
			}
		} catch (JSONException e1) {
			MLog.e(TAG, "addChannelToChannelSet### 解析json失败");
			e1.printStackTrace();
		}
		
	}
}
