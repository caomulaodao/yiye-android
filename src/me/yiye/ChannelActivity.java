package me.yiye;

import java.util.ArrayList;
import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;
import me.yiye.utils.YiyeApiOfflineImp;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChannelActivity extends BaseActivity {
	private final static String TAG = "ChannelActivity";
	
	private static Channel channel;
	
	private ChannelAdapter bookMarkListViewAdapter;
	
	private ListView bookMarkListView;
	private PullToRefreshListView pullableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_channel);
		initActionbar(channel.name);
		initChannelListView();
	}

	private void initChannelListView() {
		pullableView = (PullToRefreshListView) this.findViewById(R.id.listview_channel_bookmarks);
		pullableView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.star));
		pullableView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				freshdata(new YiyeApiImp(ChannelActivity.this));
			}
		});
		
		bookMarkListView = pullableView.getRefreshableView();
		bookMarkListView.setBackgroundColor(getResources().getColor(R.color.activitybackgroud));
		bookMarkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
					BookMarkActivity.launch(ChannelActivity.this, bookMarkListViewAdapter.getItem(pos - 1));
			}
		});
		
		bookMarkListViewAdapter = new ChannelAdapter(this);
		bookMarkListView.setAdapter(bookMarkListViewAdapter);
		
		freshdata(new YiyeApiOfflineImp(this));
		
		// 添加列表最下一项的分割线
		// View v = View.inflate(this, R.layout.item_commom_divider_style, null);
		// bookMarkListView.addFooterView(v);
	}

	private class ChannelAdapter extends BaseAdapter {
		private List<BookMark> itemList = new ArrayList<BookMark>();
		private Context context;

		public ChannelAdapter(Context context) {
			this.context = context;
		}

		public void setData(List<BookMark> bookMarkList) {
			itemList = bookMarkList;
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public BookMark getItem(int pos) {
			return itemList.get(pos);
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			BookMark b = itemList.get(pos);
			View v;
			if (convertView == null) {
				v = View.inflate(context, R.layout.item_bookmark_style, null);
			} else {
				v = convertView;
			}

			ImageView contentImageView;
			TextView titleTextView;
			// TextView descriptionTextView;
			TextView praiseTextView;
			TextView uploaderTextView;

			contentImageView = (ImageView) v.findViewById(R.id.imageview_bookmark_item);
			contentImageView.setAdjustViewBounds(false);
			contentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			ImageLoader.getInstance().displayImage(YiyeApi.PICCDN + b.image, contentImageView,
					YiyeApplication.imageoptions);

			titleTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_title);
			titleTextView.setText(b.title);

			// descriptionTextView = (TextView)
			// v.findViewById(R.id.textview_bookmark_item_content);
			// descriptionTextView.setText(c.getBookmark().description);

			praiseTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_praise);
			praiseTextView.setText("赞 " + b.likeNum);

			uploaderTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_uploader);
			uploaderTextView.setText("上传者 " + b.postUser);
			return v;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}
	}

	private static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, ChannelActivity.class);
		context.startActivity(i);
	}

	public static void launch(Context context, Channel channel) {
		ChannelActivity.channel = channel;
		launch(context);
	}

	private void freshdata(final YiyeApi api) {
		
		// 获取书签数据
		new AsyncTask<Void, Void, List<BookMark>>() {
			@Override
			protected List<BookMark> doInBackground(Void... v) {
				return api.getBookMarksByChannel(channel);
			}
			
			@Override
			protected void onPostExecute(List<BookMark> list) {
				MLog.d(TAG, "onPostExecute### get the data of bookmark.");
				bookMarkListViewAdapter.setData(list);
				bookMarkListViewAdapter.notifyDataSetChanged();
				pullableView.onRefreshComplete();
				super.onPostExecute(list);
				
			}
		}.execute();
	}
}
