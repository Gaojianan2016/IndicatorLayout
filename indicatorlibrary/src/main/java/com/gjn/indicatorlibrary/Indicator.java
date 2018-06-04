package com.gjn.indicatorlibrary;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Indicator
 * Author: gjn.
 * Time: 2018/2/10.
 */

public abstract class Indicator {
    public static final int TYPE_POINT = 0;
    public static final int TYPE_NUM = 1;
    public static final int TYPE_TEXT = 2;

    private Context context;
    private LinearLayout linearLayout;
    private int gravity = Gravity.CENTER;
    private int pointWH;
    private int margin;
    private boolean isMandatory = true;
    private List<View> pointViews = new ArrayList<>();
    private int indicatorSize;
    private List<String> titles;

    private int type = TYPE_POINT;

    public Indicator(Context context, List<String> titles, LinearLayout linearLayout) {
        this.context = context;
        this.linearLayout = linearLayout;
        this.titles = titles;
        init();
    }

    public Indicator(Context context, int indicatorSize, LinearLayout linearLayout) {
        this.context = context;
        this.linearLayout = linearLayout;
        this.indicatorSize = indicatorSize;
        init();
    }

    private void init() {
        DisplayMetrics mMetrics = context.getResources().getDisplayMetrics();
        pointWH = mMetrics.widthPixels / 60;
        margin = pointWH / 2;
    }

    public void setType(int type) {
        this.type = type;
        if (type != TYPE_TEXT) {
            titles = null;
        }
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public int getIndicatorSize() {
        return indicatorSize;
    }

    public void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void updataView() {
        updataView(indicatorSize);
    }

    public void changeType(int type){
        setType(type);
        updataView();
    }

    public void changeType(int type, List<String> titles){
        setType(type);
        updataView(titles);
    }

    public void updataView(int indicatorSize) {
        updataView(indicatorSize, titles);
    }

    public void updataView(List<String> titles) {
        updataView(indicatorSize, titles);
    }

    public void updataView(int size, List<String> titles) {
        setIndicatorSize(size);
        setTitles(titles);
        create();
    }

    public void create() {
        if (titles != null && titles.size() > 0) {
            indicatorSize = titles.size();
            type = TYPE_TEXT;
        }
        if (indicatorSize <= 0) {
            throw new NullPointerException("size is null");
        }
        if (linearLayout == null) {
            throw new NullPointerException("linearLayout is null");
        }

        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        pointViews.clear();
        if (type == TYPE_POINT) {
            createPointIndicator();
        } else {
            createTextNumIndicator();
        }
        selectIndicator(0);
    }

    private void createPointIndicator() {
        linearLayout.setGravity(gravity);
        for (int i = 0; i < indicatorSize; i++) {
            View view = createView(context, linearLayout);
            ImageView point = (ImageView) getPointView(view);
            if (isMandatory) {
                view.setLayoutParams(getLayoutParams(pointWH, pointWH, margin));
            } else {
                view.setLayoutParams(getLayoutParams(margin));
            }
            linearLayout.addView(view);
            pointViews.add(point);
        }
    }

    private void createTextNumIndicator() {
        linearLayout.setGravity(gravity);
        View view = createView(context, linearLayout);
        linearLayout.addView(view);
        TextView point = (TextView) getPointView(view);
        pointViews.add(point);
    }

    public void selectIndicator(int position) {
        if (type == TYPE_POINT) {
            for (int i = 0; i < indicatorSize; i++) {
                if (i == position) {
                    pointViews.get(i).setSelected(true);
                } else {
                    pointViews.get(i).setSelected(false);
                }
            }
        } else {
            TextView textView = (TextView) pointViews.get(0);
            if (titles != null && titles.size() > 0) {
                textView.setText(titles.get(position));
            } else {
                textView.setText((position + 1) + "/" + indicatorSize);
            }
        }
    }

    private LinearLayout.LayoutParams getLayoutParams(int w, int h, int margin, float weight) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
        lp.topMargin = margin;
        lp.bottomMargin = margin;
        lp.leftMargin = margin;
        lp.rightMargin = margin;
        if (weight > 0) {
            lp.weight = weight;
        }
        return lp;
    }

    private LinearLayout.LayoutParams getLayoutParams(int w, int h, int margin) {
        return getLayoutParams(w, h, margin, 0);
    }

    private LinearLayout.LayoutParams getLayoutParams(int margin) {
        return getLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, margin);
    }

    protected abstract View createView(Context context, ViewGroup viewGroup);

    protected abstract View getPointView(View view);
}
