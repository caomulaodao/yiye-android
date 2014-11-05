package me.yiye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yiye.contents.Channel;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends SlidingFragmentActivity {
	private final static String TAG = "MainActivity";
	private static DisplayImageOptions imageoptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.img_loading)
		.showImageForEmptyUri(R.drawable.img_empty)
		.showImageOnFail(R.drawable.img_failed)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	final YiyeApi api = new YiyeApiImp(this);
	
	final ChannelsGridAdapter dataadpter = new ChannelsGridAdapter(this);
	private PullToRefreshGridView pullableView;
	private GridView mainDataGridView;
	
	private SimpleAdapter behindMenuListAdapter;
	private ListView behindMenuListView;
	private List<HashMap<String, Object>> contents  = new ArrayList<HashMap<String, Object>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("订阅");
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.view_main_behind);
		
		initActionbar();
		initSlidingMenu();
		initAbovePanal();
	}

	private void initActionbar() {
		View barview = View.inflate(this,R.layout.view_actionbar,null);
		getSupportActionBar().setCustomView(barview,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowCustomEnabled(true);
	   ((TextView) barview.findViewById(R.id.textview_actionbar_title)).setText("一叶书签");
		barview.findViewById(R.id.imageview_actionbar_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchActivity.launch(MainActivity.this);
			}
		});
		
		((ImageView)barview.findViewById(R.id.imageview_actionbar_navicon)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
	}

	private void initAbovePanal() {
		pullableView = (PullToRefreshGridView) findViewById(R.id.gridview_main_content);
		// pullableView.getLoadingLayoutProxy().setPullLabel("你妹的");
		pullableView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.star));
		
		mainDataGridView = pullableView.getRefreshableView();
		mainDataGridView.setBackgroundColor(getResources().getColor(R.color.activitybackgroud));
		mainDataGridView.setAdapter(dataadpter);
		mainDataGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				ChannelActivity.launch(MainActivity.this,dataadpter.getItem(pos));
			}
		});
		
		// 获取频道数据
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... v) {
				dataadpter.setData(api.getBookedChannels());
				return null;
			}
			
			@Override
			protected void onPostExecute(Void v) {
				MLog.d(TAG, "onPostExecute### get user book channels");
				dataadpter.notifyDataSetChanged();
			}
		}.execute();
	}

	class ChannelsGridAdapter extends BaseAdapter {
		private Context context;
		private List<Channel> channels;
		
		ChannelsGridAdapter(Context context) {
			this.context = context;
			channels = new ArrayList<Channel>();
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
			RoundedImageView channelLogoImageView;
			TextView channelNameTextView;
			TextView newsTextView;
			Channel c = channels.get(pos);
			if (convertView == null) {
				v = View.inflate(context, R.layout.item_main_above_style, null);
				v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 300));
				v.setBackgroundResource(R.drawable.girditem_style);
			} else {
				v = convertView;
			}
			
			channelLogoImageView = (RoundedImageView) v.findViewById(R.id.imageview_main_above_item_background);
			channelLogoImageView.setAdjustViewBounds(false);
			channelLogoImageView.setCornerRadius(4.0f);
			ImageLoader.getInstance().displayImage(c.logo, channelLogoImageView,imageoptions);
			
			channelNameTextView = (TextView) v.findViewById(R.id.textview_over_item_notice);
			channelNameTextView.setText(c.name);
			newsTextView = (TextView) v.findViewById(R.id.textview_over_item_news);
			newsTextView.setText(c.news + "");
			return v;
		}
		
		public void setData(List<Channel> channels){
			this.channels = channels;
		}
	}

	private void initSlidingMenu() {
		SlidingMenu sm = this.getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		View v = View.inflate(this, R.layout.view_main_behind, null);
		behindMenuListView = (ListView) v.findViewById(R.id.listview_main_behind_menu);

		// 初始化菜单
		String[] from = new String[] { "textview_behind_item","imageview_behind_item_sign" };
		int[] to = new int[] { R.id.textview_behind_item,R.id.imageview_behind_item_sign };
		
		int[] mapids = new int[] {
			R.drawable.home,R.drawable.message,R.drawable.myup,
			R.drawable.myfavor,R.drawable.setting
		};
		
		String[] mapcontents = getResources().getStringArray(R.array.main_behind_memu_contents);
		
		for(int i = 0;i < 5; ++ i){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("textview_behind_item", mapcontents[i]);
			map.put("imageview_behind_item_sign", mapids[i]);
			contents.add(map);
		}

		behindMenuListAdapter = new SimpleAdapter(this, contents, R.layout.item_main_behind_style,from, to){
			@Override
			public View getView(int position, View view, ViewGroup parent) {
				view = super.getView(position, view, parent);
				if (view != null && position == 0) {
					view.setBackgroundResource(R.color.WETASPHALT);
					((TextView)view.findViewById(R.id.textview_behind_item)).
						setTextColor(getResources().getColor(R.color.PUERWHITE));
					((ImageView)view.findViewById(R.id.imageview_behind_item_sign)).
						setImageResource(R.drawable.home_selected);
				}
				return view;
			}
		};
		behindMenuListView.setAdapter(behindMenuListAdapter);
		behindMenuListAdapter.notifyDataSetChanged();
		sm.setMenu(v);
		
		// 设置头像
		ImageView userimageView = (ImageView)v.findViewById(R.id.imageview_main_behind_userimg);
		userimageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginManagerActivity.launch(MainActivity.this);
			}
		});
		
		if(YiyeApplication.user != null) {	// 若已经登陆，设置头像及姓名
			ImageLoader.getInstance().displayImage(YiyeApplication.user.avatar, userimageView,imageoptions);
			TextView usernameTextView = (TextView)v.findViewById(R.id.textview_main_behind_username);
			usernameTextView.setText(YiyeApplication.user.username);
		}
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,MainActivity.class);
		context.startActivity(i);
	}
}
