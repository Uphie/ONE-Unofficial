package com.uphie.one.ui.thing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.uphie.one.R;
import com.uphie.one.abs.AbsModuleFragment;
import com.uphie.one.abs.FragmentAdapter;
import com.uphie.one.common.Constants;
import com.uphie.one.ui.article.Article;
import com.uphie.one.ui.article.ArticleContentFragment;
import com.uphie.one.ui.home.HomeContentFragment;
import com.uphie.one.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class ThingFragment extends AbsModuleFragment {


    public static FragmentAdapter adapter;
    private String firstHomeDate;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_thing;
    }

    @Override
    public void init() {
        //先展示当天的首页
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.KEY_DATE, TimeUtil.getDate());
        bundle1.putInt(Constants.KEY_INDEX, 1);
        ThingContentFragment fragment1 = new ThingContentFragment();
        fragment1.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.KEY_DATE, TimeUtil.getDate());
        bundle2.putInt(Constants.KEY_INDEX, 2);
        ThingContentFragment fragment2 = new ThingContentFragment();
        fragment2.setArguments(bundle2);

        List<Fragment> list = new ArrayList<>();
        list.add(fragment1);
        list.add(fragment2);
        adapter = new FragmentAdapter(getChildFragmentManager(), list);
        pager.setAdapter(adapter);

        firstHomeDate = TimeUtil.getDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ThingPage");
        //如果首页的日期与当前不符，即数据过期，刷新数据。可能有bug
        if (firstHomeDate != null && !firstHomeDate.equals(TimeUtil.getDate())) {

            Bundle bundle1 = new Bundle();
            bundle1.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle1.putInt(Constants.KEY_INDEX, 1);
            ThingContentFragment fragment1 = new ThingContentFragment();
            fragment1.setArguments(bundle1);

            Bundle bundle2 = new Bundle();
            bundle2.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle2.putInt(Constants.KEY_INDEX, 2);
            ThingContentFragment fragment2 = new ThingContentFragment();
            fragment2.setArguments(bundle2);

            List<Fragment> list = new ArrayList<>();
            list.add(fragment1);
            list.add(fragment2);
            adapter = new FragmentAdapter(getChildFragmentManager(), list);
            pager.setAdapter(adapter);

            adapter.replaceAll(list);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ThingPage");
    }

    @Override
    public void onPageSelected(int position) {
        //当当前页为viewpager的最后一页时，加载下一页
        if (adapter.getCount() == position + 1) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle.putInt(Constants.KEY_INDEX, position + 2);
            ThingContentFragment fragment = new ThingContentFragment();
            fragment.setArguments(bundle);

            adapter.add(fragment);
        }
    }

    public Thing getCurThing() {
        return ((ThingContentFragment) adapter.getItem(pager.getCurrentItem())).getContentData();
    }
}
