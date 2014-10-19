package me.yiye;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.utils.YiyeApi;
import me.yiye.utils.YiyeApiTestImp;
import android.content.Context;
import android.content.Intent;
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

	private static Channel channel;
	
	private ListView bookMarkListView;
	private ChannelAdapter bookMarkListViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_channel);
		initActionbar(channel.getTitle());
		initChannelListView();
	}

	private void initChannelListView() {
		YiyeApi api = new YiyeApiTestImp(this);
		
		PullToRefreshListView tmpview = (PullToRefreshListView) this.findViewById(R.id.listview_channel_bookmarks);
		bookMarkListView = tmpview.getRefreshableView();
		bookMarkListView.setBackgroundColor(getResources().getColor(R.color.activitybackgroud));
		
		bookMarkListViewAdapter = new ChannelAdapter(this, api.getBookMarksByChannel(channel));
		bookMarkListView.setAdapter(bookMarkListViewAdapter);
		bookMarkListViewAdapter.notifyDataSetChanged();

		bookMarkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Item it = bookMarkListViewAdapter.getItem(pos - 1);
				if (it.type == Item.ITEM) {
					BookMarkActivity.launch(ChannelActivity.this, it.getBookmark());
				}
			}
		});
	
		// 添加列表最下一项的分割线
		// View v = View.inflate(this, R.layout.item_commom_divider_style, null);
		// bookMarkListView.addFooterView(v);
	}

	// 按时间排序书签
	public class ComparatorBookMark implements Comparator<BookMark> {

		@Override
		public int compare(BookMark b0, BookMark b1) {
			return b0.getUploadDateTimeStamp() < b1.getUploadDateTimeStamp() ? 1 : -1;
		}
	}

	private class ChannelAdapter extends BaseAdapter {

		private List<Item> itemList = new ArrayList<Item>();
		private Context context;

		public ChannelAdapter(Context context, List<BookMark> bookMarkList) {
			this.context = context;

			// 排序并实现数据按时间分段
			String currentDate = null;
			Collections.sort(bookMarkList, new ComparatorBookMark());
			for (BookMark bm : bookMarkList) {
				if (currentDate == null || !currentDate.equals(bm.getUploaddate())) {
					Item it = new Item(Item.SECTION, bm);
					currentDate = bm.getUploaddate();
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
				textView.setText(c.getBookmark().getUploaddate());
			} else {
				imageView = (ImageView) v.findViewById(R.id.imageview_bookmark_item);
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				ImageLoader.getInstance().displayImage(c.getBookmark().getImgUrl(), imageView, YiyeApplication.imageoptions);

				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_title);
				textView.setText(c.getBookmark().getTitle());

				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_content);
				textView.setText(c.getBookmark().getSummary());
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
