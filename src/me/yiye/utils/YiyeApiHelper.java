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
		JSONArray channelArray = null;
		try {
			channelArray = new JSONArray(result);
			for (int i = 0; i < channelArray.length(); i++) {
				JSONObject o = channelArray.getJSONObject(i);
				c = Channel.buildFromJosnObject(o);
				channelList.add(c);
				SQLManager.saveChannel(context, c);
			}
		} catch (JSONException e) {
			MLog.e(TAG, "addChannelToChannelSet### 解析json失败");
			e.printStackTrace();
		}
	}
	
	public static void addBookMarkToBookMarkList(Context context, List<BookMark> bookmarkList, String result) {
		BookMark b = new BookMark();
		MLog.d(TAG, "addBookMarkToBookMarkList### json:" + result);
		JSONArray bookmarkArray = null;
		try {
			bookmarkArray = new JSONArray(result);
			for (int i = 0; i < bookmarkArray.length(); i++) {
				JSONObject o = bookmarkArray.getJSONObject(i);
				b = BookMark.buildBookMarkFromJsonObject(o);
				bookmarkList.add(b);
				SQLManager.saveBookMark(context, b);
			}
		} catch (JSONException e) {
			MLog.e(TAG, "addChannelToChannelSet### 解析json失败");
			e.printStackTrace();
		}
	}
}
