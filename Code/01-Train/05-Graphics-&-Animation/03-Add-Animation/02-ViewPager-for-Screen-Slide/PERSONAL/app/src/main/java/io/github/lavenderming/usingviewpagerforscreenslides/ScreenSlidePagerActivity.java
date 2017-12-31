package io.github.lavenderming.usingviewpagerforscreenslides;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


/**
 * Created by 阿懂 on 2017/12/21.
 */

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * 本 demo 中显示的页面数量（向导步骤）
     */
    private static final int NUM_PAGES = 5;

    /**
     * ViewPager，处理动画以及允许左右滑动的小部件
     */
    private ViewPager mPager;

    /**
     * PagerAdapter，给 ViewPager 提供页面
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // 将 Activity 布局上的 pager 绑定到 mPager 实例化一个 ViewPager 和一个 PagerAdapter
        mPager = (ViewPager) findViewById(R.id.pager);

        // 实例化一个 PagerAdapter 并将其设为 mPager 的 adapter
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 一个简单的 pager adapter，能按顺序提供 5 个 ScreenSlidePageFragment 对象
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(ScreenSlidePageFragment.ARGUMENTS_BUNDLE_KEY_POSITION, position);
            screenSlidePageFragment.setArguments(bundle);

            return screenSlidePageFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
