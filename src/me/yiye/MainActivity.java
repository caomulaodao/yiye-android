package me.yiye;

import me.yiye.customwidget.SwitchBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

public class MainActivity extends FragmentActivity {

	private static JazzyViewPager mViewPager;
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	
	private SwitchBar mSwitchBar;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionbar("一叶");
		
		mViewPager = (JazzyViewPager) findViewById(R.id.main_pager);
//		mViewPager.setFadeEnabled(true);
		mViewPager.setTransitionEffect(TransitionEffect.Standard);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAppSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				mSwitchBar.setSelect(index);
			}
		});

		mSwitchBar = (SwitchBar) findViewById(R.id.switchbar_main);

		mSwitchBar.setOnClickLisener(0, new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
			}
		});
		
		mSwitchBar.setOnClickLisener(1, new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
			}
		});
	}

	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				return new ChannelsFragment();
			default:
				return new PersonalFragment();
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return position + "";
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
		    Object obj = super.instantiateItem(container, position);
		    mViewPager.setObjectForPosition(obj, position);
		    return obj;
		}
	}

	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, MainActivity.class);
		context.startActivity(i);
	}

	// 自定义的Actionbar
	protected void initActionbar(String title) {
		View barview = View.inflate(this,R.layout.view_main_actionbar,null);
		getActionBar().setCustomView(barview);
		getActionBar().setDisplayShowCustomEnabled(true);
		
		// 标题
		TextView titleTextView = (TextView) barview.findViewById(R.id.textview_actionbar_title);
		titleTextView.setText(title);
	}
}
