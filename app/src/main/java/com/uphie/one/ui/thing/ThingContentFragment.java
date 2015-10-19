package com.uphie.one.ui.thing;

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
import com.uphie.one.R;
import com.uphie.one.abs.AbsBaseFragment;
import com.uphie.one.common.Api;
import com.uphie.one.common.Constants;
import com.uphie.one.common.HttpData;
import com.uphie.one.common.HttpError;
import com.uphie.one.utils.JsonUtil;
import com.uphie.one.utils.TextToast;
import com.uphie.one.utils.TimeUtil;

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

    @Override
    public int getLayoutId() {
        return R.layout.layout_thing;
    }

    @Override
    public HttpData getDataStructure() {
        return new HttpData("rs", "entTg");
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        int index = bundle.getInt(Constants.KEY_INDEX);

        RequestParams params = new RequestParams();
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_THING, params);
    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_THING:
                Thing thing = JsonUtil.getEntity(data, Thing.class);
                if (thing == null) {
                    return;
                }
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
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url) {
            case Api.URL_THING:
                //没有数据，删除并销毁自己
                ThingFragment.adapter.removeLast();
                onDestroy();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
