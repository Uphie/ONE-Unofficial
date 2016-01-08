package studio.uphie.one.ui.home;

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
import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseFragment;
import studio.uphie.one.common.Api;
import studio.uphie.one.common.Constants;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;
import studio.uphie.one.ui.article.Article;
import studio.uphie.one.ui.article.ArticleFragment;
import studio.uphie.one.utils.JsonUtil;
import studio.uphie.one.utils.TextToast;
import studio.uphie.one.utils.TimeUtil;
import studio.uphie.one.widgets.LikeView;

import org.json.JSONException;
import org.json.JSONObject;

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
    LikeView lvSaying;
    @Bind(R.id.dv_illustrator)
    SimpleDraweeView dvIllustrator;

    private Home curSaying;

    @Override
    public int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    public void init() {

        lvSaying.addOnLikeChangeListener(this);

        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        int index = bundle.getInt(Constants.KEY_INDEX);

        RequestParams params = new RequestParams();
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_HOME, params, new HttpData("result", "hpEntity"));
    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_HOME:
                Home home = JsonUtil.getEntity(data, Home.class);
                if (home == null) {
                    return;
                }
                curSaying = home;

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
                lvSaying.setText(home.strPn);
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
                        float rate = (float) imageInfo.getWidth() / (float) imageInfo.getHeight();
                        dvIllustrator.setAspectRatio(rate);
                    }
                };
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(home.strThumbnailUrl))
                        .setControllerListener(controllerListener)
                        .build();
                dvIllustrator.setController(controller);
                break;
            case Api.URL_LIKE_OR_CANCLELIKE:
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int likeCount = jsonObject.optInt("strPraisednumber");
                    //若实际的喜欢数量与LikeView自增的结果值不同，显示实际的数量
                    if (likeCount != lvSaying.getLikeCount()) {
                        lvSaying.setText(likeCount + "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url) {
            case Api.URL_HOME:
                //没有数据，删除并销毁自己
                HomeFragment.adapter.removeLast();
                onDestroy();
                break;
        }

    }

    @Override
    public void onLikeChanged() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("strPraiseItemId", curSaying.strHpId);
        requestParams.put("strDeviceId", "");
        requestParams.put("strAppName", "ONE");
        requestParams.put("strPraiseItem", "HP");
        getHttpData(Api.URL_LIKE_OR_CANCLELIKE, requestParams, new HttpData("result", "entPraise"));
    }

    public Home getContentData() {
        return curSaying;
    }
}
