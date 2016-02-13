package studio.uphie.one.ui.home;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseFragment;
import studio.uphie.one.abs.AbsModuleFragment;
import studio.uphie.one.ui.FragmentAdapter;
import studio.uphie.one.utils.TimeUtil;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class HomeFragment extends AbsModuleFragment {

    private String firstHomeDate;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init() {
        refresh();
        firstHomeDate = TimeUtil.getDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomePage");
        //如果首页的日期与当前不符，即数据过期，刷新数据。可能有bug
        if (firstHomeDate != null && !firstHomeDate.equals(TimeUtil.getDate())) {
            refresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomePage");
    }

    @Override
    public void onPageSelected(int position) {
        //当前某一个位置已经被选择了

        //当当前页为viewpager的最后一页时，加载下一页
        if (adapter.getCount() == position + 1) {
            adapter.add(HomeContentFragment.newInstance(position + 2));
        }
    }

    @Override
    public void refresh() {
        //先展示当天的首页
        List<AbsBaseFragment> list = new ArrayList<>();
        list.add(HomeContentFragment.newInstance(1));
        list.add(HomeContentFragment.newInstance(2));

        adapter = new FragmentAdapter(getChildFragmentManager(), list);
        pager.setAdapter(adapter);

//        下面的代码无效，不明原因，换成了上面的两句代码。
//        先移除旧的Fragment后再实例化新的Fragment，adapter更新Fragment，新的Fragment不能实例化。明白的可以与我联系。
//        adapter.replaceAll(list);
    }

    public Home getCurSaying() {
        return ((HomeContentFragment) adapter.getItem(pager.getCurrentItem())).getContentData();
    }
}
