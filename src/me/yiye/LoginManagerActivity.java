package me.yiye;

import org.json.JSONException;
import org.json.JSONObject;

import me.yiye.contents.User;
import me.yiye.utils.MLog;
import me.yiye.utils.SQLManager;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	// 判断email与password是否有输入，若都有启用登陆按钮，否则禁用登陆按钮
	private boolean emailflag = false;
	private boolean passwordflag = false;
	
	private User user = new User();
	
	private Button loginBtn;
	private EditText emailEditText;
	private EditText passwordEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		initActionbar("登陆");

		loginBtn = (Button) this.findViewById(R.id.btn_login);
		emailEditText = (EditText) this.findViewById(R.id.edittext_login_email);
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
				if (emailflag && passwordflag) {
					loginBtn.setEnabled(true);
				} else {
					loginBtn.setEnabled(false);
				}
			}

		});
		emailEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = emailEditText.getText();
				emailflag = editable.length() == 0 ? false : true;
				if (emailflag && passwordflag) {
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
						user.email = emailEditText.getText().toString();
						user.password = passwordEditText.getText().toString();
					}

					@Override
					protected String doInBackground(Void... v) {
						MLog.d(TAG, "doInBackground### " + user.toString());
						String ret = api.login(user.email, user.password);
						MLog.d(TAG, "doInBackground### login ret:" + ret);
						ret = api.getUserInfo();
						MLog.d(TAG, "doInBackground### getuserinfo ret:" + ret);
						try {
							JSONObject o = new JSONObject(ret);
							user.avatar = o.getString("avatar");
							user.username = o.getString("username");
						} catch (JSONException e) {
							MLog.e(TAG, "get user info failed");
							e.printStackTrace();
						}
					
						return ret;
					}

					@Override
					protected void onPostExecute(String result) {
						// TODO 登陆认证
						MLog.d(TAG,"onPostExecute### saveuer:" + user.toString());
						SQLManager.saveuser(LoginManagerActivity.this,user);
						setCurrentUser(user);
						YiyeApplication.user = user;
						MainActivity.launch(LoginManagerActivity.this);
					}
				}.execute();
			}
		});
	}

	private void setCurrentUser(User user) {
		SharedPreferences userSharedPreferences= this.getSharedPreferences("user", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = userSharedPreferences.edit(); 
		editor.putString("currentuser", user.email); // 标记已经初始化
		editor.commit();
	}
	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, LoginManagerActivity.class);
		context.startActivity(i);
	}

}
