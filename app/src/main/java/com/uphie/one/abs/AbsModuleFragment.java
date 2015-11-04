package com.uphie.one.abs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.uphie.one.R;
import com.uphie.one.interfaces.IInit;
import com.uphie.one.interfaces.IShare;
import com.uphie.one.interfaces.ShareChannel;
import com.uphie.one.ui.article.Article;
import com.uphie.one.ui.home.Home;
import com.uphie.one.ui.question.Question;
import com.uphie.one.ui.thing.Thing;
import com.uphie.one.utils.TextToast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/10/30.
 * Email: uphie7@gmail.com
 */
public abstract class AbsModuleFragment extends Fragment implements IInit, ViewPager.OnPageChangeListener, IShare {

    @Bind(R.id.pager)
    public ViewPager pager;

    public ShareDialog shareDialog;
    CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                TextToast.shortShow(getString(R.string.share_fail));
            }
        });
        pager.addOnPageChangeListener(this);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void share(int channel, Object data) {
        if (data == null) {
            return;
        }
        switch (channel) {
            case ShareChannel.FACEBOOK:
                if (data instanceof Home) {
                    Home home = (Home) data;
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setImageUrl(Uri.parse(home.strThumbnailUrl))
                            .setContentDescription(home.strContent)
                            .setContentTitle("「ONE·一个」 【句子】" + home.strMarketTime)
                            .setContentUrl(Uri.parse(home.sWebLk)).build();
                    if (shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC)) {
                        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                    }
                } else if (data instanceof Article) {
                    Article article = (Article) data;
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentDescription(article.strContent.substring(0,30)+"……")
                            .setContentTitle("「ONE·一个」 【文章】" + article.strContMarketTime)
                            .setContentUrl(Uri.parse(article.sWebLk)).build();
                    if (shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC)) {
                        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                    }
                } else if (data instanceof Question) {
                    Question question = (Question) data;
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentDescription(question.strQuestionTitle)
                            .setContentTitle("「ONE·一个」 【问题】" + question.strQuestionMarketTime)
                            .setContentUrl(Uri.parse(question.sWebLk)).build();
                    if (shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC)) {
                        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                    }
                } else {
                    Thing thing = (Thing) data;
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setImageUrl(Uri.parse(thing.strBu))
                            .setContentDescription(thing.strTc)
                            .setContentTitle("「ONE·一个」 【东西】" + thing.strTm)
                            .setContentUrl(Uri.parse(thing.strWu)).build();
                    if (shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC)) {
                        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                    }
                }
                break;
            case ShareChannel.GOOGLE_PLUS:
            case ShareChannel.TWITTER:
            case ShareChannel.WECHAT:
            case ShareChannel.WEIBO:
            case ShareChannel.QQ:
            case ShareChannel.QZONE:
                TextToast.longShow(getString(R.string.not_support_share));
                break;
        }
    }
}
