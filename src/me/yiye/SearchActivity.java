package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.contents.ChannelSet;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiTestImp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class SearchActivity extends SherlockActivity {
	private static final String TAG = "SearchActivity";
	private EditText searchEditText;
	private ListView channelSetsListView;
	
	private List<HashMap<String,Object>> channelSetsList = new ArrayList<HashMap<String,Object>>();
	private SimpleAdapter classesListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("发现");
		this.setContentView(R.layout.view_seach);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		initChannelSets();
		initSerchEdit();
	}

	private void initSerchEdit() {
		searchEditText = (EditText) this.findViewById(R.id.edittext_search_keyword);
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

	private void initChannelSets() {
		
		ChannelSet.setDefaultPic(this,R.drawable.balidao);
		channelSetsListView = (ListView) this.findViewById(R.id.listview_search_channelsets);
		
		YiyeApi api = new YiyeApiTestImp();
		final List<ChannelSet> channelsets = api.getChannelSets();
		
		for(ChannelSet cs: channelsets) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("img", cs.getPic());
			map.put("tittle",cs.getTitle());
			String labelsString = "";
			for(String label:cs.getLabels()) {
				labelsString += label + " ";
			}
			
			map.put("content",labelsString);
			channelSetsList.add(map);
		}
		
		String[] from = new String[] {"img","tittle","content"};
		int[] to = new int[] {R.id.imageview_search_item,R.id.textview_search_item_title,
				R.id.textview_search_item_content};
		classesListAdapter = new SimpleAdapter(this, channelSetsList, R.layout.item_search_style, from, to);
		
		ViewBinder viewBinder = new ViewBinder() {

			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;

					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		};  
		classesListAdapter.setViewBinder(viewBinder);
		channelSetsListView.setAdapter(classesListAdapter);
		channelSetsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int id,long pos) {
				LabelClassActivity.launch(SearchActivity.this,channelsets.get(id));
			}
		});
		
		classesListAdapter.notifyDataSetChanged();
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
