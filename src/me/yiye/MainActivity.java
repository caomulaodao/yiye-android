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

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

public class MainActivity extends FragmentActivity {

	private JazzyViewPager mViewPager;
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	private SwitchBar mSwitchBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (JazzyViewPager) findViewById(R.id.main_pager);
		mViewPager.setTransitionEffect(TransitionEffect.Stack);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAppSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				mSwitchBar.setSelect(index);
			}
		});
		
		mSwitchBar = (SwitchBar)findViewById(R.id.switchbar_main);
	
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
			case 1:
				return new SearchFragment();
			default:
				return new PersonalFragment();
			}
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return position + "";
		}
	}
	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, MainActivity.class);
		context.startActivity(i);
	}

}
