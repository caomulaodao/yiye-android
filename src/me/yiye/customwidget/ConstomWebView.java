package me.yiye.customwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ConstomWebView extends WebView {

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
}
