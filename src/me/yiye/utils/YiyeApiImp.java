package me.yiye.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;

public class YiyeApiImp implements YiyeApi{
	
	private final static String TAG = "YiyeApiImp";
	private Context context;
	
	public YiyeApiImp(Context context) {
		this.context = context;
	}
	
	@Override
	public List<Channel> getBookedChannels() {
		List<Channel> bookedChannels = new ArrayList<Channel>();
		String ret = NetworkUtil.get(context, YiyeApi.TESTHOST, YiyeApi.BOOKEDCHANNELS);
		MLog.d(TAG,"getBookedChannels### ret:" + ret);
		if(ret != null) {
			YiyeApiHelper.addChannelToChannelSet(context, bookedChannels, ret);
		} else {
			MLog.e(TAG,"getBookedChannels### 获取订阅频道为空");
		}
		return bookedChannels;
	}

	@Override
	public List<ChannelSet> getChannelSets() {
		// TODO Auto-generated method stub 
		return null;
	}

	@Override
	public List<Channel> getChannelsByLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookMark> getBookMarksByChannel(Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String email, String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", keyword));
		return NetworkUtil.post(context,YiyeApi.LOGIN,params);
	}

	@Override
	public String getUserInfo() {
		return NetworkUtil.get(context, YiyeApi.HOST, YiyeApi.ME);
	}

}
