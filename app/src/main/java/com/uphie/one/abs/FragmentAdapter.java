package com.uphie.one.abs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class FragmentAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private boolean canLoadMore = true;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList == null ? new ArrayList<Fragment>() : fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList.size() > 0 && fragmentList.size() > position) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public boolean canLoadMore() {
        return canLoadMore;
    }

    /**
     * 增加一个Fragment
     *
     * @param fragment
     */
    public void add(Fragment fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    /**
     * 更新全部Fragment
     *
     * @param fragmentList
     */
    public void updateAll(List<Fragment> fragmentList) {
        fragmentList.clear();
        fragmentList.addAll(fragmentList);
        notifyDataSetChanged();
    }

    /**
     * 删除最后一个fragment，因为最后一个是没有数据的
     */
    public void removeLast() {
        fragmentList.remove(fragmentList.size() - 1);
        notifyDataSetChanged();
        canLoadMore = false;
    }

    /**
     * 更新Fragment
     *
     * @param newFragments
     */
    public void replaceAll(List<Fragment> newFragments) {
        fragmentList.clear();
        fragmentList.addAll(newFragments);
        notifyDataSetChanged();
    }
}
