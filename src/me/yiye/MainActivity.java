package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.contents.Channel;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiTestImp;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("订阅");
		setContentView(R.layout.view_main_above);
		setBehindContentView(R.layout.view_main_behind);
		getSupportActionBar().setLogo(R.drawable.yiye_logo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getSupportActionBar().setDisplayShowCustomEnabled(true);
		// getSupportActionBar().setCustomView(R.layout.view_main_actionbar);
		
		initSlidingMenu();
		initAbovePanal();
	}

	private void initAbovePanal() {
		GridView mainDataGridView;
		PullToRefreshGridView pullableView;
		pullableView = (PullToRefreshGridView) findViewById(R.id.gridview_main_content);
		mainDataGridView = pullableView.getRefreshableView();
		final ChannelsGridAdapter dataadpter = new ChannelsGridAdapter(this);
		YiyeApi api = new YiyeApiTestImp();
		dataadpter.setData(api.getBookedChannels());
		mainDataGridView.setAdapter(dataadpter);
		dataadpter.notifyDataSetChanged();
		mainDataGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				ChannelActivity.launch(MainActivity.this,dataadpter.getItem(pos));

			}
		});
	}

	class ChannelsGridAdapter extends BaseAdapter {
		private Context context;

		private List<Channel> channels;
		
		ChannelsGridAdapter(Context context) {
			this.context = context;
			Channel.setDefaultPic(context, R.drawable.balidao);
		}

		@Override
		public int getCount() {
			return channels.size();
		}

		@Override
		public Channel getItem(int item) {
			return channels.get(item);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {

			View v;
			ImageView imageView;
			TextView textView;
			Channel c = channels.get(pos);
			if (convertView == null) {
				v = View.inflate(context, R.layout.item_main_above_style, null);
				v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 350));
				v.setBackgroundResource(R.drawable.girditem_style);
			} else {
				v = convertView;
			}
			
			imageView = (ImageView) v.findViewById(R.id.imageview_main_above_item_background);
			imageView.setAdjustViewBounds(false);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageBitmap(c.getPic());
			
			textView = (TextView) v.findViewById(R.id.textview_over_item_notice);
			textView.setText(c.getTitle());
			
			return v;
		}
		
		public void setData(List<Channel> channels){
			this.channels = channels;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_find:
			SearchActivity.launch(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initSlidingMenu() {
		ListView behindMenuListView;
		SimpleAdapter behindMenuListAdapter;
		List<HashMap<String, Object>> contents  = new ArrayList<HashMap<String, Object>>();
		
		SlidingMenu sm = this.getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		View v = View.inflate(this, R.layout.view_main_behind, null);
		behindMenuListView = (ListView) v.findViewById(R.id.listview_main_behind_menu);

		String[] from = new String[] { "textview_behind_item",
				"imageview_behind_item_sign" };
		int[] to = new int[] { R.id.textview_behind_item,
				R.id.imageview_behind_item_sign };

		int[] mapids = new int[] {
			R.drawable.home,R.drawable.message,R.drawable.myup,
			R.drawable.myfavor,R.drawable.setting
		};
		
		String[] mapcontents = getResources()
				.getStringArray(R.array.main_behind_memu_contents);
		
		for(int i = 0;i < 5; ++ i){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("textview_behind_item", mapcontents[i]);
			map.put("imageview_behind_item_sign", mapids[i]);
			contents.add(map);
		}

		behindMenuListAdapter = new SimpleAdapter(this, contents, R.layout.item_main_behind_style,from, to);
		behindMenuListView.setAdapter(behindMenuListAdapter);
		behindMenuListAdapter.notifyDataSetChanged();

		sm.setMenu(v);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
