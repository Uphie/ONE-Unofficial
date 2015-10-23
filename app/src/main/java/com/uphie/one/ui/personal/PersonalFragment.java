package com.uphie.one.ui.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.uphie.one.R;

import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class PersonalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal,null);
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
}
