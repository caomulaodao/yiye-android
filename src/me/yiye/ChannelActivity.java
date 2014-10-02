package me.yiye;

import java.util.ArrayList;
import java.util.Collection;
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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.hb.views.PinnedSectionListView;
import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;

public class ChannelActivity extends SherlockActivity{
	
	private static Channel channel;
	private class Item {

		public static final int ITEM = 0;
		public static final int SECTION = 1;

		public final int type;
		public final BookMark bookmark;
		public Item(int type,BookMark bookmark) {
			this.type = type;
			this.bookmark = bookmark;
		}
	}
	
	
	// 按时间排序
	public class ComparatorBookMark implements Comparator<BookMark>{

		@Override
		 public int compare(BookMark b0, BookMark b1) {
			 return b0.getUploadDateTimeStamp() < b1.getUploadDateTimeStamp() ? 1 : -1;
		}
	}


	
	
	private class ChannelAdapter extends BaseAdapter implements PinnedSectionListAdapter{

		private List<Item> itemList = new ArrayList<Item>();
		private Context context;
		
		public ChannelAdapter(Context context,List<BookMark> bookMarkList) {
			BookMark.setDefaultPic(context, R.drawable.balidao);
			this.context = context;
			String currentDate = null;
			Item it;

			Collections.sort(bookMarkList,new ComparatorBookMark());
			
			for(BookMark bm:bookMarkList) {
				if(currentDate == null || !currentDate.equals(bm.getUploaddate())){ 
					it = new Item(Item.SECTION,bm);
					currentDate = bm.getUploaddate();
					itemList.add(it);
				}
				
				it = new Item(Item.ITEM,bm);
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

			View v;
			Item c = itemList.get(pos);
			
			if (convertView == null) {
				if(c.type == Item.ITEM) {
					v = View.inflate(context, R.layout.item_bookmark_style, null);
				} else {
					v = View.inflate(context, R.layout.item_bookmark_section_style, null);
				}
			} else {
				v = convertView;
			}
			
			ImageView imageView;
			TextView textView;
			
			if(c.type == Item.SECTION) {
				textView = (TextView) v.findViewById(R.id.textview_bookmark_section_title_item);
				textView.setText(c.bookmark.getUploaddate());
			} else {
				imageView = (ImageView) v.findViewById(R.id.imageview_bookmark_item);
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageBitmap(c.bookmark.getPic());
				
				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_title);
				textView.setText(c.bookmark.getTitle());
				
				textView = (TextView) v.findViewById(R.id.textview_bookmark_item_content);
				textView.setText(c.bookmark.getSummary());
			}
			return v;
		}

		@Override
		public int getViewTypeCount() {
			 return 2;
		}

		@Override
		public boolean isItemViewTypePinned(int viewType) {
			 return viewType == Item.SECTION;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}
		
	}
	
	private PinnedSectionListView bookMarkListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_channel);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.setTitle(channel.getTitle());
		PullToRefreshPinnedSectionListView tmpview = (PullToRefreshPinnedSectionListView) this.findViewById(R.id.listview_channel_bookmarks);
		bookMarkListView = tmpview.getRefreshableView();
		YiyeApi api = new YiyeApiTestImp();
		
		final ChannelAdapter ca = new ChannelAdapter(this, api.getBookMarksByChannel(channel));
		bookMarkListView.setAdapter(ca);
		ca.notifyDataSetChanged();
		
		bookMarkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				Item it = ca.getItem(pos);
				if(it.type == Item.ITEM) {
					ContentActivity.launch(ChannelActivity.this, it.bookmark);
				}
			}
			
		});
	}
	
	private static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context,ChannelActivity.class);
		context.startActivity(i);
	}
	
	public static void launch(Context context,Channel channel) {
		ChannelActivity.channel = channel;
		launch(context);
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
