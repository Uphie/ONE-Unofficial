package com.uphie.one.ui.home;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.loopj.android.http.RequestParams;
import com.uphie.one.R;
import com.uphie.one.abs.AbsBaseFragment;
import com.uphie.one.common.Api;
import com.uphie.one.common.Constants;
import com.uphie.one.common.HttpData;
import com.uphie.one.common.HttpError;
import com.uphie.one.ui.article.ArticleFragment;
import com.uphie.one.utils.JsonUtil;
import com.uphie.one.utils.TextToast;
import com.uphie.one.utils.TimeUtil;
import com.uphie.one.widgets.LikeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class HomeContentFragment extends AbsBaseFragment implements LikeView.OnLikeChangedListener {

    @Bind(R.id.saying_title)
    TextView sayingTitle;
    @Bind(R.id.text_illustration_author)
    TextView textIllustrationAuthor;
    @Bind(R.id.text_day)
    TextView textDay;
    @Bind(R.id.text_month)
    TextView textMonth;
    @Bind(R.id.text_saying)
    TextView textSaying;
    @Bind(R.id.home_content)
    LinearLayout homeContent;
    @Bind(R.id.liv_saying)
    LikeView like_Saying;
    @Bind(R.id.dv_illustrator)
    SimpleDraweeView dvIllustrator;

    @Override
    public int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    public void init() {

        like_Saying.addOnLikeChangeListener(this);

        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        int index = bundle.getInt(Constants.KEY_INDEX);

        RequestParams params = new RequestParams();
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_HOME, params,new HttpData("result", "hpEntity"));
    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_HOME:
                Home home = JsonUtil.getEntity(data, Home.class);
                if (home == null) {
                    return;
                }
                homeContent.setVisibility(View.VISIBLE);

                //标题，如 VOL.1997
                sayingTitle.setText(home.strHpTitle);
                //照片名称及作者简介
                textIllustrationAuthor.setText(home.strAuthor.replace("&", "\n"));
                //日
                textDay.setText(TimeUtil.getDay(home.strMarketTime));
                //月、年
                textMonth.setText(TimeUtil.getMonthAndYear(home.strMarketTime));
                //内容
                textSaying.setText(home.strContent);
                //喜欢的数量
                like_Saying.setText(home.strPn);
                //插画

                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        TextToast.shortShow("加载失败:" + throwable.toString());
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        super.onIntermediateImageSet(id, imageInfo);
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null) {
                            return;
                        }
                        float rate = (float)imageInfo.getWidth() / (float)imageInfo.getHeight();
                        dvIllustrator.setAspectRatio(rate);
                    }
                };
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(home.strThumbnailUrl))
                        .setControllerListener(controllerListener)
                        .build();
                dvIllustrator.setController(controller);
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url) {
            case Api.URL_HOME:
                //没有数据，删除并销毁自己
                ArticleFragment.adapter.removeLast();
                onDestroy();
                break;
        }

    }

    @Override
    public void onLiked(boolean like) {
        RequestParams requestParams = new RequestParams();
    }

}
