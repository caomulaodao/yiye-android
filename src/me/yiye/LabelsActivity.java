package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.contents.Channel;
import me.yiye.contents.ChannelSet;
import me.yiye.customwidget.AutoNewLineLinearLayout;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiTestImp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class LabelsActivity extends SherlockActivity{
	
	private final static String TAG = "LabelsActivity";
	private ListView channelsHitLabelListView;
	private List<HashMap<String,Object> > channelsHitLabelList = new ArrayList<HashMap<String,Object> >();
	private SimpleAdapter channelsHitLabelListAdapter;
	private static ChannelSet topic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_labels);
		this.setTitle(topic.getTitle());
		getSupportActionBar().setLogo(R.drawable.yiye_logo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		initLabels();
		initChannelsListView();
	}

	private void initChannelsListView() {
		YiyeApi api = new YiyeApiTestImp(this);
		List<Channel> channelsByLabel = api.getChannelsByLabel(topic.getLabels().get(0));
		for(Channel c :channelsByLabel) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("img",c.getPic());
			map.put("tittle", c.getTitle());
			map.put("content", c.getSummary());
			channelsHitLabelList.add(map);
		}
		
		String[] from = new String[] {"img","tittle","content"};
		int[] to = new int[] {R.id.imageview_channel_item,R.id.textview_channel_item_title,R.id.textview_channel_item_content};
		channelsHitLabelListAdapter = new SimpleAdapter(this, channelsHitLabelList,R.layout.item_channel_style, from, to);
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
		channelsHitLabelListAdapter.setViewBinder(viewBinder);
		
		channelsHitLabelListView = (ListView) this.findViewById(R.id.listview_labels_channels_hit_label);
		channelsHitLabelListView.setAdapter(channelsHitLabelListAdapter);
		channelsHitLabelListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				// TODO jump to an Channel
			}
		});
	}
	
	private List<TextView> labels = new ArrayList<TextView>();
	private TextView currentLabel;
	private void initLabels() {
		AutoNewLineLinearLayout labelContainer = (AutoNewLineLinearLayout) this.findViewById(R.id.linearlayout_labels_labelsparent);
		labelContainer.removeAllViewsInLayout();
		for(String label:topic.getLabels()) {
			View labelView = View.inflate(this, R.layout.item_label_style, null);
			TextView tv = (TextView)labelView.findViewById(R.id.textview_label_item);
			tv.setText(label);
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					v.setBackgroundColor(getResources().getColor(R.color.GREENSEA));
					currentLabel.setBackgroundColor(getResources().getColor(R.color.E3GRAY));
					currentLabel = (TextView) v;
					Toast.makeText(LabelsActivity.this, currentLabel.getText(), Toast.LENGTH_LONG).show();
				}
			});
			labels.add(tv);
			labelContainer.addView(labelView);
		}
		if(labels.size() == 0) {
			MLog.e(TAG, "initLabels### no label");
			return ;
		}
		currentLabel = labels.get(0);
		currentLabel.setBackgroundColor(getResources().getColor(R.color.GREENSEA));
		
		labelContainer.invalidate();
	}
	public static void launch(Context context,ChannelSet topic) {
		if(topic == null) {
			return;
		}
		
		LabelsActivity.topic = topic;
		launch(context);
	}

	private static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,LabelsActivity.class);
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
