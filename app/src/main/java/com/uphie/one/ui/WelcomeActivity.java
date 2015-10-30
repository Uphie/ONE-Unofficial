package com.uphie.one.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.uphie.one.R;
import com.uphie.one.abs.AbsBaseActivity;
import com.uphie.one.common.HttpError;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/10/23.
 * Email: uphie7@gmail.com
 */
public class WelcomeActivity extends AbsBaseActivity {

    @Bind(R.id.dv_welcome)
    SimpleDraweeView dvWelcome;
    @Bind(R.id.text_countdown)
    TextView textCountdown;

    private int count = 7;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init() {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setUri(Uri.parse("asset://com.uphie.one/welcome.gif")).build();
        dvWelcome.setController(draweeController);

        textCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //必须移除Message,否则Handler会继续执行，导致空指针异常
                handler.removeMessages(0);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onDataOk(String url, String data) {

    }

    @Override
    public void onDataError(String url, HttpError error) {

    }

    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (count > 0) {
                    textCountdown.setText(count + "");
                    count--;
                    sendEmptyMessageDelayed(0,1000);
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        }
    };

}
