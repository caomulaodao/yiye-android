package me.yiye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.view.MenuItem;

public class LoginManagerActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_login);
		initActionbar("登陆");
	}
	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,LoginManagerActivity.class);
		context.startActivity(i);
	}
}
