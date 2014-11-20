package me.yiye;

import me.yiye.contents.BookMark;
import me.yiye.customwidget.ConstomWebView;
import me.yiye.customwidget.ConstomWebView.OnScrollListener;
import me.yiye.customwidget.SmoothProgressBar;
import me.yiye.utils.MLog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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
		
		// 监听webview的滑动行为 在滑动到顶部与底部作特殊处理
		mainWebView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onSChanged(int l, int t, int oldl, int oldt) {
				MLog.d(TAG,"onSChanged### l:" + l + " t:" + t + " oldl:" + oldl + " oldt:" + oldt);

				if (mainWebView.getContentHeight() * mainWebView.getScale() == (mainWebView.getHeight() + mainWebView.getScrollY())) {
					BookMarkActivity.this.getSupportActionBar().show();
					BookMarkActivity.this.findViewById(R.id.view_bookmark_bottom_bar).setVisibility(View.VISIBLE);
					return;
				}
				
				if(t > oldt) {
					BookMarkActivity.this.getSupportActionBar().hide();
					BookMarkActivity.this.findViewById(R.id.view_bookmark_bottom_bar).setVisibility(View.GONE);
				} else {
					BookMarkActivity.this.getSupportActionBar().show();
					BookMarkActivity.this.findViewById(R.id.view_bookmark_bottom_bar).setVisibility(View.VISIBLE);
				}
			}
		});
		
		// Calculate ActionBar height
		int actionbarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
		{
			actionbarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		}
		
		int w = 0;
		int h = 0;
		BookMarkActivity.this.findViewById(R.id.view_bookmark_bottom_bar).measure(w, h);
		int bottombarHeight = BookMarkActivity.this.findViewById(R.id.view_bookmark_bottom_bar).getMeasuredHeight(); 
		MLog.d(TAG,"initWebView### bottombar height:" + bottombarHeight);
		
		mainWebView.setHeaderHeight(actionbarHeight);
		mainWebView.setFooterHeight(bottombarHeight);
		
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
