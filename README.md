# IndicatorLayout
自定义Indicator

- 依赖使用
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}


dependencies {
    implementation 'com.github.Gaojianan2016:IndicatorLayout:1.0.9'
}
```

# 基本使用
<br >
![基本使用](https://github.com/Gaojianan2016/ImgData/blob/master/Indicator_Demo/indicatorlayout_1.gif)
<br >

```
package com.gjn.indicatorlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjn.indicatorlibrary.Indicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int type = 0;
    private int size = 0;
    private EditText etBottom, etMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = findViewById(R.id.ll);
        etBottom = findViewById(R.id.et_bottom);
        etMargin = findViewById(R.id.et_margin);

        final Indicator indicator = new Indicator(this, 3, ll) {
            @Override
            public View createView(Context context, ViewGroup viewGroup) {
                switch (type) {
                    case 4:
                        return LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
                }
                return null;
            }

            @Override
            public View getPointView(View view) {
                if (type == 0) {
                    view.setBackgroundResource(R.drawable.select);
                }
                return super.getPointView(view);
            }

            @Override
            public void setCustomView(View view, String title, int position) {
                TextView tv_size = view.findViewById(R.id.tv_size);
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_size.setText((position + 1) + "/" + getIndicatorSize());
                tv_title.setText(title);
            }
        };

        final List<String> list = new ArrayList<>();
        list.add("Android 本地化翻译插件，解放你的双手！ AndroidLocalizePlugin");
        list.add("Android 技能图谱学习路线");
        list.add("MMKV for Android 多进程设计与实现");
        list.add("实现类似微博内容，@用户，链接高亮 ExpandableTextView");

        indicator.updataView();
        indicator.changeGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type++;
                if (type > 4) {
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
                    case 4:
                        indicator.changeType(Indicator.TYPE_CUSTOM);
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
                indicator.selectIndicator(size);
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicator.reversalMandatory();
                indicator.selectIndicator(size);
            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicator.changeBottomPadding(Integer.parseInt(etBottom.getText().toString()));
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int margin = Integer.parseInt(etMargin.getText().toString());
                indicator.changeImageMargin(margin);
            }
        });
    }
}
```
