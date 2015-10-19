package com.uphie.one.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uphie.one.R;
import com.uphie.one.common.App;
import com.uphie.one.ui.article.ArticleFragment;
import com.uphie.one.ui.home.HomeFragment;
import com.uphie.one.ui.personal.PersonalFragment;
import com.uphie.one.ui.question.QuestionFragment;
import com.uphie.one.ui.thing.ThingFragment;
import com.uphie.one.utils.SysUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.tab_group)
    RadioGroup tabGroup;
    @Bind(R.id.action_bar_more)
    TextView actionBarMore;

    private HomeFragment homeFragment;
    private ArticleFragment articleFragment;
    private QuestionFragment questionFragment;
    private ThingFragment thingFragment;
    private PersonalFragment personalFragment;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.displayMetrics = SysUtil.getDisplayMetrics(this);

        actionBarMore.setOnClickListener(this);
        tabGroup.setOnCheckedChangeListener(this);

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_content, homeFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_more:

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (articleFragment != null) {
            transaction.hide(articleFragment);
        }
        if (questionFragment != null) {
            transaction.hide(questionFragment);
        }
        if (thingFragment != null) {
            transaction.hide(thingFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
        switch (checkedId) {
            case R.id.tab_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case R.id.tab_article:
                if (articleFragment == null) {
                    articleFragment = new ArticleFragment();
                    transaction.add(R.id.main_content, articleFragment);
                } else {
                    transaction.show(articleFragment);
                }
                break;
            case R.id.tab_question:
                if (questionFragment == null) {
                    questionFragment = new QuestionFragment();
                    transaction.add(R.id.main_content, questionFragment);
                } else {
                    transaction.show(questionFragment);
                }
                break;
            case R.id.tab_thing:
                if (thingFragment == null) {
                    thingFragment = new ThingFragment();
                    transaction.add(R.id.main_content, thingFragment);
                } else {
                    transaction.show(thingFragment);
                }
                break;
            case R.id.tab_personal:
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                    transaction.add(R.id.main_content, personalFragment);
                } else {
                    transaction.show(personalFragment);
                }
                break;
        }
        transaction.commit();
    }
}
