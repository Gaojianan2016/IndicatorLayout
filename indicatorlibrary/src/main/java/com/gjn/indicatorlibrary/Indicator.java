package com.gjn.indicatorlibrary;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Indicator
 * Author: gjn.
 * Time: 2018/2/10.
 */

public abstract class Indicator implements IndicatorViewListener {

    private static final String TAG = "Indicator";

    private static final int H = 60;

    public static final int TYPE_POINT =    0;
    public static final int TYPE_NUM =      1;
    public static final int TYPE_TEXT =     2;
    public static final int TYPE_CUSTOM =   3;

    private Context context;
    private LinearLayout linearLayout;
    private float density;
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
        density = context.getResources().getDisplayMetrics().density;
        pointWH = context.getResources().getDisplayMetrics().widthPixels / H;
        margin = pointWH / 2;
    }


    private void create() {
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
        linearLayout.setGravity(gravity);
        if (type == TYPE_POINT) {
            createPointIndicator();
        } else {
            createTextNumIndicator();
        }
        selectIndicator(0);
    }

    private void createPointIndicator() {
        for (int i = 0; i < indicatorSize; i++) {
            View view = createView(context, linearLayout);
            if (view == null) {
                view = new ImageView(context);
            }
            if (isMandatory) {
                view.setLayoutParams(getLayoutParams(pointWH, pointWH, margin));
            } else {
                view.setLayoutParams(getLayoutParams(margin));
            }
            linearLayout.addView(view);
            pointViews.add(getPointView(view));
        }
    }

    private void createTextNumIndicator() {
        View view = createView(context, linearLayout);
        if (view == null) {
            view = new MarqueeTextView(context);
        }
        linearLayout.addView(view);
        pointViews.add(getPointView(view));
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public Indicator setMandatory(boolean mandatory) {
        isMandatory = mandatory;
        return this;
    }

    public int getIndicatorSize() {
        return indicatorSize;
    }

    public Indicator setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
        return this;
    }

    public List<String> getTitles() {
        return titles;
    }

    public Indicator setTitles(List<String> titles) {
        this.titles = titles;
        return this;
    }

    public Indicator setType(int type) {
        this.type = type;
        return this;
    }

    public Indicator setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public Indicator setMargin(int marginDp) {
        this.margin = (int) (marginDp * density);
        return this;
    }

    public Indicator setImgState(int normalImg, int selectImg) {
        return setImgState(normalImg, selectImg, new ImageLoader() {
            @Override
            public void loadImg(Context context, Object img, ImageView imageView) {
                imageView.setImageResource((Integer) img);
            }
        });
    }

    public Indicator setImgState(Object normalImg, Object selectImg, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        if (normalImg instanceof Integer) {
            if ((int) normalImg != 0) {
                NormalImg = normalImg;
            } else {
                NormalImg = null;
            }
        } else {
            NormalImg = normalImg;
        }
        if (selectImg instanceof Integer) {
            if ((int) selectImg != 0) {
                SelectImg = selectImg;
            } else {
                SelectImg = null;
            }
        } else {
            SelectImg = selectImg;
        }
        return this;
    }

    public void changeBottomPadding(int bottomDp) {
        int bottom = (int) (density * bottomDp);
        linearLayout.setPadding(0, 0, 0, bottom);
    }

    public void changeType(int type) {
        changeType(type, titles);
    }

    public void changeType(int type, List<String> titles) {
        setType(type);
        updataView(titles);
    }

    public void changeGravity(int gravity) {
        setGravity(gravity);
        updataView();
    }

    public void changeImageMargin(int marginDp) {
        changeImageMargin(marginDp, marginDp, marginDp, marginDp);
    }

    public void changeImageMargin(int leftDp, int topDp, int rightDp, int bottomDp) {
        int left = (int) (density * leftDp);
        int top = (int) (density * topDp);
        int right = (int) (density * rightDp);
        int bottom = (int) (density * bottomDp);
        if (type == TYPE_POINT) {
            for (View pointView : pointViews) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                params.leftMargin = left;
                params.topMargin = top;
                params.rightMargin = right;
                params.bottomMargin = bottom;
                pointView.setLayoutParams(params);
            }
        }
    }

    public void reversalMandatory() {
        setMandatory(!isMandatory);
        if (type == TYPE_POINT) {
            updataView();
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

    public void selectIndicator(int position) {
        if (position >= indicatorSize) {
            position = 0;
        }
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
            MarqueeTextView textView = (MarqueeTextView) pointViews.get(0);
            textView.setText(titles.get(position));
        } else if (type == TYPE_NUM) {
            MarqueeTextView textView = (MarqueeTextView) pointViews.get(0);
            textView.setText((position + 1) + "/" + indicatorSize);
        } else {
            String title = "";
            if (titles != null && titles.size() > 0) {
                title = titles.get(position);
            }
            setCustomView(pointViews.get(0), title, position);
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

    @Override
    public View getPointView(View view) {
        return view;
    }

    @Override
    public void setCustomView(View view, String title, int position) {
    }

    public interface ImageLoader {
        void loadImg(Context context, Object img, ImageView imageView);
    }
}
