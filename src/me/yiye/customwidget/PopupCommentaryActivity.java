package me.yiye.customwidget;

import me.yiye.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PopupCommentaryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_popupcommentary);
	}

	public static void lauch(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, PopupCommentaryActivity.class);
		context.startActivity(intent);
	}
}
