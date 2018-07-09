package com.gjn.indicatorlibrary;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private static final String TAG = "Indicator";

    public static final int TYPE_POINT =    0;
    public static final int TYPE_NUM =      1;
    public static final int TYPE_TEXT =     2;

    private Context context;
    private LinearLayout linearLayout;
    private int gravity = Gravity.CENTER;
    private int pointWH;
    private int margin;
    private boolean isMandatory;
    private List<View> pointViews = new ArrayList<>();
    private int indicatorSize;
    private List<String> titles;

    private int type = TYPE_POINT;

    private Object NormalImg;
    private Object SelectImg;
    private ImageLoader imageLoader;

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

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void changeType(int type) {
        setType(type);
        updataView();
    }

    public void changeType(int type, List<String> titles) {
        setType(type);
        updataView(titles);
    }

    public void setImgState(Object normalImg, Object selectImg, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        NormalImg = normalImg;
        SelectImg = selectImg;
    }

    public void setImgState(int normalImg, int selectImg) {
        this.imageLoader = new ImageLoader() {
            @Override
            public void loadImg(Context context, Object img, ImageView imageView) {
                imageView.setImageResource((Integer) img);
            }
        };
        if (normalImg != 0) {
            NormalImg = normalImg;
        } else {
            NormalImg = null;
        }
        if (selectImg != 0) {
            SelectImg = selectImg;
        } else {
            SelectImg = null;
        }
    }

    public void updataView() {
        updataView(indicatorSize);
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
        if (type == TYPE_TEXT) {
            if (titles != null) {
                indicatorSize = titles.size();
            } else {
                Log.e(TAG, "titles is null.");
                return;
            }
        }
        if (indicatorSize <= 0) {
            Log.e(TAG, "size is null.");
            return;
        }
        if (linearLayout == null) {
            Log.e(TAG, "linearLayout is null.");
            return;
        }
        if (NormalImg == null || SelectImg == null) {
            Log.d(TAG, "NormalImg or SelectImg is null.");
            imageLoader = null;
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
            ImageView point = (ImageView) getPointView(view, i);
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
        TextView point = (TextView) getPointView(view, 0);
        pointViews.add(point);
    }

    public void selectIndicator(int position) {
        if (type == TYPE_POINT) {
            for (int i = 0; i < indicatorSize; i++) {
                if (imageLoader != null) {
                    if (i == position) {
                        imageLoader.loadImg(context, SelectImg, (ImageView) pointViews.get(i));
                    } else {
                        imageLoader.loadImg(context, NormalImg, (ImageView) pointViews.get(i));
                    }
                } else {
                    if (i == position) {
                        pointViews.get(i).setSelected(true);
                    } else {
                        pointViews.get(i).setSelected(false);
                    }
                }
            }
        } else if (type == TYPE_TEXT) {
            TextView textView = (TextView) pointViews.get(0);
            textView.setText(titles.get(position));
        } else {
            TextView textView = (TextView) pointViews.get(0);
            textView.setText((position + 1) + "/" + indicatorSize);
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

    protected abstract View getPointView(View view, int position);

    public interface ImageLoader {
        void loadImg(Context context, Object img, ImageView imageView);
    }
}
