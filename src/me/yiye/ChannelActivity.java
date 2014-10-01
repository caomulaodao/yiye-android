package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ChannelActivity extends SherlockActivity{
	
	private ListView channelListView;
	private List<HashMap<String,Object> > channelList = new ArrayList<HashMap<String,Object> >();
	private SimpleAdapter channelListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.view_channel);
		channelListView = (ListView) this.findViewById(R.id.listview_channel);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("img",R.drawable.balidao);
		map.put("tittle", "iphone6 降价到1000");
		map.put("content", "我擦这么便宜，老板给我来10斤。");
		
		channelList.add(map);
		
		String[] from = new String[] {"img","tittle","content"};
		int[] to = new int[] {R.id.imageview_channel_item,R.id.textview_channel_item_title,
				R.id.textview_channel_item_content};
		channelListAdapter = new SimpleAdapter(this, channelList,R.layout.item_channel_style, from, to);
		
		channelListView.setAdapter(channelListAdapter);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		channelListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				ContentActivity.launch(ChannelActivity.this);
			}
		});
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,ChannelActivity.class);
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
