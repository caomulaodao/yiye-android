package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.utils.MLog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class SearchActivity extends SherlockActivity {
	private static final String TAG = "SearchActivity";
	private EditText searchEditText;
	private ListView classesListView;
	
	private List<HashMap<String,Object>> classesList = new ArrayList<HashMap<String,Object>>();
	private SimpleAdapter classesListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("发现");
		this.setContentView(R.layout.view_seach);
		
		searchEditText = (EditText) this.findViewById(R.id.edittext_search_keyword);
		classesListView = (ListView) this.findViewById(R.id.listview_search_classes);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("img", R.drawable.balidao);
		map.put("tittle", "科技");
		map.put("content", "iphone android google 一页");
		classesList.add(map);
		
		String[] from = new String[] {"img","tittle","content"};
		int[] to = new int[] {R.id.imageview_search_item,R.id.textview_search_item_title,
				R.id.textview_search_item_content};
		classesListAdapter = new SimpleAdapter(this, classesList, R.layout.item_search_style, from, to);
		classesListView.setAdapter(classesListAdapter);
		classesListAdapter.notifyDataSetChanged();
		
		classesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				ChannelActivity.launch(SearchActivity.this);
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		searchEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent e) {
				if (KeyEvent.KEYCODE_ENTER == keyCode && e.getAction() == KeyEvent.ACTION_DOWN) {
					String content = searchEditText.getText().toString();
					MLog.d(TAG, "onKey### search edit content:" + content);
					ResultActivity.launch(SearchActivity.this, content);
					
				}
				return false;
			}
		});
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,SearchActivity.class);
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
