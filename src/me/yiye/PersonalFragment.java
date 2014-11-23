package me.yiye;

import me.yiye.utils.MLog;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private final static String TAG = "PersonalFragment";
	
	private static DisplayImageOptions imageoptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.img_loading)
		.showImageForEmptyUri(R.drawable.img_empty)
		.showImageOnFail(R.drawable.img_failed)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	private Button loginBtn;
	private Button aboutBtn;
	
	private Button findBtn;
	
	private View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		v = inflater.inflate(R.layout.activity_personal, null, false);
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
		
		aboutBtn = (Button) v.findViewById(R.id.btn_personal_about);
		aboutBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				new AlertDialog.Builder(PersonalFragment.this.getActivity())
					.setTitle("关于一叶")
					.setMessage("囧囧有序的施工中")
					.setPositiveButton("确定", null)
					.show();
			}
		});
		
		findBtn = (Button) v.findViewById(R.id.btn_personal_find);
		findBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchActivity.launch(getActivity());
			}
		});
		
		MLog.d(TAG, "init### setting user info");
		setUserInfo(v);
	}
	
	private void setUserInfo(View v) {
		
		// 设置头像
		ImageView userimageView = (ImageView) v.findViewById(R.id.imageview_personal_userimg);

		if (YiyeApplication.user != null) { // 若已经登陆，设置头像及姓名
			ImageLoader.getInstance().displayImage(YiyeApplication.user.avatar, userimageView, imageoptions);
			TextView usernameTextView = (TextView) v.findViewById(R.id.textview_personal_username);
			usernameTextView.setText(YiyeApplication.user.username);
			loginBtn.setText("注销");
			loginBtn.setTextColor(getActivity().getResources().getColor(R.color.Purple500));
			loginBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new AlertDialog.Builder(PersonalFragment.this.getActivity()) 
						.setTitle("额，还没做好，注销不能")
						.setMessage("我们的工程师度假去了。。")
						.setPositiveButton("就这样吧(ノ=Д=)ノ┻━┻ ", null)
						.setNegativeButton("强行登陆！！！", new  DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								LoginManagerActivity.launch(PersonalFragment.this.getActivity());
							}})
						.show();
				}
			});
		}
	}
}
