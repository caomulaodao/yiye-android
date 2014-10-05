package me.yiye;

import me.yiye.contents.BookMark;
import me.yiye.utils.MLog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ContentActivity extends SherlockActivity {
	private final static String TAG = "ContentActivity";
	private static BookMark bookmark;
	private WebView mainWebView;
	private ProgressBar loaddingProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_content);
		getSupportActionBar().setLogo(R.drawable.yiye_logo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		this.setTitle(bookmark.getTitle());
		initWebView();
		initBottomActionBar();
	}

	private void initWebView() {
		
		loaddingProgressBar = (ProgressBar) this.findViewById(R.id.progressbar_web);  
		loaddingProgressBar.setMax(100); 
		mainWebView = (WebView) this.findViewById(R.id.webview_content_data);
		
		WebSettings webSettings = mainWebView.getSettings();
		// 设置出现缩放工具
		webSettings.setSupportZoom(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		
		// 设置加载进度条
		mainWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					loaddingProgressBar.setVisibility(View.GONE);
				} else {
					if (loaddingProgressBar.getVisibility() == View.GONE)
						loaddingProgressBar.setVisibility(View.VISIBLE);
					loaddingProgressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		
		// 防止启动外部浏览器
		mainWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		mainWebView.loadUrl(bookmark.getUrl());
	}
	
	private void initBottomActionBar() {
		ImageButton commentary = (ImageButton) this.findViewById(R.id.imagebutton_content_commentary);
		ImageButton favour = (ImageButton) this.findViewById(R.id.imagebutton_content_favour);
		ImageButton praise = (ImageButton) this.findViewById(R.id.imagebutton_content_praise);
		commentary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(ContentActivity.this, "评论", Toast.LENGTH_LONG).show();
			}
		});
		favour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ContentActivity.this, "收藏", Toast.LENGTH_LONG).show();
			}
		});
		praise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(ContentActivity.this,"赞", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	// 支持网页回退
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mainWebView.canGoBack()) {
			mainWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}  

	/**
	 *  TODO 支持左右滑动回退
	 */
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static void launch(Context context, BookMark bookmark) {
		if(bookmark == null) {
			MLog.e(TAG, "launch### bookmark is null");
			return;
		}
		ContentActivity.bookmark = bookmark;
		launch(context);
	}

	private static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, ContentActivity.class);
		context.startActivity(i);
	}
}
