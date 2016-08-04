package lin.convenientbanner;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.*;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private ConvenientBanner mConvenientBanner;
    private List<BannerItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        try {
            mConvenientBanner.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {
                @Override
                public NetWorkImageHolderView createHolder() {
                    return new NetWorkImageHolderView();
                }
            }, mData)//设置需要切换的View
                    .setPointViewVisible(true)//设置指示器是否可见
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})//设置指示器圆点
                    .startTurning(3000)//设置翻页时间
                    .setPageTransformer(CubeOutTransformer.class.newInstance())//设置切换动画
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置圆点的位置
                    .setOnItemClickListener(this);
            mConvenientBanner.setScrollDuration(1500);
//            mConvenientBanner.stopTurning();// 设置之后上来就是停止的翻页状态，滑动之后恢复翻页，就无法再次停止
//            mConvenientBanner.setManualPageable(false);//设置手动影响(设置了该项无法手动切换);
            mConvenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void initData() {
        BannerItem item1 = new BannerItem("http://client.yeeaoobox.com//uploadimages//86591466843429.png");
        BannerItem item2 = new BannerItem("http://www.yeeaoo.com//images//faq_public_course_416.png");
        BannerItem item3 = new BannerItem("http://www.yeeaoo.com//images//faq_public_course_417.png");
        BannerItem item4 = new BannerItem("http://www.yeeaoo.com//images//faq_public_course_414.png");
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
        mData.add(item4);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MainActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * 各种动画效果
     transformerList.add(DefaultTransformer.class.getSimpleName());
     transformerList.add(AccordionTransformer.class.getSimpleName());
     transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
     transformerList.add(CubeInTransformer.class.getSimpleName());
     transformerList.add(CubeOutTransformer.class.getSimpleName());
     transformerList.add(DepthPageTransformer.class.getSimpleName());
     transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
     transformerList.add(FlipVerticalTransformer.class.getSimpleName());
     transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
     transformerList.add(RotateDownTransformer.class.getSimpleName());
     transformerList.add(RotateUpTransformer.class.getSimpleName());
     transformerList.add(StackTransformer.class.getSimpleName());
     transformerList.add(ZoomInTransformer.class.getSimpleName());
     transformerList.add(ZoomOutTranformer.class.getSimpleName());
     transformerArrayAdapter.notifyDataSetChanged();
     */

}
