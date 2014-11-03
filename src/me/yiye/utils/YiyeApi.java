package me.yiye.utils;

import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;

public interface YiyeApi {
	public List<Channel> getBookedChannels(); // 获取订阅的频道
	public List<ChannelSet> getChannelSets();	// 获取频道集合
	public List<Channel> getChannelsByLabel(String label);	// 由标签获取频道
	public List<BookMark> getBookMarksByChannel(Channel channel);	// 获取频道中的书签

	public String login(String email,String keyword);
	public String getUserInfo();
	
	public final static String TESTHOST = "http://192.168.199.105:3000";
	public final static String BOOKEDCHANNELS = "/api/channel/all";
	public final static String LOGIN = "/api/account/login";
	public final static String USERINFO = "/api/user/me";
}