package me.yiye.utils;

import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;

public interface YiyeApi {
	public List<Channel> getBookedChannels();
	public List<ChannelSet> getChannelSets();
	public List<Channel> getChannelsByLabel(String label);
	public List<BookMark> getBookMarksByChannel(Channel channel);
	
	public final static String LOGIN = "http://192.168.199.105:3000/login";
	public final static String HOST = "http://192.168.199.105:3000/";
	public final static String ME = "users/me";
	
	public String login(String email,String keyword);
	public String getUserInfo();
}
