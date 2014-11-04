package me.yiye.contents;

import me.yiye.utils.MLog;

import org.json.JSONException;
import org.json.JSONObject;

public class Channel {
	private final static String TAG = "Channel";
	public String channelId;
	public String name;
	public String logo;
	public String type;
	public int news;
	public String lastTime;
	
	@Override
	public String toString() {
		return "[channelId:" + channelId + " name:" + name + " logo:" + logo + " type:" + type + " news:" + news + "lasttime:" + lastTime + "]";
	}
	
	public static Channel buildFromJosnObject(JSONObject o) throws JSONException {
		Channel c = new Channel();
		c.channelId = o.getString("channelId");
		c.name = o.getString("name");
		c.logo = o.getString("logo");
		c.type = o.getString("type");
		c.lastTime = o.getString("lastTime");
		c.news = o.getInt("news");
		MLog.d(TAG, c.toString());
		return c;
	}
}
