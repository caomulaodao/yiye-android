package me.yiye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class LoginManagerActivity extends BaseActivity {

	private Button login;
	private EditText username;
	private EditText password;

	private int maxLen = 10;
	private boolean userflag = false;
	private boolean passwordflag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_login);
		initActionbar("登陆");

		login = (Button) this.findViewById(R.id.btn_login);
		username = (EditText) this.findViewById(R.id.edittext_login_username);
		password = (EditText) this.findViewById(R.id.edittext_login_password);

		password.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = password.getText();
				passwordflag = editable.length() == 0 ? false : true;
				if (userflag && passwordflag) {
					login.setEnabled(true);
				} else {
					login.setEnabled(false);
				}
			}

		});

		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = username.getText();
				userflag = editable.length() == 0 ? false : true;
				if (userflag && passwordflag) {
					login.setEnabled(true);
				} else {
					login.setEnabled(false);
				}
			}
		});
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, LoginManagerActivity.class);
		context.startActivity(i);
	}
}
