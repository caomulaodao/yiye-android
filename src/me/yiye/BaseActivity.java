package me.yiye;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.LayoutParams;

class BaseActivity  extends SherlockActivity {
	
	protected void initActionbar(String title) {
		View barview = View.inflate(this,R.layout.view_main_actionbar,null);
		getSupportActionBar().setCustomView(barview,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowCustomEnabled(true);
	   ((TextView) barview.findViewById(R.id.textview_actionbar_title)).setText(title);
		barview.findViewById(R.id.imageview_actionbar_btn).setVisibility(View.GONE);
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
