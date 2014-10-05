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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SearchActivity extends SherlockActivity {
	private static final String TAG = "SearchActivity";
	private EditText searchEditText;
	private ListView channelSetsListView;
	private List<HashMap<String,String>> channelSetsList = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter channelSetsListAdapter;
	
	private static DisplayImageOptions imageoptions;
	
	static {
		imageoptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.img_loading)
			.showImageForEmptyUri(R.drawable.img_empty)
			.showImageOnFail(R.drawable.img_failed)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			// .displayer(new RoundedBitmapDisplayer(20))
			.build();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("发现");
		this.setContentView(R.layout.view_seach);
		getSupportActionBar().setLogo(R.drawable.yiye_logo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		initChannelSets();
		initSearch();
	}
	
	private void initChannelSets() {
		YiyeApi api = new YiyeApiTestImp(this);
		final List<ChannelSet> channelsets = api.getChannelSets();
		
		for(ChannelSet cs: channelsets) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("img", cs.getPicurl());
			map.put("tittle",cs.getTitle());
			String labelsString = "";
			for(String label:cs.getLabels()) {
				labelsString += label + " ";
			}
			
			map.put("content",labelsString);
			channelSetsList.add(map);
		}
		
		String[] from = new String[] {"img","tittle","content"};
		int[] to = new int[] {R.id.imageview_search_item,
				R.id.textview_search_item_title,
				R.id.textview_search_item_content};
		channelSetsListAdapter = new SimpleAdapter(this, channelSetsList, R.layout.item_search_style, from, to);
		
		ViewBinder viewBinder = new ViewBinder() {

			public boolean setViewValue(View view, Object data,String textRepresentation) {
				if (view instanceof ImageView) {
					ImageView iv = (ImageView) view;
					String url = (String)data;
					ImageLoader.getInstance().displayImage(url,iv,imageoptions);
					MLog.d(TAG, "setViewValue### imageview:" + iv.toString() + " url:" + url);
					return true;
				} else
					return false;
			}
		};  
		channelSetsListAdapter.setViewBinder(viewBinder);
		
		channelSetsListView = (ListView) this.findViewById(R.id.listview_search_channelsets);
		channelSetsListView.setAdapter(channelSetsListAdapter);
		channelSetsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int id,long pos) {
				LabelsActivity.launch(SearchActivity.this,channelsets.get(id));
			}
		});
		
		channelSetsListAdapter.notifyDataSetChanged();
	}

	private void initSearch() {
		searchEditText = (EditText) this.findViewById(R.id.edittext_search_keyword);
		searchEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent e) {
				if (KeyEvent.KEYCODE_ENTER == keyCode && e.getAction() == KeyEvent.ACTION_DOWN) {
					doSearch();
				}
				return false;
			}
		});
		
		Button search = (Button) this.findViewById(R.id.btn_search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doSearch();
			}
		});
	}

	private void doSearch() {
		String content = searchEditText.getText().toString();
		MLog.d(TAG, "onKey### search edit content:" + content);
		ResultActivity.launch(SearchActivity.this, content);
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

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,SearchActivity.class);
		context.startActivity(i);
	}

}
