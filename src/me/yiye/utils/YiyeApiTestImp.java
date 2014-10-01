package me.yiye.utils;

import java.util.ArrayList;
import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;

public class YiyeApiTestImp implements YiyeApi{

	List<Channel> channels = new ArrayList<Channel>();
	List<ChannelSet> channelsets = new ArrayList<ChannelSet>();
	List<BookMark> technologyChannel = new ArrayList<BookMark>();
	List<BookMark> entertainmentChannel = new ArrayList<BookMark>();
	
	public YiyeApiTestImp() {
		BookMark t = new BookMark();
		t.setImg("http://www.baidu.com/img/baidu_jgylogo3.gif?v=11961390.gif");
		t.setSummary("百度一下 你就知道");
		t.setTitle("百度");
		t.setUrl("www.baidu.com");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setImg("http://ii.ivi.li/jaskni/logo.png");
		t.setSummary("十年寒窗磨一剑，今朝出鞘试锋芒");
		t.setTitle("好玩吧");
		t.setUrl("http://www.9haow.cn/");
		entertainmentChannel.add(t);
		
		Channel c = new Channel();
		c.setTitle("视频娱乐");
		c.setPicurl(null);
		c.setSummary("猫扑 土豆 优库");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("移动开发");
		c.setPicurl(null);
		c.setSummary("android ios wp等");
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
