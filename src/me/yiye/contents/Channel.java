package me.yiye.contents;
public class Channel {
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
}
