package me.yiye;

import me.yiye.contents.BookMark;
import me.yiye.customwidget.ConstomWebView;
import me.yiye.customwidget.ConstomWebView.OnScrollListener;
import me.yiye.customwidget.SmoothProgressBar;
import me.yiye.utils.MLog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

public class BookMarkActivity extends BaseActivity {
	private final static String TAG = "BookMarkActivity";
	
	private static BookMark bookmark;
	
	private ConstomWebView mainWebView;
	private SmoothProgressBar loaddingProgressBar;
	private OnTouchListener webviewTouchListener;
	
	
	private View commentaryView;
	private View buttomBarView;
	private ImageButton commentaryBtn;
	private ImageButton favourBtn;
	private ImageButton praiseBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_bookmark);
		initActionbar(bookmark.title);
		initWebView();
		initBottomActionBar();
		mainWebView.loadUrl(bookmark.url);
	}
	
	private void initWebView() {
		
		// 设置webview
		mainWebView = (ConstomWebView) this.findViewById(R.id.webview_bookmark_data);
		WebSettings webSettings = mainWebView.getSettings();
		webSettings.setSupportZoom(true);	// 允许缩放
		webSettings.setBuiltInZoomControls(true);	// 使用内建的缩放手势
		webSettings.setLoadWithOverviewMode(true);	// 缩小以适应屏幕
		webSettings.setJavaScriptEnabled(true);	// 开启javascript（！！有问题 知乎的登陆按钮不起作用)
		webSettings.setUseWideViewPort(true);	// 双击变大变小
		webSettings.setDisplayZoomControls(false);	// 去除缩放时右下的缩放按钮
		// 去除滚动条
		mainWebView.setHorizontalScrollBarEnabled(false);
		mainWebView.setVerticalScrollBarEnabled(false);
		
		// 防止启动外部浏览器
		mainWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		// 设置加载进度条
		loaddingProgressBar = (SmoothProgressBar) this.findViewById(R.id.progressbar_web);
		loaddingProgressBar.setMax(100);
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
	}

	private void initBottomActionBar() {
		commentaryView = (View) this.findViewById(R.id.view_bookmark_commentary);
		buttomBarView = (View) this.findViewById(R.id.view_bookmark_bottom_bar);
		
		// 底部栏的三个按钮
		commentaryBtn = (ImageButton) buttomBarView.findViewById(R.id.imagebutton_bookmark_commentary);
		favourBtn = (ImageButton) buttomBarView.findViewById(R.id.imagebutton_bookmark_favour);
		praiseBtn = (ImageButton) buttomBarView.findViewById(R.id.imagebutton_bookmark_praise);
		
		commentaryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				commentaryView.setVisibility(View.VISIBLE);
				buttomBarView.setVisibility(View.GONE);
				mainWebView.setOnTouchListener(null);
			}
		});
		favourBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BookMarkActivity.this, "收藏", Toast.LENGTH_LONG).show();
			}
		});
		praiseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(BookMarkActivity.this, "赞", Toast.LENGTH_LONG).show();
			}
		});
		
		// 评论面板上的关闭按钮
		TextView closeBtn = (TextView) commentaryView.findViewById(R.id.btn_bookmark_commentary_close);
		
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commentaryView.setVisibility(View.GONE);
				buttomBarView.setVisibility(View.VISIBLE);
				mainWebView.setOnTouchListener(webviewTouchListener);
			}
		});
		
		// 屏蔽评论面板滑动穿透到webview上
		commentaryView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				return true;
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
