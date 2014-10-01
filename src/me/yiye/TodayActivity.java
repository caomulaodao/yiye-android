package me.yiye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class TodayActivity extends SherlockActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_today);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
	}
	
	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,TodayActivity.class);
		context.startActivity(i);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
