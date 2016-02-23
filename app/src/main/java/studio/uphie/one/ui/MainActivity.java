package studio.uphie.one.ui;

import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import studio.uphie.one.R;
import studio.uphie.one.common.App;
import studio.uphie.one.interfaces.ShareChannel;
import studio.uphie.one.ui.article.ArticleFragment;
import studio.uphie.one.ui.home.HomeFragment;
import studio.uphie.one.ui.personal.PersonalFragment;
import studio.uphie.one.ui.question.QuestionFragment;
import studio.uphie.one.ui.thing.ThingFragment;
import studio.uphie.one.utils.ConfigUtil;
import studio.uphie.one.utils.SysUtil;
import studio.uphie.one.utils.TextToast;

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

    private PopupWindow sharePanel;
    private long curTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.displayMetrics = SysUtil.getDisplayMetrics(this);

        actionBarMore.setOnClickListener(this);
        tabGroup.setOnCheckedChangeListener(this);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.main_content, homeFragment);
            transaction.commit();
        }

        setUmeng();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainPage");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainPage");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //不保存Fragment的状态，否则在后台长时间运行再返回后，会有Fragment重叠的情况
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //2秒之内的连续按返回键视为退出，防止误操作
            if (System.currentTimeMillis() - curTime < 2000) {
                finish();
                MobclickAgent.onKillProcess(this);
                System.exit(0);
            } else {
                TextToast.shortShow("再按一次退出");
                curTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_more:
                if (tabGroup.getCheckedRadioButtonId() != R.id.tab_personal) {
                    showSharePanel();
                }
                break;
            case R.id.share_facebook:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.FACEBOOK, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.FACEBOOK, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.FACEBOOK, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.FACEBOOK, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_twitter:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.TWITTER, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.TWITTER, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.TWITTER, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.TWITTER, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_google_plus:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.GOOGLE_PLUS, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.GOOGLE_PLUS, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.GOOGLE_PLUS, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.GOOGLE_PLUS, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_wechat:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.WECHAT, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.WECHAT, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.WECHAT, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.WECHAT, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_weibo:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.WEIBO, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.WEIBO, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.WEIBO, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.WEIBO, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_qq:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.QQ, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.QQ, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.QQ, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.QQ, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_qzone:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.QZONE, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.QZONE, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.QZONE, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.QZONE, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
            case R.id.btn_cancel:
                sharePanel.dismiss();
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

    private void showSharePanel() {
        if (sharePanel == null) {
            View view = View.inflate(this, R.layout.menu_share, null);
            view.findViewById(R.id.share_facebook).setOnClickListener(this);
            view.findViewById(R.id.share_twitter).setOnClickListener(this);
            view.findViewById(R.id.share_google_plus).setOnClickListener(this);
            view.findViewById(R.id.share_wechat).setOnClickListener(this);
            view.findViewById(R.id.share_weibo).setOnClickListener(this);
            view.findViewById(R.id.share_qq).setOnClickListener(this);
            view.findViewById(R.id.share_qzone).setOnClickListener(this);
            view.findViewById(R.id.btn_cancel).setOnClickListener(this);

            sharePanel = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            sharePanel.setOutsideTouchable(true);
            sharePanel.setFocusable(true);
            sharePanel.setBackgroundDrawable(new BitmapDrawable());
            sharePanel.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.alpha = 1f;
                    getWindow().setAttributes(layoutParams);
                }
            });
            sharePanel.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.3f;
            getWindow().setAttributes(layoutParams);
        } else {
            if (!sharePanel.isShowing()) {
                sharePanel.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 0.3f;
                getWindow().setAttributes(layoutParams);
            } else {
                sharePanel.dismiss();
            }
        }

    }

    private void setUmeng() {
        //对友盟统计日志加密
        AnalyticsConfig.enableEncrypt(true);
        //友盟统计不采集mac信息
        MobclickAgent.setCheckDevice(false);

        //禁止自动提示更新对话框
        UmengUpdateAgent.setUpdateAutoPopup(false);
        //禁止增量更新
        UmengUpdateAgent.setDeltaUpdate(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int status, UpdateResponse updateResponse) {
                switch (status) {
                    case UpdateStatus.Yes:
                        //有更新
                        showUpdateDialog(updateResponse);
                        break;
                    case UpdateStatus.No:
                        //无更新
                        break;
                    case UpdateStatus.NoneWifi:
                        //无wifi
                        break;
                    case UpdateStatus.Timeout:
                        //超时
                        break;
                }
            }
        });
        //友盟设置检查更新，不限于wifi
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        //禁用集成检测，否则会提示缺少xxx，然而我并不需要那些东西
        UmengUpdateAgent.setUpdateCheckConfig(false);
        //检查更新
        UmengUpdateAgent.update(this);

        //同步数据
        final FeedbackAgent agent = new FeedbackAgent(this);
//        agent.openFeedbackPush();      启用推送在小米手机上会有崩溃发生
        agent.sync();
        UserInfo userInfo = agent.getUserInfo();
        String nickname = ConfigUtil.readString("user", "nickname");
        if (TextUtils.isEmpty(nickname)) {
            final String n = generateNickname();
            Map<String, String> contact = new HashMap<>();
            contact.put("昵称", n);
            userInfo.setContact(contact);
            agent.setUserInfo(userInfo);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean success = agent.updateUserInfo();
                    if (success) {
                        ConfigUtil.writeString("user", "nickname", n);
                    }
                }
            }).start();

        }
        //启用推送
//        PushAgent.getInstance(this).enable();  启用推送在小米手机上会有崩溃发生
    }

    /**
     * 生成昵称
     *
     * @return 昵称
     */
    private String generateNickname() {
        String[] temp = getResources().getStringArray(R.array.nicknames);
        Random random = new Random();
        int index = random.nextInt(temp.length);
        return temp[index] + "-" + random.nextInt(1000);
    }

    private void showUpdateDialog(final UpdateResponse updateResponse) {
        View view = View.inflate(this, R.layout.dialog_update, null);
        TextView content = (TextView) view.findViewById(R.id.dialog_update_content);
        TextView cancel = (TextView) view.findViewById(R.id.dialog_update_cancel);
        TextView ok = (TextView) view.findViewById(R.id.dialog_update_ok);

        content.setText(String.format(getResources().getString(R.string.label_update_content), updateResponse.version, updateResponse.updateLog));

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //开始下载
                File file = UmengUpdateAgent.downloadedFile(MainActivity.this, updateResponse);
                if (file == null) {
                    //若未下载，下载
                    UmengUpdateAgent.startDownload(MainActivity.this, updateResponse);
                } else {
                    // 已经下载，直接安装
                    UmengUpdateAgent.startInstall(MainActivity.this, file);
                }
            }
        });

        dialog.show();
    }
}
