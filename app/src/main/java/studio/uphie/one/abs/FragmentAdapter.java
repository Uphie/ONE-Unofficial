package studio.uphie.one.abs;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class FragmentAdapter<T extends AbsBaseFragment> extends android.support.v4.app.FragmentPagerAdapter {

    private List<T> fragmentList;
    private boolean canLoadMore = true;

    public FragmentAdapter(FragmentManager fm, List<T> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList == null ? new ArrayList<T>() : fragmentList;
    }

    @Override
    public T getItem(int position) {
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
    public void add(T fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    /**
     * 从Adapter中移除Fragment
     *
     * @param fragment 要移除的Fragment
     */
    public void removeFromAdapter(T fragment) {
        fragmentList.remove(fragment);
        fragment.onDestroy();
        canLoadMore = false;
    }

    /**
     * 删除所有的Fragment
     */
    public void removeAll() {
        for (AbsBaseFragment f : fragmentList) {
            f.onDestroyView();
            f.onDestroy();
        }
        fragmentList.clear();
        notifyDataSetChanged();
    }

    /**
     * 更新Fragment
     *
     * @param newFragments 新的Fragment
     */
    public void replaceAll(List<T> newFragments) {
        removeAll();
        fragmentList.addAll(newFragments);
        notifyDataSetChanged();
        canLoadMore=true;
    }
}
