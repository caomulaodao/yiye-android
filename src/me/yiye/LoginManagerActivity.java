package me.yiye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
