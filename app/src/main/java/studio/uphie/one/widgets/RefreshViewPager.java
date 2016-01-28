package studio.uphie.one.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Uphie on 2016/1/19 0019.
 * Email:uphie7@gmail.com
 */
public class RefreshViewPager extends ViewPager {

    public RefreshViewPager(Context context) {
        super(context);
    }

    public RefreshViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public interface OnScrollLisener{

    }

}
