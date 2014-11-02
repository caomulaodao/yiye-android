package me.yiye.utils;

import java.util.List;

import me.yiye.contents.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class YiyeApiHelper {
	private final static String TAG = "YiyeApiHelper";
	
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
					c.setTitle(o.getString("title"));
					c.setImgurl(o.getString("imgurl"));
					c.setSummary(o.getString("summary"));
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
}
