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
		t.setImgUrl("http://www.baidu.com/img/baidu_jgylogo3.gif?v=11961390.gif");
		t.setSummary("百度一下 你就知道");
		t.setTitle("百度");
		t.setUrl("http://www.baidu.com");
		t.setUploaddate(System.currentTimeMillis());
		t.setFavour(7);
		t.setPraise(2);
		t.setCommentary(9);
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("CSDN");
		t.setUrl("http://www.csdn.net/");
		t.setUploaddate(System.currentTimeMillis() - 10000000);
		t.setSummary("专业的技术论坛");
		t.setImgUrl("http://bbs.csdn.net/assets/guest.jpg");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("github");
		t.setUrl("http://github.com");
		t.setUploaddate(System.currentTimeMillis() - 10000000);
		t.setSummary("GitHub · Build software better, together.");
		t.setImgUrl("http://t11.baidu.com/it/u=3896377702,3215289161&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("Google");
		t.setUrl("http://www.google.com");
		t.setUploaddate(System.currentTimeMillis() - 100000000);
		t.setSummary("谷歌是Google公司开发的互联网搜索引擎");
		t.setImgUrl("http://t11.baidu.com/it/u=1891683652,4023211487&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("Java");
		t.setUrl("http://www.java.com/");
		t.setUploaddate(System.currentTimeMillis() - 200000000);
		t.setSummary("java是一种可以撰写跨平台应用软件的面向对象的程序设计语言");
		t.setImgUrl("http://t11.baidu.com/it/u=3695155492,3500119636&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("c");
		t.setUrl("http://baike.baidu.com/");
		t.setUploaddate(System.currentTimeMillis() - 20000000);
		t.setSummary("语言是一种计算机程序设计语言，它既具有高级语言的特点，又具有汇编语言的特点。");
		t.setImgUrl("http://t11.baidu.com/it/u=2980528525,2674288977&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("python");
		t.setUrl("http://www.python.org");
		t.setUploaddate(System.currentTimeMillis() - 20000000);
		t.setSummary("Python（英语发音：/ˈpaɪθən/）, 是一种面向对象、解释型计算机程序设计语言，由Guido van Rossum于1989年底发明，第一个公开发行版发行于1991年。");
		t.setImgUrl("http://t12.baidu.com/it/u=2249553257,3895267356&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("android");
		t.setUrl("http://developer.android.com/");
		t.setUploaddate(System.currentTimeMillis() - 30000000);
		t.setSummary("Android是一种基于Linux的自由及开放源代码的操作系统");
		t.setImgUrl("http://t12.baidu.com/it/u=1574734811,2290912573&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("moli");
		t.setUrl("http://www.moli.org.cn/");
		t.setUploaddate(System.currentTimeMillis() - 300000000);
		t.setSummary("走走走，养猪去");
		t.setImgUrl("http://t12.baidu.com/it/u=423422024,921799086&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("moli");
		t.setUrl("http://www.moli.org.cn/");
		t.setUploaddate(System.currentTimeMillis() - 30000000);
		t.setSummary("走走走，养猪去");
		t.setImgUrl("http://t12.baidu.com/it/u=423422024,921799086&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("moli");
		t.setUrl("http://www.moli.org.cn/");
		t.setUploaddate(System.currentTimeMillis() - 30000000);
		t.setSummary("走走走，养猪去");
		t.setImgUrl("http://t12.baidu.com/it/u=423422024,921799086&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("moli");
		t.setUrl("http://www.moli.org.cn/");
		t.setUploaddate(System.currentTimeMillis() - 400000000);
		t.setSummary("走走走，养猪去");
		t.setImgUrl("http://t12.baidu.com/it/u=423422024,921799086&fm=58");
		technologyChannel.add(t);
		
		t = new BookMark();
		t.setTitle("moli");
		t.setUrl("http://www.moli.org.cn/");
		t.setUploaddate(System.currentTimeMillis() - 400000000);
		t.setSummary("走走走，养猪去");
		t.setImgUrl("http://t12.baidu.com/it/u=423422024,921799086&fm=58");
		technologyChannel.add(t);
		
		
		t = new BookMark();
		t.setImgUrl("http://ii.ivi.li/jaskni/logo.png");
		t.setSummary("十年寒窗磨一剑，今朝出鞘试锋芒");
		t.setTitle("好玩吧");
		t.setUrl("http://www.9haow.cn/");
		t.setUploaddate(System.currentTimeMillis() - 400000000);
		entertainmentChannel.add(t);
		
		Channel c = new Channel();
		c.setTitle("视频娱乐");
		c.setPicurl("http://www.kdatu.com/2014/10/01/landscape-56/");
		c.setSummary("猫扑 土豆 优库");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("移动开发");
		c.setPicurl("http://img3.imgtn.bdimg.com/it/u=706127875,3022820504&fm=23&gp=0.jpg");
		c.setSummary("android ios wp等");
		channels.add(c);
		
		c = new Channel();
		c.setTitle("情感日常");
		c.setPicurl("http://img.wallba.com/data/Image/2012zwj/9yue/9-10/qtbz/jignxuan/12/2012910114313390.jpg");
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
