package me.yiye;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.LayoutParams;

class BaseActivity  extends SherlockActivity {
	
	// 采用的actionbar均为自定义的view，包括左边导航栏，中间标题，及右边的菜单
	protected void initActionbar(String title) {
		View barview = View.inflate(this,R.layout.view_main_actionbar,null);
		getSupportActionBar().setCustomView(barview,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		// 标题
	   ((TextView) barview.findViewById(R.id.textview_actionbar_title)).setText(title);
	   
	   // 右边菜单
		((View)barview.findViewById(R.id.imageview_actionbar_btn)).setVisibility(View.GONE);
		
		// 左边导航按钮
		ImageView navicon = (ImageView)barview.findViewById(R.id.imageview_actionbar_navicon);
		navicon.setImageResource(R.drawable.ic_action_back);
		navicon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
