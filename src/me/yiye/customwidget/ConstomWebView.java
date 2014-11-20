package me.yiye.customwidget;

import me.yiye.utils.MLog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class ConstomWebView extends WebView {

	private final static String TAG = "ConstomWebView";
	private int headerHeight = 0;
	private int footerHeight = 0;
  
	public void setHeaderHeight(int headerHeight) {
		this.headerHeight = headerHeight;
	}
	
	public void setFooterHeight(int footerHeight) {
		this.footerHeight = footerHeight;
	}
	
	@Override
	protected void onDraw(Canvas c) {
		c.save();
		float tH = visibleHeaderHeight();
		if(tH > 0) {
	      c.translate(0, tH);
		}
		
		tH = visibleFooterHeight();
		// MLog.d(TAG,"onDraw### visibleFooterHeight onfooter:" + tH);
		if(tH < 0) {
			c.translate(0,tH);
		}
		
		super.onDraw(c);
		c.restore();
	}
	
	private float visibleHeaderHeight(){
		return headerHeight-getScrollY();
	} 

	private float visibleFooterHeight() {
		return - footerHeight - getScrollY() - getHeight() + getContentHeight() * getScale();
	}
	
	public ConstomWebView(Context context) {
		super(context);
	}

	public ConstomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ConstomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private OnScrollListener scrollListener;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollListener != null) {
			scrollListener.onSChanged(l, t, oldl, oldt);
		}
	}

	public void setOnScrollListener(OnScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}

	/**
	 * 滑动接口
	 */
	public interface OnScrollListener {
		public void onSChanged(int l, int t, int oldl, int oldt);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true; // don't pass our touch events to children (title bar), we
						// send these in dispatchTouchEvent
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		// 修改触摸偏移
		me.offsetLocation(0, -headerHeight);
		

		return super.dispatchTouchEvent(me);
	}
}
