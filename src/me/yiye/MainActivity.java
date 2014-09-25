package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	protected ListFragment mFrag;

	private ListView contents;
	private SimpleAdapter adapter;
	private List<HashMap<String,Object>> data;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_main_over);
		// set the Behind View
		setBehindContentView(R.layout.view_main_behind);
		
		initSM();
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
		contents = (ListView)v.findViewById(R.id.listview_main_behind);
		
		String[] from = new String[]{"textview_behind_item","imageview_behind_item_sign"};
		int[] to = new int[]{R.id.textview_behind_item,R.id.imageview_behind_item_sign};
		data = new ArrayList<HashMap<String,Object> >();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("textview_behind_item", "首页");
		map.put("imageview_behind_item_sign", R.drawable.home);
		data.add(map);
		
		map = new HashMap<String,Object>();
		map.put("textview_behind_item", "消息");
		map.put("imageview_behind_item_sign", R.drawable.message);
		data.add(map);
		
		map = new HashMap<String,Object>();
		map.put("textview_behind_item", "我的提交");
		map.put("imageview_behind_item_sign", R.drawable.myup);
		data.add(map);
		
		map = new HashMap<String,Object>();
		map.put("textview_behind_item", "我的收藏");
		map.put("imageview_behind_item_sign", R.drawable.myfavor);
		data.add(map);
		
		map = new HashMap<String,Object>();
		map.put("textview_behind_item", "设置");
		map.put("imageview_behind_item_sign", R.drawable.setting);
		data.add(map);
		
		
		adapter = new SimpleAdapter(this, data, R.layout.item_behind_style, from, to);
		contents.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		sm.setMenu(v);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
