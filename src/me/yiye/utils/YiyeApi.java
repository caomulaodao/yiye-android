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
}
