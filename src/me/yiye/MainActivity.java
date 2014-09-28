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
	private ListView contents;
	private SimpleAdapter adapter;
	private List<HashMap<String, Object>> data;

	private GridView gv;
	private PullToRefreshGridView ptgv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("订阅/管理");
		setContentView(R.layout.view_main_over);
		setBehindContentView(R.layout.view_main_behind);

		initSM();
		initOverPanal();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setCustomView(R.layout.view_main_actionbar);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		View v = getSupportActionBar().getCustomView();
		((Button)v.findViewById(R.id.btn_main_actionbar)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "mid button clicked", Toast.LENGTH_LONG).show();
			}
			
		});
	}

	private void initOverPanal() {
		ptgv = (PullToRefreshGridView) findViewById(R.id.gridview_main_content);
		gv = ptgv.getRefreshableView();
		gv.setAdapter(new GvAdapter(this));
	}

	private void initSM() {
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		View v = View.inflate(this, R.layout.view_main_behind, null);
		contents = (ListView) v.findViewById(R.id.listview_main_behind);

		String[] from = new String[] { "textview_behind_item",
				"imageview_behind_item_sign" };
		int[] to = new int[] { R.id.textview_behind_item,
				R.id.imageview_behind_item_sign };
		data = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("textview_behind_item", "首页");
		map.put("imageview_behind_item_sign", R.drawable.home);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("textview_behind_item", "消息");
		map.put("imageview_behind_item_sign", R.drawable.message);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("textview_behind_item", "我的提交");
		map.put("imageview_behind_item_sign", R.drawable.myup);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("textview_behind_item", "我的收藏");
		map.put("imageview_behind_item_sign", R.drawable.myfavor);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("textview_behind_item", "设置");
		map.put("imageview_behind_item_sign", R.drawable.setting);
		data.add(map);

		adapter = new SimpleAdapter(this, data, R.layout.item_behind_style,
				from, to);
		contents.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		sm.setMenu(v);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	class GvAdapter extends BaseAdapter {
		private Context context;
		private int[] imgs = { R.drawable.home, R.drawable.message,
				R.drawable.myfavor, R.drawable.myup, R.drawable.setting };

		GvAdapter(Context context) {
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
				v = View.inflate(context, R.layout.item_over_style, null);
				v.setLayoutParams(new GridView.LayoutParams(300, 300));
			} else {
				v = convertView;
			}

			imageView = (ImageView) v
					.findViewById(R.id.imageview_over_item_background);
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
		case R.id.main:
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
}
