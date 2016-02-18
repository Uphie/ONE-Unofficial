package studio.uphie.one.ui.personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.uphie.one.R;
import studio.uphie.one.utils.TextToast;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class PersonalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PersonalPage");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PersonalPage");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.item_about, R.id.item_share_app, R.id.item_feedback, R.id.item_comment})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.item_about:
                intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.item_share_app:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "我发现一个不错的应用，ONE（非官方版），每天给你一个新心情：）\n Github: https://github.com/Uphie/ONE-Unofficial");
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.item_feedback:
                intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.item_comment:
                //跳转到应用商店，官方ONE的详情页
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=one.hh.oneclient"));
                //必须先检查是否有应用商店安装，否则可能会崩溃
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    //有应用商店
                    startActivity(intent);
                }else {
                    //无应用商店
                    TextToast.longShow(R.string.non_market_app);
                }
                break;
        }
    }
}
