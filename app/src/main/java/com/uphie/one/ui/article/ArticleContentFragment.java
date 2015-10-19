package com.uphie.one.ui.article;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.uphie.one.R;
import com.uphie.one.abs.AbsBaseFragment;
import com.uphie.one.common.Api;
import com.uphie.one.common.Constants;
import com.uphie.one.common.HttpData;
import com.uphie.one.common.HttpError;
import com.uphie.one.utils.JsonUtil;
import com.uphie.one.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class ArticleContentFragment extends AbsBaseFragment implements View.OnClickListener {

    @Bind(R.id.text_article_date)
    TextView textArticleDate;
    @Bind(R.id.text_article_title)
    TextView text_ArticleTitle;
    @Bind(R.id.text_article_author)
    TextView text_ArticleAuthor;
    @Bind(R.id.text_article_content)
    TextView text_ArticleContent;
    @Bind(R.id.text_article_editor)
    TextView text_ArticleEditor;
    @Bind(R.id.text_like_article)
    TextView text_LikeArticle;
    @Bind(R.id.text_article_author_addition)
    TextView text_ArticleAuthorAddition;
    @Bind(R.id.text_article_author_weibo)
    TextView text_ArticleAuthorWeibo;
    @Bind(R.id.text_article_author_intro)
    TextView text_ArticleAuthorIntro;
    @Bind(R.id.article_content)
    LinearLayout articleContent;

    @Override
    public int getLayoutId() {
        return R.layout.layout_article;
    }

    @Override
    public HttpData getDataStructure() {
        return new HttpData("result", "contentEntity");
    }

    @Override
    public void init() {

        text_LikeArticle.setOnClickListener(this);
        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        int index = bundle.getInt(Constants.KEY_INDEX);

        RequestParams params = new RequestParams();
        params.put("strMS", 1);
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_ARTICLE, params);


    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_ARTICLE:
                Article article = JsonUtil.getEntity(data, Article.class);
                if (article == null) {
                    return;
                }
                articleContent.setVisibility(View.VISIBLE);
                //上架日期
                textArticleDate.setText(TimeUtil.getEngDate(article.strContMarketTime));
                //标题
                text_ArticleTitle.setText(article.strContTitle);
                //作者
                text_ArticleAuthor.setText(article.strContAuthor);
                //文章内容，内容中有HTML标记，用于正常显示段落，需要解析
                text_ArticleContent.setText(Html.fromHtml(article.strContent));
                //责任编辑
                text_ArticleEditor.setText(article.strContAuthorIntroduce);
                //喜欢的数量
                text_LikeArticle.setText(article.strPraiseNumber);
                //作者
                text_ArticleAuthorAddition.setText(article.strContAuthor);
                //微博名
                text_ArticleAuthorWeibo.setText(article.sWbN);
                //作者简介
                text_ArticleAuthorIntro.setText(article.sAuth);
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url){
            case Api.URL_ARTICLE:
                //没有数据，删除并销毁自己
                ArticleFragment.adapter.removeLast();
                onDestroy();
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_like_article:

                break;
        }
    }
}
