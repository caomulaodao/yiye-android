package me.yiye;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.utils.MLog;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiImp;
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

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChannelActivity extends BaseActivity {

	private final static String TAG = "ChannelActivity";
	private static Channel channel;
	
	private ListView bookMarkListView;
	private ChannelAdapter bookMarkListViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_channel);
		initActionbar(channel.name);
		initChannelListView();
	}

	private void initChannelListView() {
		final YiyeApi api = new YiyeApiImp(this);
		
		PullToRefreshListView tmpview = (PullToRefreshListView) this.findViewById(R.id.listview_channel_bookmarks);
		bookMarkListView = tmpview.getRefreshableView();
		bookMarkListView.setBackgroundColor(getResources().getColor(R.color.activitybackgroud));
		
		bookMarkListViewAdapter = new ChannelAdapter(this);
		bookMarkListView.setAdapter(bookMarkListViewAdapter);

		bookMarkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Item it = bookMarkListViewAdapter.getItem(pos - 1);
				if (it.type == Item.ITEM) {
					BookMarkActivity.launch(ChannelActivity.this, it.getBookmark());
				}
			}
		});
	
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... v) {
				bookMarkListViewAdapter.setData(api.getBookMarksByChannel(channel));
				return null;
			}
			
			@Override
			protected void onPostExecute(Void v) {
				MLog.d(TAG, "onPostExecute### data changed");
				bookMarkListViewAdapter.notifyDataSetChanged();
			}
		}.execute();
		
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

			// 实现数据按时间分段
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

			ImageView imageView;
			TextView textView;

			if (c.getType() == Item.SECTION) {
				textView = (TextView) v.findViewById(R.id.textview_bookmark_section_title_item);
				textView.setText(c.getBookmark().postTime);
			} else {
				imageView = (ImageView) v.findViewById(R.id.imageview_bookmark_item);
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				ImageLoader.getInstance().displayImage(YiyeApi.PICCDN + c.getBookmark().image, imageView, YiyeApplication.imageoptions);

				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_title);
				textView.setText(c.getBookmark().title);

				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_content);
				textView.setText(c.getBookmark().description);
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
}
