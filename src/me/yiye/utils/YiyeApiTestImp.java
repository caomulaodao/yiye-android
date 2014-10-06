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
	
	List<Channel> channels = new ArrayList<Channel>();
	List<ChannelSet> channelsets = new ArrayList<ChannelSet>();
	List<BookMark> technologyChannel = new ArrayList<BookMark>();
	List<BookMark> entertainmentChannel = new ArrayList<BookMark>();
	List<BookMark> youknowChannel = new ArrayList<BookMark>();
	
	public YiyeApiTestImp(Context context) {
		addBookMarkToChannel(context,entertainmentChannel,R.raw.entertainment);
		addBookMarkToChannel(context, technologyChannel, R.raw.technology);
		addBookMarkToChannel(context, youknowChannel, R.raw.youknow);
		
		Channel c = new Channel();
		c.setTitle("视频娱乐");
		c.setImgurl("http://img0.imgtn.bdimg.com/it/u=3991146073,807626457&fm=21&gp=0.jpg");
		c.setSummary("猫扑 土豆 优库");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("移动开发");
		c.setImgurl("http://img3.imgtn.bdimg.com/it/u=706127875,3022820504&fm=23&gp=0.jpg");
		c.setSummary("android ios wp等");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("你懂的");
		c.setImgurl("http://img.wallba.com/data/Image/2012zwj/9yue/9-10/qtbz/jignxuan/12/2012910114313390.jpg");
		c.setSummary("我就端着碗看着不说话");
		channels.add(c);
		
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
		} else {
			return youknowChannel;
		}
	}

	public void login(Context context,String email,String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("keyword", keyword));
		NetworkUtil.post(context,YiyeApi.LOGIN,params);
	}
}
