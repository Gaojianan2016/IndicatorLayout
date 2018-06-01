package com.gjn.indicatorlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjn on 2018/5/30.
 */

public class IndicatorLayout extends LinearLayout {
    public static final int TYPE_POINT = 0;
    public static final int TYPE_NUM = 1;
    public static final int TYPE_TEXT = 2;
    private static final String TAG = "IndicatorLayout";

    private int pointWH;
    private int margin;
    private int indicatorSize;
    private List<String> textStrings;
    private int layoutId = -1;
    private List<View> pointViews = new ArrayList<>();

    private int type = TYPE_POINT;


    public IndicatorLayout(Context context) {
        this(context, null);
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        DisplayMetrics mMetrics = context.getResources().getDisplayMetrics();
        pointWH = mMetrics.widthPixels / 60;
        margin = pointWH / 2;
    }

    public void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
        updataView();
    }

    public void setTextStrings(List<String> textStrings) {
        this.textStrings = textStrings;
        updataView();
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        updataView();
    }

    private void updataView() {
        removeAllViews();

        if (textStrings != null && textStrings.size() > 0) {
            indicatorSize = textStrings.size();
            type = TYPE_TEXT;
        }
        if (indicatorSize <= 0) {
            Log.e(TAG, "indicatorSize is 0.");
            return;
        }

        if (type == TYPE_NUM) {
            createNum();
        }else if (type == TYPE_TEXT) {
            createText();
        }else {
            createPoint();
        }
        selectIndicator(0);
    }

    private void createNum() {

    }

    private void createText() {

    }

    private void createPoint() {
        pointViews.clear();
        for (int i = 0; i < indicatorSize; i++) {
            View view;
            if (layoutId == -1) {
                view = new ImageView(getContext());
                view.setBackgroundResource(R.drawable.ic_launcher);
                view.setLayoutParams(getLayoutParams(pointWH, pointWH, margin));
            }else {
                view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
            }
            addView(view);
            pointViews.add(view);
        }
    }

    private void selectIndicator(int position) {
        if (type == TYPE_POINT){
            for (int i = 0; i < pointViews.size(); i++) {
                if (i == position) {
                    pointViews.get(i).setSelected(true);
                }else {
                    pointViews.get(i).setSelected(false);
                }
            }
        }else {

        }
    }

    private LayoutParams getLayoutParams(int w, int h, int margin, float weight) {
        LayoutParams lp = new LayoutParams(w,h);
        lp.topMargin = margin;
        lp.bottomMargin = margin;
        lp.leftMargin = margin;
        lp.rightMargin = margin;
        if (weight > 0){
            lp.weight = weight;
        }
        return lp;
    }

    private LayoutParams getLayoutParams(int w, int h, int margin) {
        return getLayoutParams(w, h, margin, 0);
    }
}
