package com.uphie.one.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

import com.uphie.one.R;

public class Tab extends RadioButton {
    /**
     * 选择的背景资源id
     */
    private int selectedBackground;
    /**
     * 未选择的背景资源id
     */
    private int unselectedBackground;
    /**
     * 选择的文字颜色资源id
     */
    private int selectedTextColor;
    /**
     * 未选择的文字颜色资源id
     */
    private int unselectedTextColor;

    public Tab(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.Tab);

        selectedBackground = array.getResourceId(R.styleable.Tab_checkedBackground, -1);
        unselectedBackground = array.getResourceId(R.styleable.Tab_uncheckedBackground, -1);

        selectedTextColor = array.getResourceId(R.styleable.Tab_checkedTextColor, android.R.color.holo_blue_light);
        unselectedTextColor = array.getResourceId(R.styleable.Tab_uncheckedTextColor, android.R.color.black);

        array.recycle();

        setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
        setGravity(Gravity.CENTER_HORIZONTAL);
        setCompoundDrawablePadding(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isChecked()) {
            setTextColor(getResources().getColor(selectedTextColor));
            if (selectedBackground > 0) {
                Drawable selectedDrawable = getResources().getDrawable(selectedBackground);
                setCompoundDrawablesWithIntrinsicBounds(null, selectedDrawable, null, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        } else {
            setTextColor(getResources().getColor(unselectedTextColor));
            if (unselectedBackground > 0) {
                Drawable unselectedDrawable = getResources().getDrawable(unselectedBackground);
                setCompoundDrawablesWithIntrinsicBounds(null, unselectedDrawable, null, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
        super.onDraw(canvas);
    }
}
