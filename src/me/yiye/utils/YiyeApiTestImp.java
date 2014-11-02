package me.yiye.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.yiye.R;
import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class YiyeApiTestImp implements YiyeApi{

	
	private final static String TAG = "YiyeApiTestImp";
	// private final static String TAG = null;
	private Context context;
	List<Channel> channels = new ArrayList<Channel>();
	List<ChannelSet> channelsets = new ArrayList<ChannelSet>();
	List<BookMark> technologyChannel = new ArrayList<BookMark>();
	List<BookMark> entertainmentChannel = new ArrayList<BookMark>();
	List<BookMark> youknowChannel = new ArrayList<BookMark>();
	List<BookMark> knowmoreChannel = new ArrayList<BookMark>();
	
	
	private YiyeApiTestImp(Context context) {
		
		this.context = context;
		addBookMarkToChannel(context,entertainmentChannel,R.raw.entertainment);
		addBookMarkToChannel(context, technologyChannel, R.raw.technology);
		addBookMarkToChannel(context, youknowChannel, R.raw.youknow);
		addBookMarkToChannel(context,knowmoreChannel,R.raw.knowmore);
		addChannelToChannelSet(context,channels,R.raw.channelset);
		
		ChannelSet cs =  new ChannelSet();
		cs.setTitle("科技");
		cs.addLabel("移动");
		cs.addLabel("后台");
		cs.addLabel("前端");
		cs.addLabel("一页");
		channelsets.add(cs);
		
		cs =  new ChannelSet();
		cs.setTitle("娱乐");
		cs.addLabel("视频");
		cs.addLabel("糗百");
		cs.addLabel("万万没想到");
		cs.addLabel("好玩吧");
		cs.addLabel("吃饭");
		cs.addLabel("海贼王");
		cs.addLabel("火影");
		cs.addLabel("中国好声音就要吃香辣里脊");
		channelsets.add(cs);
		
	}

	private void addBookMarkToChannel(Context context, List<BookMark> channel, int res) {
		BookMark t = new BookMark();
		String result = "";
		try {
			InputStream in = context.getResources().openRawResource(res);
			int lenght = in.available();
			byte[] buffer = new byte[lenght];
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MLog.d(TAG, "YiyeApiTestImp### json:" + result);
		JSONArray bookmarkArray = null;
		try {
			bookmarkArray = new JSONArray(result);
			for (int i = 0; i < bookmarkArray.length(); i++) {
				try {
					JSONObject o = bookmarkArray.getJSONObject(i);
					t = new BookMark();
					t.setTitle(o.getString("title"));
					t.setImgUrl(o.getString("imgurl"));
					t.setSummary(o.getString("summary"));
					t.setUrl(o.getString("url"));
					t.setUploaddate(DateUtil.dateStringToTimeStamp(o.getString("uploaddate")));
					MLog.d(TAG, t.toString());
					channel.add(t);
				} catch (JSONException e) {
					e.printStackTrace();
					MLog.e(TAG, "YiyeApiTestImp### json error");
				}
			}
		} catch (JSONException e1) {
			MLog.e(TAG, "YiyeApiTestImp### 解析json失败");
			e1.printStackTrace();
		}
	}
	
	private void addChannelToChannelSet(Context context, List<Channel> channelList, int res) {

		Channel c = new Channel();
		String result = "";
		try {
			InputStream in = context.getResources().openRawResource(res);
			int lenght = in.available();
			byte[] buffer = new byte[lenght];
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MLog.d(TAG, "YiyeApiTestImp### json:" + result);
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
					MLog.e(TAG, "YiyeApiTestImp### json error");
				}
			}
		} catch (JSONException e1) {
			MLog.e(TAG, "YiyeApiTestImp### 解析json失败");
			e1.printStackTrace();
		}
	}
	
	@Override
	public List<Channel> getBookedChannels() {
		return channels;
	}

	@Override
	public List<ChannelSet> getChannelSets() {
		return channelsets;
	}

	public List<Channel> getChannelsByLabel(String label){
		return channels;
	}
	
	@Override
	public List<BookMark> getBookMarksByChannel(Channel channel) {
		if(channel.getTitle().equals("视频娱乐")) {
			return entertainmentChannel;
		} else if(channel.getTitle().equals("移动开发")){
			return technologyChannel;
		} else if(channel.getTitle().equals("新闻社交")){
			return knowmoreChannel;
		} else {
			return youknowChannel;
		} 
	}

	public String login(String email,String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", keyword));
		return NetworkUtil.post(context,YiyeApi.LOGIN,params);
	}
	
	public String getUserInfo() {
		return NetworkUtil.get(context, YiyeApi.HOST, YiyeApi.ME);
	}
}
