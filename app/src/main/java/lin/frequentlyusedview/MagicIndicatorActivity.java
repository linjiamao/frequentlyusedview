package lin.frequentlyusedview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.UIUtil;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.AbsorbPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 指示器 目前还可以使用tabLayout
 */
public class MagicIndicatorActivity extends BaseActivity {
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private String[] channels = new String[]{"科技", "汽车", "互联网", "hackware.lucode.net", "奇闻异事", "搞笑", "段子", "趣图", "健康", "时尚", "教育", "星座", "育儿", "游戏", "家居", "美食", "旅游", "历史", "情感"};
    private List<String> mDataList = new ArrayList<>();
    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textview =new TextView(container.getContext());
            textview.setText(mDataList.get(position));
            textview.setGravity(Gravity.CENTER);
            container.addView(textview);
            return textview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    };
    {
        for (int i = 0 ; i <channels.length;i++){
            mDataList.add(channels[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_indicator);
        initView();
    }

    private void initView() {
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(mAdapter);

        // 今日头条式
//        NavigatorAdapter indicatorAdapter = new NavigatorAdapter();

        // 当前页不定位到中间
//        NavigatorAdapter1 indicatorAdapter = new NavigatorAdapter1();

        // 当前页始终定位到中间
//        NavigatorAdapter2 indicatorAdapter = new NavigatorAdapter2();
//        commonNavigator.setEnablePivotScroll(true);
//        commonNavigator.setScrollPivotX(0.15f);

        // 自适应模式
//        NavigatorAdapter3 indicatorAdapter = new NavigatorAdapter3();
//        commonNavigator.setAdjustMode(true);

        //自适用带插值器
//        NavigatorAdapter4 indicatorAdapter = new NavigatorAdapter4();
//        commonNavigator.setAdjustMode(true);

        // 缩放 + 颜色渐变
//        NavigatorAdapter5 indicatorAdapter = new NavigatorAdapter5();
//        commonNavigator.setEnablePivotScroll(true);
//        commonNavigator.setScrollPivotX(0.8f);

        // 只有指示器，没有title
//        NavigatorAdapter6 indicatorAdapter = new NavigatorAdapter6();
//        commonNavigator.setAdjustMode(true);

        // 带吸附效果
//        NavigatorAdapter7 indicatorAdapter = new NavigatorAdapter7();
//        commonNavigator.setEnablePivotScroll(true);

        // 贝塞尔曲线
//        NavigatorAdapter8 indicatorAdapter = new NavigatorAdapter8();
//        commonNavigator.setEnablePivotScroll(true);

        // 天天快报式
//        NavigatorAdapter9 indicatorAdapter = new NavigatorAdapter9();
//        commonNavigator.setEnablePivotScroll(true);

        // 小尖角式
//        NavigatorAdapter10 indicatorAdapter = new NavigatorAdapter10();
//        commonNavigator.setScrollPivotX(0.15f);

        // 圆圈式
//        CircleNavigator commonNavigator = new CircleNavigator(this);
//        commonNavigator.setCircleCount(mDataList.size());
//        commonNavigator.setCircleColor(Color.RED);
//        commonNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
//            @Override
//            public void onClick(int index) {
//                viewPager.setCurrentItem(index);
//            }
//        });

        //通用的
        NavigatorAdapter11 indicatorAdapter = new NavigatorAdapter11();
        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setFollowTouch(false);
        commonNavigator.setAdapter(indicatorAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
        viewPager.setCurrentItem(1);
        magicIndicator.setNavigator(commonNavigator);
    }


    /**
     *  今日头条式
     */
    class NavigatorAdapter extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ClipPagerTitleView textview = new ClipPagerTitleView(context);
            textview.setText(mDataList.get(index));
            textview.setTextColor(Color.parseColor("#f2c4c4"));
            textview.setClipColor(Color.WHITE);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return textview;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            return null; // 没有指示器，因为title的指示作用已经很明显了
        }
    }

    /*
     *
     * 当前页不定位到中间
     */
    class NavigatorAdapter1 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ColorTransitionPagerTitleView textview = new ColorTransitionPagerTitleView(context);
            textview.setText(mDataList.get(index));
            textview.setNormalColor(Color.GRAY);
            textview.setSelectedColor(Color.BLACK);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return textview;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setWrapContentMode(true);
            List<String> colorList = new ArrayList<String>();
            colorList.add("#ff4a42");
            colorList.add("#fcde64");
            colorList.add("#73e8f4");
            colorList.add("#76b0ff");
            colorList.add("#c683fe");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /*
    *
    * 当前页始终定位到中间
    */
    class NavigatorAdapter2 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ColorTransitionPagerTitleView textview = new ColorTransitionPagerTitleView(context);
            textview.setText(mDataList.get(index));
            textview.setNormalColor(Color.GRAY);
            textview.setSelectedColor(Color.BLACK);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return textview;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            List<String> colorList = new ArrayList<String>();
            colorList.add("#ff4a42");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /*
    *
    * 自适应模式
    */
    class NavigatorAdapter3 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?3:0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ColorTransitionPagerTitleView textview = new ColorTransitionPagerTitleView(context);
            textview.setText(mDataList.get(index));
            textview.setNormalColor(Color.GRAY);
            textview.setSelectedColor(Color.BLACK);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return textview;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setLineHeight(UIUtil.dip2px(context, 6));
            indicator.setRoundRadius(UIUtil.dip2px(context, 3));
            List<String> colorList = new ArrayList<String>();
            colorList.add("#fcde64");
           indicator.setColorList(colorList);
            return indicator;
        }
    }
    /*
    *
    * 自适应模式，带插值器
    */
    class NavigatorAdapter4 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?3:0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ColorTransitionPagerTitleView textview = new ColorTransitionPagerTitleView(context);
            textview.setText(mDataList.get(index));
            textview.setNormalColor(Color.GRAY);
            textview.setSelectedColor(Color.BLACK);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return textview;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setStartInterpolator(new AccelerateInterpolator());
            indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
            indicator.setLineHeight(UIUtil.dip2px(context, 1));
            List<String> colorList = new ArrayList<String>();
            colorList.add("#76b0ff");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /*
    *
    * 缩放 + 颜色渐变
    */
    class NavigatorAdapter5 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setText(mDataList.get(index));
            colorTransitionPagerTitleView.setTextSize(18);
            colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
            colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setStartInterpolator(new AccelerateInterpolator());
            indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
            indicator.setLineHeight(UIUtil.dip2px(context, 1));
            List<String> colorList = new ArrayList<String>();
            colorList.add("#c683fe");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /**
     *  只有指示器，没有title
     */
    class NavigatorAdapter6 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?5:0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            return new DummyPagerTitleView(context);
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setLineHeight(UIUtil.dip2px(context, 5));
            List<String> colorList = new ArrayList<String>();
            colorList.add("#76b0ff");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /**
     *  带吸附效果
     */
    class NavigatorAdapter7 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(mDataList.get(index));
            simplePagerTitleView.setNormalColor(Color.GRAY);
            simplePagerTitleView.setSelectedColor(Color.BLACK);
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return simplePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            AbsorbPagerIndicator indicator = new AbsorbPagerIndicator(context);
            indicator.setLineHeight(UIUtil.dip2px(context, 6));
            List<String> colorList = new ArrayList<String>();
            colorList.add("#ff4a42");
            colorList.add("#fcde64");
            colorList.add("#73e8f4");
            colorList.add("#76b0ff");
            colorList.add("#c683fe");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /**
     *  贝塞尔曲线
     */
    class NavigatorAdapter8 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setText(mDataList.get(index));
            colorTransitionPagerTitleView.setTextSize(18);
            colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
            colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            BezierPagerIndicator indicator = new BezierPagerIndicator(context);
            List<String> colorList = new ArrayList<String>();
            colorList.add("#ff4a42");
            colorList.add("#fcde64");
            colorList.add("#73e8f4");
            colorList.add("#76b0ff");
            colorList.add("#c683fe");
            indicator.setColorList(colorList);
            return indicator;
        }
    }

    /**
     * 天天快报式
      */
    class NavigatorAdapter9 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(mDataList.get(index));
            simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
            simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return simplePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            WrapPagerIndicator indicator = new WrapPagerIndicator(context);
            indicator.setFillColor(Color.parseColor("#ebe4e3"));
            return indicator;
        }
    }

    /**
     * 小尖角式
     */
    class NavigatorAdapter10 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(mDataList.get(index));
            simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
            simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            return simplePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
            indicator.setLineColor(Color.parseColor("#cc22dd"));
            return indicator;
        }
    }

    /**
     * 通用的
     */
    class NavigatorAdapter11 extends CommonNavigatorAdapter{

        @Override
        public int getCount() {
            return mDataList!=null?mDataList.size():0;
        }

        @Override
        public IPagerTitleView getItemView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(MagicIndicatorActivity.this);
            commonPagerTitleView.setContentView(R.layout.simple_pager_title_layout);

            // 初始化
            final ImageView titleImg = (ImageView) commonPagerTitleView.findViewById(R.id.title_img);
            titleImg.setImageResource(R.mipmap.ic_launcher);
            final TextView titleText = (TextView) commonPagerTitleView.findViewById(R.id.title_text);
            titleText.setText(mDataList.get(index));

            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                @Override
                public void onSelected(int index) {
                    titleText.setTextColor(Color.RED);
                }

                @Override
                public void onDeselected(int index) {
                    titleText.setTextColor(Color.BLACK);
                }

                @Override
                public void onLeave(int index, float leavePercent, boolean leftToRight) {
                    titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                    titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                }

                @Override
                public void onEnter(int index, float enterPercent, boolean leftToRight) {
                    titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                    titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                }
            });

            commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });

            return commonPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }
    }
    /**
     * 更新数据
     *
     mDataList.clear();
     mDataList.add("欢迎关注");
     mDataList.add("我的博客");
     mDataList.add("hackware.lucode.net");
     commonNavigator.notifyDataSetChanged();
     mAdapter.notifyDataSetChanged();
     *
     */


}
