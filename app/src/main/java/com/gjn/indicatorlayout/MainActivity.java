package com.gjn.indicatorlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5, ll_6, ll_7, ll_8;
    Indicator indicator1, indicator2, indicator3, indicator4, indicator5, indicator6, indicator7, indicator8;
    List<Indicator> indicators;
    List<String> list;
    private int type = 0;
    private int size = 0;
    private EditText etBottom, etMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBottom = findViewById(R.id.et_1);
        etMargin = findViewById(R.id.et_2);

        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
        ll_6 = findViewById(R.id.ll_6);
        ll_7 = findViewById(R.id.ll_7);
        ll_8 = findViewById(R.id.ll_8);

        list = new ArrayList<>();
        list.add("Android 本地化翻译插件，解放你的双手！ AndroidLocalizePlugin");
        list.add("Android 技能图谱学习路线");
        list.add("MMKV for Android 多进程设计与实现");
        list.add("实现类似微博内容，@用户，链接高亮 ExpandableTextView");

        indicator1 = new Indicator(this, 4, ll_1);
        indicator1.changeType(Indicator.TYPE_NUM);

        indicator2 = new Indicator(this, 4, ll_2);
        indicator2.changeType(Indicator.TYPE_TEXT, list);

        indicator3 = new Indicator(this, 4, ll_3);
        indicator3.setImgState(R.mipmap.homepage_banner_point02, R.mipmap.homepage_banner_point01);
        indicator3.updataView();

        indicator4 = new Indicator(this, 4, ll_4) {
            @Override
            public View createView(Context context, ViewGroup viewGroup) {
                return LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
            }

            @Override
            public void setCustomView(View view, String title, int position) {
                TextView tv_size = view.findViewById(R.id.tv_size);
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_size.setText((position + 1) + "/" + getIndicatorSize());
                tv_title.setText(title);
            }
        };
        indicator4.changeType(Indicator.TYPE_CUSTOM, list);

        indicator5 = new Indicator(this, 4, ll_5) {
            @Override
            public View getPointView(View view) {
                view.setBackgroundResource(R.drawable.select);
                return super.getPointView(view);
            }
        };
        indicator5.updataView();

        indicator6 = new Indicator(this, 4, ll_6) {
            @Override
            public View getPointView(View view) {
                view.setBackgroundResource(R.drawable.select);
                return super.getPointView(view);
            }
        };
        indicator6.reversalMandatory();
        indicator6.updataView();

        indicator7 = new Indicator(this, 4, ll_7);
        indicator7.setImgState(R.mipmap.homepage_banner_point02, R.mipmap.homepage_banner_point01);
        indicator7.reversalMandatory();
        indicator7.updataView();

        indicator8 = new Indicator(this, 4, ll_8);
        indicator8.setImgState(R.mipmap.homepage_banner_point02, R.mipmap.homepage_banner_point01);
        indicator8.reversalMandatory();
        indicator8.changeGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        indicators = new ArrayList<>();
        indicators.add(indicator1);
        indicators.add(indicator2);
        indicators.add(indicator3);
        indicators.add(indicator4);
        indicators.add(indicator5);
        indicators.add(indicator6);
        indicators.add(indicator7);
        indicators.add(indicator8);

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size++;
                if (size >= indicator1.getIndicatorSize()) {
                    size = 0;
                }

                for (int i = 0; i < indicators.size(); i++) {
                    Indicator indicator = indicators.get(i);
                    indicator.selectIndicator(size);
                }
            }
        });

        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicator8.changeBottomPadding(Integer.parseInt(etBottom.getText().toString()));
            }
        });

        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicator8.changeImageMargin(Integer.parseInt(etMargin.getText().toString()));
            }
        });
    }
}
