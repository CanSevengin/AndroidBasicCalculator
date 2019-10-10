package com.humber.saynn.humbercalc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w =  MeasureSpec.getSize(widthMeasureSpec);
        int h =  MeasureSpec.getSize(heightMeasureSpec);
        int newheight = w*5/5;


        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(newheight, MeasureSpec.EXACTLY));
    }
}
