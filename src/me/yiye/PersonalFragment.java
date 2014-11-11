package me.yiye;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalFragment extends Fragment{
	private static DisplayImageOptions imageoptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.img_loading)
		.showImageForEmptyUri(R.drawable.img_empty)
		.showImageOnFail(R.drawable.img_failed)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	private Button loginBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.activity_personal, null, false);
		init(v);
		return v;
	}
	
	private void init(View v) {
		loginBtn =  (Button) v.findViewById(R.id.btn_personal_login);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginManagerActivity.launch(PersonalFragment.this.getActivity());
				
			}
		});
		
		setUserInfo(v);
	}
	
	private void setUserInfo(View v) {
		
		// 设置头像
		ImageView userimageView = (ImageView) v.findViewById(R.id.imageview_personal_userimg);

		if (YiyeApplication.user != null) { // 若已经登陆，设置头像及姓名
			ImageLoader.getInstance().displayImage(YiyeApplication.user.avatar, userimageView, imageoptions);
			TextView usernameTextView = (TextView) v.findViewById(R.id.textview_personal_username);
			usernameTextView.setText(YiyeApplication.user.username);
		}
	}
}
