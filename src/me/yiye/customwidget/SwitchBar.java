package me.yiye.customwidget;

import me.yiye.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SwitchBar extends LinearLayout {

	private TextView channelTextView;
	private TextView findTextView;
	private TextView personalTextView;
	private int pos = 0;
	private Context context;

	public SwitchBar(Context context) {
		super(context);
		this.context = context;
		init(context);
	}

	public SwitchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	public SwitchBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		inflate(context, R.layout.item_main_switchbar, this);
		channelTextView = (TextView) findViewById(R.id.textview_main_switchbar_channel);
		findTextView = (TextView) findViewById(R.id.textview_main_switchbar_find);
		personalTextView = (TextView) findViewById(R.id.textview_main_switchbar_personal);
		setSelect(pos);
	}

	public void setSelect(int pos) {
		clearSelect();
		switch (pos) {
		case 0:
			channelTextView.setTextColor(context.getResources().getColor(R.color.Purple500));
			break;
		case 1:
			findTextView.setTextColor(context.getResources().getColor(R.color.Purple500));
			break;
		case 2:
			personalTextView.setTextColor(context.getResources().getColor(R.color.Purple500));
			break;
		default:
			break;
		}
		this.pos = pos;
	}

	private void clearSelect() {
		channelTextView.setTextColor(context.getResources().getColor(R.color.Grey900));
		findTextView.setTextColor(context.getResources().getColor(R.color.Grey900));
		personalTextView.setTextColor(context.getResources().getColor(R.color.Grey900));
	}

	public void setOnClickLisener(int pos, OnClickListener listener) {
		switch (pos) {
		case 0:
			channelTextView.setOnClickListener(listener);
			break;
		case 1:
			findTextView.setOnClickListener(listener);
			break;
		case 2:
			personalTextView.setOnClickListener(listener);
			break;
		}
	}
}
