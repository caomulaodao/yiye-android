package me.yiye;

import me.yiye.contents.BookMark;
import me.yiye.utils.MLog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ContentActivity extends SherlockActivity {
	private final static String TAG = "ContentActivity";
	private static BookMark bookmark;
	private WebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_content);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		web = (WebView) this.findViewById(R.id.webview_content_data);
		MLog.d(TAG, "onCreate### found webview");
		WebSettings webSettings = web.getSettings();
		// 设置出现缩放工具
		webSettings.setSupportZoom(false);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		web.loadUrl("http://www.baidu.com");

		MLog.d(TAG, "onCreate### set webview done");
	}

	public static void launch(Context context, BookMark bookmark) {
		ContentActivity.bookmark = bookmark;
		launch(context);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}  

	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, ContentActivity.class);
		context.startActivity(i);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
