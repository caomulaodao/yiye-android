package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.contents.ChannelSet;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class SearchFragment extends Fragment{
	private final static String TAG = "SearchFragment";
	private EditText searchEditText;
	private ListView channelSetsListView;
	private List<HashMap<String,String>> channelSetsList = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter channelSetsListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.activity_seach, null, false);
		initChannelSets(v);
		initSearch(v);
		return v;
	}
	
	private void initChannelSets(View v) {
		YiyeApi api = new YiyeApiImp(this.getActivity());
		final List<ChannelSet> channelsets = api.getChannelSets();
		for(ChannelSet cs: channelsets) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("img", cs.getImgurl());
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
		channelSetsListAdapter = new SimpleAdapter(this.getActivity(), channelSetsList, R.layout.item_search_style, from, to);
		
		ViewBinder viewBinder = new ViewBinder() {

			public boolean setViewValue(View view, Object data,String textRepresentation) {
				if (view instanceof ImageView) {
					ImageView iv = (ImageView) view;
					String url = (String)data;
					ImageLoader.getInstance().displayImage(url,iv,YiyeApplication.imageoptions);
					MLog.d(TAG, "setViewValue### imageview:" + iv.toString() + " url:" + url);
					return true;
				} else
					return false;
			}
		};  
		channelSetsListAdapter.setViewBinder(viewBinder);
		
		channelSetsListView = (ListView) v.findViewById(R.id.listview_search_channelsets);
		channelSetsListView.setAdapter(channelSetsListAdapter);
		channelSetsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int id,long pos) {
				ChannelsByLabelActivity.launch(SearchFragment.this.getActivity(),channelsets.get(id));
			}
		});
		
		channelSetsListAdapter.notifyDataSetChanged();
	}

	private void initSearch(View v) {
		
		final Button search = (Button) v.findViewById(R.id.btn_search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doSearch();
			}
		});
		
		searchEditText = (EditText) v.findViewById(R.id.edittext_search_keyword);
		searchEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent e) {
				if (KeyEvent.KEYCODE_ENTER == keyCode && e.getAction() == KeyEvent.ACTION_DOWN) {
					doSearch();
				}
				return false;
			}
		});
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Editable editable = searchEditText.getText();
				if(editable.length() >  0) {
					search.setVisibility(View.VISIBLE);
				} else {
					search.setVisibility(View.GONE);
				}
			}

		});

	}

	private void doSearch() {
		String content = searchEditText.getText().toString();
		MLog.d(TAG, "onKey### search edit content:" + content);
		ResultActivity.launch(this.getActivity(), content);
	}
}
