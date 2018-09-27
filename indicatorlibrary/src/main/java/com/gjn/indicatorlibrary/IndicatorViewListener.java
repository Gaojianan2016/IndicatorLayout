package com.gjn.indicatorlibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author gjn
 * @time 2018/9/27 15:54
 */

public interface IndicatorViewListener {
    View createView(Context context, ViewGroup viewGroup);

    View getPointView(View view);

    void setCustomView(View view, String title, int position);
}
