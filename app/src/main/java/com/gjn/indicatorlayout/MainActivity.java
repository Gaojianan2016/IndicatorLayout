package com.gjn.indicatorlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjn.indicatorlibrary.Indicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int type = 0;
    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = findViewById(R.id.ll);

        final Indicator indicator = new Indicator(this, 5, ll) {
            @Override
            protected View createView(Context context, ViewGroup viewGroup) {
                View view = null;
                switch (type) {
                    case 0:
                    case 3:
                        Log.e("-s-", "type = point");
                        view = new ImageView(context);
                        break;
                    case 1:
                        Log.e("-s-", "type = num");
                        view = new TextView(context);
                        break;
                    case 2:
                        Log.e("-s-", "type = text");
                        view = new TextView(context);
                        break;
                }
                return view;
            }

            @Override
            protected View getPointView(View view, int position) {
                if (type == 0) {
                    view.setBackgroundResource(R.drawable.select);
                }
                return view;
            }
        };

        final List<String> list = new ArrayList<>();
        list.add("text0");
        list.add("text1");
        list.add("text2");
        list.add("text3");
        list.add("text4");

        indicator.create();

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type++;
                if (type > 3) {
                    type = 0;
                }
                Log.e("-s-", " type = " + type);
                switch (type) {
                    case 0:
                        indicator.setImgState(0, 0);
                        indicator.changeType(Indicator.TYPE_POINT);
                        break;
                    case 1:
                        indicator.changeType(Indicator.TYPE_NUM);
                        break;
                    case 2:
                        indicator.changeType(Indicator.TYPE_TEXT, list);
                        break;
                    case 3:
                        indicator.setImgState(R.mipmap.homepage_banner_point02, R.mipmap.homepage_banner_point01);
                        indicator.changeType(Indicator.TYPE_POINT);
                        break;
                }
                indicator.selectIndicator(size);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size++;
                if (size >= indicator.getIndicatorSize()) {
                    size = 0;
                }
                Log.e("-s-", "size = " + size);
                indicator.selectIndicator(size);
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicator.setMandatory(!indicator.isMandatory());
                indicator.updataView();
                indicator.selectIndicator(size);
            }
        });
    }
}
