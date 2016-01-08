package studio.uphie.one.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;

import studio.uphie.one.R;

/**
 * Created by Uphie on 2015/9/11.
 * Email: uphie7@gmail.com
 */
public class LikeView extends CheckBox implements CompoundButton.OnCheckedChangeListener {

    private int likeCount;
    private OnLikeChangedListener onLikeChangedListener;

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER_VERTICAL);
        refresh(isChecked());
        String text = getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            likeCount = 0;
        } else {
            likeCount = Integer.parseInt(text);
        }
        setText(likeCount + "");

        setOnCheckedChangeListener(this);
    }

    private void refresh(boolean checked) {
        String text = getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            likeCount = 0;
        } else {
            likeCount = Integer.parseInt(text);
        }
        if (checked) {
            setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_liked), null, null, null);
            likeCount++;
            setText(likeCount + "");
        } else {
            setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_like), null, null, null);
            if (likeCount > 0) {
                likeCount--;
            }
            setText(likeCount + "");
        }
    }

    public void addOnLikeChangeListener(OnLikeChangedListener onLikeChangedListener) {
        this.onLikeChangedListener = onLikeChangedListener;
    }

    public int getLikeCount(){
        return likeCount;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        refresh(isChecked);
        if (onLikeChangedListener != null) {
            onLikeChangedListener.onLikeChanged();
        }
    }

    public interface OnLikeChangedListener {
        void onLikeChanged();
    }
}
