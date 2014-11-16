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
		// bookMarkListView.setBackgroundColor(getResources().getColor(R.color.activitybackgroud));
		bookMarkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Item it = bookMarkListViewAdapter.getItem(pos - 1);
				if (it.type == Item.ITEM) {
					BookMarkActivity.launch(ChannelActivity.this, it.getBookmark());
				}
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
		private List<Item> itemList = new ArrayList<Item>();
		private Context context;

		public ChannelAdapter(Context context) {
			this.context = context;
		}

		public void setData(List<BookMark> bookMarkList) {
			//TODO 实现数据按时间分段
			itemList.clear();
			String currentDate = null;
			for (BookMark bm : bookMarkList) {
				if (currentDate == null || !currentDate.equals(bm.postTime)) {
					Item it = new Item(Item.SECTION, bm);
					currentDate = bm.postTime;
					itemList.add(it);
				}

				Item it = new Item(Item.ITEM, bm);
				itemList.add(it);
			}
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Item getItem(int pos) {
			return itemList.get(pos);
		}

		@Override
		public int getItemViewType(int pos) {
			return getItem(pos).type;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			Item c = itemList.get(pos);
			View v;
			if (convertView == null) {
				if (c.type == Item.ITEM) {
					v = View.inflate(context, R.layout.item_bookmark_style, null);
				} else {
					v = View.inflate(context, R.layout.item_bookmark_section_style, null);
				}
			} else {
				v = convertView;
			}

			ImageView contentImageView;
			TextView titleTextView;
			// TextView descriptionTextView;
			TextView praiseTextView; 
			TextView uploaderTextView;
			
			if (c.getType() == Item.SECTION) {
				titleTextView = (TextView) v.findViewById(R.id.textview_bookmark_section_title_item);
				titleTextView.setText(c.getBookmark().postTime);
			} else {
				contentImageView = (ImageView) v.findViewById(R.id.imageview_bookmark_item);
				contentImageView.setAdjustViewBounds(false);
				contentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				ImageLoader.getInstance().displayImage(YiyeApi.PICCDN + c.getBookmark().image, contentImageView, YiyeApplication.imageoptions);

				titleTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_title);
				titleTextView.setText(c.getBookmark().title);

				// descriptionTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_content);
				// descriptionTextView.setText(c.getBookmark().description);
				
				praiseTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_praise);
				praiseTextView.setText("赞 " + c.getBookmark().likeNum);
				
				uploaderTextView = (TextView) v.findViewById(R.id.textview_bookmark_item_uploader);
				uploaderTextView.setText("上传者 " + c.getBookmark().postUser);
			}
			return v;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}


		@Override
		public long getItemId(int id) {
			return id;
		}
	}

	private class Item {
		public static final int ITEM = 0;
		public static final int SECTION = 1;

		private final int type;
		private final BookMark bookmark;

		public int getType() {
			return type;
		}

		public BookMark getBookmark() {
			return bookmark;
		}

		public Item(int type, BookMark bookmark) {
			this.type = type;
			this.bookmark = bookmark;
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
