package studio.uphie.one.ui.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;
import studio.uphie.one.R;
import studio.uphie.one.abs.FragmentAdapter;
import studio.uphie.one.abs.AbsModuleFragment;
import studio.uphie.one.common.Constants;
import studio.uphie.one.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class ArticleFragment extends AbsModuleFragment {

    public static FragmentAdapter adapter;
    private String firstArticleDate;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void init() {
        //先展示当天的文章
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.KEY_DATE, TimeUtil.getDate());
        bundle1.putInt(Constants.KEY_INDEX, 1);
        ArticleContentFragment fragment1 = new ArticleContentFragment();
        fragment1.setArguments(bundle1);
        //昨天的文章
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.KEY_DATE, TimeUtil.getDate());
        bundle2.putInt(Constants.KEY_INDEX, 2);
        ArticleContentFragment fragment2 = new ArticleContentFragment();
        fragment2.setArguments(bundle2);

        List<Fragment> list = new ArrayList<>();
        list.add(fragment1);
        list.add(fragment2);
        adapter = new FragmentAdapter(getChildFragmentManager(), list);
        pager.setAdapter(adapter);

        firstArticleDate = TimeUtil.getDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ArticlePage");
        //如果首页的日期与当前不符，即数据过期，刷新数据。可能有bug
        if (firstArticleDate != null && !firstArticleDate.equals(TimeUtil.getDate())) {

            Bundle bundle1 = new Bundle();
            bundle1.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle1.putInt(Constants.KEY_INDEX, 1);
            ArticleContentFragment fragment1 = new ArticleContentFragment();
            fragment1.setArguments(bundle1);

            Bundle bundle2 = new Bundle();
            bundle2.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle2.putInt(Constants.KEY_INDEX, 2);
            ArticleContentFragment fragment2 = new ArticleContentFragment();
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
        MobclickAgent.onPageEnd("ArticlePage");
    }

    @Override
    public void onPageSelected(int position) {
        //当前某一个位置已经被选择了

        //当当前页为viewpager的倒数最后一页时，加载下一页
        if (adapter.getCount() == position + 1) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_DATE, TimeUtil.getDate());
            bundle.putInt(Constants.KEY_INDEX, position + 2);
            ArticleContentFragment fragment = new ArticleContentFragment();
            fragment.setArguments(bundle);

            adapter.add(fragment);
        }
    }

    public Article getCurArticle() {
        return ((ArticleContentFragment)adapter.getItem(pager.getCurrentItem())).getContentData();
    }
}
