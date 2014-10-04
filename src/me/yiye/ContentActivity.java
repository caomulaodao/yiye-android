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
	private WebView web;
	private ProgressBar webpgbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_content);
		getSupportActionBar().setLogo(R.drawable.yiye_logo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		this.setTitle(bookmark.getTitle());
		initWebView();
		initBottomBar();
	}

	private void initWebView() {
		
		webpgbar = (ProgressBar) this.findViewById(R.id.progressbar_web);  
		webpgbar.setMax(100); 
		
		web = (WebView) this.findViewById(R.id.webview_content_data);
		MLog.d(TAG, "onCreate### found webview");
		WebSettings webSettings = web.getSettings();
		// 设置出现缩放工具
		webSettings.setSupportZoom(false);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		web.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					webpgbar.setVisibility(View.GONE);
				} else {
					if (webpgbar.getVisibility() == View.GONE)
						webpgbar.setVisibility(View.VISIBLE);
					webpgbar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		
		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		
		web.loadUrl(bookmark.getUrl());

		MLog.d(TAG, "onCreate### set webview done");
	}
	
	private void initBottomBar() {
		ImageButton commentary = (ImageButton) this.findViewById(R.id.textview_content_commentary);
		ImageButton favour = (ImageButton) this.findViewById(R.id.textview_content_favour);
		ImageButton praise = (ImageButton) this.findViewById(R.id.textview_content_praise);
		
		// commentary.setText(bookmark.getCommentary() + " 评论");
		// favour.setText(bookmark.getFavour() + " 收藏");
		// praise.setText(bookmark.getPraise() + " 赞");
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
	
	public static void launch(Context context, BookMark bookmark) {
		if(bookmark == null) {
			MLog.e(TAG, "launch### bookmark is null");
			return;
		}
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

	
	private static void launch(Context context) {
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
