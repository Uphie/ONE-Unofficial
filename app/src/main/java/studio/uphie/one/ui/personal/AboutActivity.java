package studio.uphie.one.ui.personal;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseActivity;

/**
 * Created by Uphie on 2015/10/9.
 * Email: uphie7@gmail.com
 */
public class AboutActivity extends AbsBaseActivity {

    @Bind(R.id.text_about)
    TextView textAbout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void init() {
        setTitle("关于");

        String s = getResources().getString(R.string.content_about);
        int start=s.indexOf("『");
        int end=s.indexOf("』")+1;
        SpannableString content = new SpannableString(s);
        //ClickableSpan会默认给区域内的文件设为下划线、绿色字体
        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=one.hh.oneclient"));
                startActivity(intent);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //此处将上面的绿色字体覆盖为了蓝色（前景色即为字体的颜色）
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textAbout.setText(content);
    }

}
