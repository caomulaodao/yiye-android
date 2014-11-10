package me.yiye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class MainActivity extends FragmentActivity {

	private JazzyViewPager mViewPager;
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (JazzyViewPager) findViewById(R.id.main_pager);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAppSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				
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
			case 1:
				return new SearchFragment();
			default:
				// The other sections of the app are dummy placeholders.
				return new DummySectionFragment();
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

	public static class DummySectionFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			TextView rootView = new TextView(this.getActivity());
			rootView.setText("for furture");
			return rootView;
		}
	}
	
	public static void launch(Context context) {
		Intent i = new Intent();
		i.setClass(context, MainActivity.class);
		context.startActivity(i);
	}

}
