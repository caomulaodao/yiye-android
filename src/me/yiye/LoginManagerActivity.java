package me.yiye;

import me.yiye.contents.User;
import me.yiye.utils.MLog;
import me.yiye.utils.SQLManager;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginManagerActivity extends BaseActivity {
	private final static String TAG = "LoginManagerActivity";
	private Button loginBtn;
	private EditText usernameEditText;
	private EditText passwordEditText;

	private User user = new User();
	
	// 判断username与password是否有输入，若都有启用登陆按钮，否则禁用登陆按钮
	private boolean usernameflag = false;
	private boolean passwordflag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		initActionbar("登陆");

		loginBtn = (Button) this.findViewById(R.id.btn_login);
		usernameEditText = (EditText) this.findViewById(R.id.edittext_login_username);
		passwordEditText = (EditText) this.findViewById(R.id.edittext_login_password);

		passwordEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = passwordEditText.getText();
				passwordflag = editable.length() == 0 ? false : true;
				if (usernameflag && passwordflag) {
					loginBtn.setEnabled(true);
				} else {
					loginBtn.setEnabled(false);
				}
			}

		});
		usernameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = usernameEditText.getText();
				usernameflag = editable.length() == 0 ? false : true;
				if (usernameflag && passwordflag) {
					loginBtn.setEnabled(true);
				} else {
					loginBtn.setEnabled(false);
				}
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, String>() {
					private YiyeApi api;

					@Override
					protected void onPreExecute() {
						api = new YiyeApiImp(LoginManagerActivity.this);
						user.username = usernameEditText.getText().toString();
						user.password = passwordEditText.getText().toString();
					}

					@Override
					protected String doInBackground(Void... v) {
						MLog.d(TAG, "doInBackground### " + user.toString());
						String ret = api.login(user.username, user.password);
						MLog.d(TAG, "doInBackground### login ret:" + ret);
						ret = api.getUserInfo();
						MLog.d(TAG, "doInBackground### getuserinfo ret:" + ret);
						// TODO 测试用
						user.avatar = "http://a.hiphotos.baidu.com/image/w%3D310/sign=6f23d4d17d3e6709be0043fe0bc69fb8/7a899e510fb30f248a02c4f5ca95d143ac4b03af.jpg";
						return ret;
					}

					@Override
					protected void onPostExecute(String result) {
						// TODO 登陆认证
						// if(ret == null) {
						//     error();
						//  } else {
						MLog.d(TAG,"onPostExecute### saveuer:" + user.toString());
						SQLManager.saveuser(LoginManagerActivity.this,user);
						MainActivity.launch(LoginManagerActivity.this,user);
					    // }
					}
				}.execute();
			}
		});
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, LoginManagerActivity.class);
		context.startActivity(i);
	}

}
