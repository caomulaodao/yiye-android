package me.yiye;

import me.yiye.contents.BookMark;
import me.yiye.customwidget.PopupCommentaryActivity;
import me.yiye.customwidget.SmoothProgressBar;
import me.yiye.utils.MLog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class BookMarkActivity extends BaseActivity {
	private final static String TAG = "ContentActivity";
	private static BookMark bookmark;
	
	private WebView mainWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_bookmark);
		initActionbar(bookmark.getTitle());
		initWebView();
		initBottomActionBar();
		initGlobalAction();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		final SmoothProgressBar loaddingProgressBar = (SmoothProgressBar) this.findViewById(R.id.progressbar_web);
		loaddingProgressBar.setMax(100);
		
		mainWebView = (WebView) this.findViewById(R.id.webview_bookmark_data);

		WebSettings webSettings = mainWebView.getSettings();
		// 设置出现缩放工具
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setDisplayZoomControls(false);
		// 去除滚动条
		mainWebView.setHorizontalScrollBarEnabled(false);
		mainWebView.setVerticalScrollBarEnabled(false);
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
		ImageButton commentary = (ImageButton) this.findViewById(R.id.imagebutton_bookmark_commentary);
		ImageButton favour = (ImageButton) this.findViewById(R.id.imagebutton_bookmark_favour);
		ImageButton praise = (ImageButton) this.findViewById(R.id.imagebutton_bookmark_praise);
		commentary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupCommentaryActivity.lauch(BookMarkActivity.this);
			}
		});
		favour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BookMarkActivity.this, "收藏", Toast.LENGTH_LONG).show();
			}
		});
		praise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(BookMarkActivity.this, "赞", Toast.LENGTH_LONG).show();
			}
		});
	}

	// 下滑标题栏跟点赞栏消失
	private void initGlobalAction() {
		final View buttomBarView = findViewById(R.id.view_bookmark_bottom_bar);
		final GestureDetector webviewGestureDetector = new GestureDetector(this,new SimpleOnGestureListener() {

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				int verticalMinDistance = 30;
				int minVelocity = 10;
				if ((e1.getY() - e2.getY() > verticalMinDistance && Math.abs(velocityY) > minVelocity)) {
					getSupportActionBar().hide();
					buttomBarView.setVisibility(View.GONE);
				} else {
					getSupportActionBar().show();
					buttomBarView.setVisibility(View.VISIBLE);
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}

		});
		mainWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent me) {
				return webviewGestureDetector.onTouchEvent(me);
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
	 * TODO 支持左右滑动回退
	 */
	public static void launch(Context context, BookMark bookmark) {
		if (bookmark == null) {
			MLog.e(TAG, "launch### bookmark is null");
			return;
		}
		BookMarkActivity.bookmark = bookmark;
		launch(context);
	}

	private static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, BookMarkActivity.class);
		context.startActivity(i);
	}
}
