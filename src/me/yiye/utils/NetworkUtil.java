package me.yiye.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NetworkUtil {
	public final static String TAG = "NetworkUtil";

	public static String post(Context context,String url, List<NameValuePair> params) {
		MLog.d(TAG, "post### " + url);
		try {
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				// 取出回应字串
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				Header header = httpResponse.getFirstHeader("Set-Cookie");
				String cookie = header.getValue();
				MLog.d("TAG","post### cookie:" + cookie);
				
				// 写入cookie
				SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE); 
				Editor editor = sharedPreferences.edit();
				editor.putString("yiye",cookie);
				editor.commit();
				return strResult;
			} else {
				return statusCode + " error";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String get(Context context,String host,String extra) {
		String url = host + extra;
		MLog.d(TAG,"get### " + url);
		try {
			HttpGet httpget = new HttpGet(url);
			SharedPreferences share = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
			String cookie = share.getString("yiye", "");
			httpget.addHeader("Cookie", cookie);
			HttpResponse ret = new DefaultHttpClient().execute(httpget);
			return EntityUtils.toString(ret.getEntity(),"utf-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
