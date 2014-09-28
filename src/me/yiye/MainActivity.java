package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("订阅/管理");
		setContentView(R.layout.view_main_above);
		setBehindContentView(R.layout.view_main_behind);

		initSlidingMenu();
		initAbovePanal();
	}

	private void initAbovePanal() {
		GridView mainDataGridView;
		PullToRefreshGridView pullableView;
		pullableView = (PullToRefreshGridView) findViewById(R.id.gridview_main_content);
		mainDataGridView = pullableView.getRefreshableView();
		mainDataGridView.setAdapter(new MainDataGridAdapter(this));
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setCustomView(R.layout.view_main_actionbar);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		View v = getSupportActionBar().getCustomView();
		((Button)v.findViewById(R.id.btn_main_above_actionbar_booknmanager)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "mid button clicked", Toast.LENGTH_LONG).show();
			}
			
		});
	}

	class MainDataGridAdapter extends BaseAdapter {
		private Context context;
		private int[] imgs = { R.drawable.home, R.drawable.message,
				R.drawable.myfavor, R.drawable.myup, R.drawable.balidao};

		MainDataGridAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return imgs.length;
		}

		@Override
		public Object getItem(int item) {
			return item;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {

			View v;
			ImageView imageView;

			if (convertView == null) {
				v = View.inflate(context, R.layout.item_main_above_style, null);
				v.setLayoutParams(new GridView.LayoutParams(300, 300));
			} else {
				v = convertView;
			}

			imageView = (ImageView) v.findViewById(R.id.imageview_main_above_item_background);
			imageView.setAdjustViewBounds(false);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageResource(imgs[pos]);
			return v;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_find:
			Toast.makeText(this, "menu clicked", Toast.LENGTH_SHORT).show();
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
