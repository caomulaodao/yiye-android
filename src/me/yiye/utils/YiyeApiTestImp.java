package me.yiye.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import me.yiye.R;
import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;
import android.content.Context;

public class YiyeApiTestImp implements YiyeApi{

	private final static String TAG = "YiyeApiTestImp";
	
	List<Channel> channels = new ArrayList<Channel>();
	List<ChannelSet> channelsets = new ArrayList<ChannelSet>();
	List<BookMark> technologyChannel = new ArrayList<BookMark>();
	List<BookMark> entertainmentChannel = new ArrayList<BookMark>();
	
	public YiyeApiTestImp(Context context) {
		BookMark t = new BookMark();
		
		String[] entertainment = context.getResources().getStringArray(R.array.娱乐);
		/*
        title:\'睿思\',
        summary:\'爱生活，爱睿思\'
        url:\'http://rs.xidian.edu.cn\'
        imgurl:\'\'
        uploaddate:\'2014-10-04\'
        */
		for (String linksjson : entertainment) {
			MLog.d(TAG, "YiyeApiTestImp### linksjson:" + linksjson);
			try {
				JSONObject o = new JSONObject(linksjson);
				t = new BookMark();
				t.setTitle(o.getString("title"));
				t.setImgUrl(o.getString("imgurl"));
				t.setSummary(o.getString("summary"));
				t.setUrl(o.getString("url"));
				t.setUploaddate(DateUtil.dateStringToTimeStamp(o.getString("uploaddate")));
				MLog.d(TAG, t.toString());
				entertainmentChannel.add(t);
			} catch (JSONException e) {
				e.printStackTrace();
				MLog.e(TAG, "YiyeApiTestImp### json error");
			}
		}
		
		String technology[] = context.getResources().getStringArray(R.array.移动开发);
		for (String linksjson : technology) {
			MLog.d(TAG, "YiyeApiTestImp### linksjson:" + linksjson);
			try {
				JSONObject o = new JSONObject(linksjson);
				t = new BookMark();
				t.setTitle(o.getString("title"));
				t.setImgUrl(o.getString("imgurl"));
				t.setSummary(o.getString("summary"));
				t.setUrl(o.getString("url"));
				t.setUploaddate(DateUtil.dateStringToTimeStamp(o.getString("uploaddate")));
				MLog.d(TAG, t.toString());
				technologyChannel.add(t);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		Channel c = new Channel();
		c.setTitle("视频娱乐");
		c.setImgurl("http://www.kdatu.com/2014/10/01/landscape-56/");
		c.setSummary("猫扑 土豆 优库");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("移动开发");
		c.setImgurl("http://img3.imgtn.bdimg.com/it/u=706127875,3022820504&fm=23&gp=0.jpg");
		c.setSummary("android ios wp等");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("情感日常");
		c.setImgurl("http://img.wallba.com/data/Image/2012zwj/9yue/9-10/qtbz/jignxuan/12/2012910114313390.jpg");
		c.setSummary("我尼玛");
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
		} else {
			return technologyChannel;
		}
	}

}
