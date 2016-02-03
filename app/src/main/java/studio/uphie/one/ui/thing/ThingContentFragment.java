package studio.uphie.one.ui.thing;

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
import com.loopj.android.http.RequestParams;

import io.paperdb.Paper;
import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseFragment;
import studio.uphie.one.common.Api;
import studio.uphie.one.common.Constants;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;
import studio.uphie.one.ui.article.Article;
import studio.uphie.one.ui.article.ArticleFragment;
import studio.uphie.one.ui.home.HomeFragment;
import studio.uphie.one.utils.JsonUtil;
import studio.uphie.one.utils.TextToast;
import studio.uphie.one.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class ThingContentFragment extends AbsBaseFragment {

    @Bind(R.id.text_thing_date)
    TextView textThingDate;
    @Bind(R.id.dv_thing)
    SimpleDraweeView dvThing;
    @Bind(R.id.text_thing_name)
    TextView textThingName;
    @Bind(R.id.text_thing_intro)
    TextView textThingIntro;
    @Bind(R.id.thing_content)
    LinearLayout thingContent;

    /**
     * 当前的东西
     */
    private Thing curThing;

    @Override
    public int getLayoutId() {
        return R.layout.layout_thing;
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        index = bundle.getInt(Constants.KEY_INDEX);

        curDate = TimeUtil.getPreviousDate(date, index);

        RequestParams params = new RequestParams();
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_THING, params, new HttpData("rs", "entTg"));
    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_THING:
                Thing thing = JsonUtil.getEntity(data, Thing.class);
                refreshUI(thing);
                if (thing != null) {
                    Paper.book().write(Constants.TAG_THING + curDate, thing);
                }
                ThingFragment.getInstance().pager.onRefreshComplete();
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url) {
            case Api.URL_THING:
                ThingFragment.getInstance().pager.onRefreshComplete();
                //没有数据，删除并销毁自己
                finish();
                break;
        }
    }

    @Override
    public void onRestoreData(String url) {
        if (url.equals(Api.URL_THING)) {
            Thing thing = Paper.book().read(Constants.TAG_THING + curDate);
            refreshUI(thing);
            ThingFragment.getInstance().pager.onRefreshComplete();
        }
    }

    public Thing getContentData() {
        return curThing;
    }

    @Override
    public void refreshUI(Object data) {
        Thing thing = (Thing) data;
        if (thing == null) {
            if (!isFirstPage()) {
                //如果不是第一个，销毁之
                finish();
            }
            return;
        }
        if (isExpired()) {
            finish();
        }
        curThing = thing;

        thingContent.setVisibility(View.VISIBLE);

        //日期
        textThingDate.setText(TimeUtil.getEngDate(thing.strTm));
        //东西名称
        textThingName.setText(thing.strTt);
        //东西简介
        textThingIntro.setText(thing.strTc);
        //东西
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
                dvThing.setAspectRatio(rate);
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(thing.strBu))
                .setControllerListener(controllerListener)
                .build();
        dvThing.setController(controller);
    }

    @Override
    public void finish() {
        ThingFragment.adapter.removeFromAdapter(this);
    }

    public static ThingContentFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_DATE, TimeUtil.getDate());
        bundle.putInt(Constants.KEY_INDEX, index);
        ThingContentFragment fragment = new ThingContentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
