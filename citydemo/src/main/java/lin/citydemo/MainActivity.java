package lin.citydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 城市导航(按字母导航)
 *
 * 存在问题  按Home键后，再次返回该界面，字母索引不见了。再次点击一下才会显示
 */
public class MainActivity extends AppCompatActivity implements MySlideView.onTouchListener,CityAdapter.OnItemClickListener{
    private Set<String> firstPinyin = new LinkedHashSet<>();
    public static List<String> pinyinList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private CircleTextView mCircleTextView;
    private RecyclerView mRecyclerView;
    private MySlideView mMySlideView;
    private PinyinComparator mPinyinComparator;
    private LinearLayoutManager mLayoutManager;
    private CityAdapter mAdapter;
    private TextView mTvStickyHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        firstPinyin.clear();
        pinyinList.clear();
        cityList.clear();
        mCircleTextView = (CircleTextView) findViewById(R.id.my_circle_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mMySlideView = (MySlideView) findViewById(R.id.my_slide_view);
        mTvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        mPinyinComparator = new PinyinComparator();
        for (int i = 0 ; i< City.stringCitys.length;i++){
            City city = new City();
            city.setCityName(City.stringCitys[i]);
            city.setCityPinyin(transformPinYin(City.stringCitys[i]));
            cityList.add(city);
        }

        //根据字母顺序重新排序
        Collections.sort(cityList,mPinyinComparator);
        //初始化右侧导航条的数据
        for (City city:cityList){
            firstPinyin.add(city.getFirstPinYin());
        }
        for(String string:firstPinyin){
            pinyinList.add(string);
        }
        mMySlideView.setListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CityAdapter(this,cityList);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //.第一次调用RecyclerView的findChildViewUnder()方法，返回指定位置的childView，这里也就是item的头部布局
                View stickyInfoView = recyclerView.findChildViewUnder(
                        mTvStickyHeaderView.getMeasuredWidth()/2,5 );
                if(stickyInfoView!=null&&stickyInfoView.getContentDescription()!=null){
                    mTvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }
                //第二次调用RecyclerView的findChildViewUnder()方法，这里返回的是固定在屏幕上方那个tvStickyHeaderView下面一个像素位置的RecyclerView的item，
                // 根据这个item来更新tvStickyHeaderView要translate多少距离
                View transInfoView = recyclerView.findChildViewUnder(
                        mTvStickyHeaderView.getMeasuredWidth()/2,mTvStickyHeaderView.getMeasuredHeight()+1
                );
                if(transInfoView!=null&&transInfoView.getTag()!=null){
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop()-mTvStickyHeaderView.getMeasuredHeight();
                    if(transViewStatus == CityAdapter.HAS_STICKY_VIEW){
                        if(transInfoView.getTop()>0){
                            mTvStickyHeaderView.setTranslationY(dealtY);
                        }else{
                            mTvStickyHeaderView.setTranslationY(0);
                        }
                    }else if(transViewStatus == CityAdapter.NONE_STICKY_VIEW){
                        mTvStickyHeaderView.setTranslationY(0);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    /**
     *
     * 将汉字转为拼音
     * @param stringCity
     * @return
     */
    private String transformPinYin(String stringCity) {
        StringBuffer buffer = new StringBuffer();
        for (int i =0;i<stringCity.length();i++){
            buffer.append(Pinyin.toPinyin(stringCity.charAt(i)));
        }
        return buffer.toString();
    }

    @Override
    public void showTextView(String textView, boolean dismiss) {
        if(dismiss){
            mCircleTextView.setVisibility(View.GONE);
        }else{
            mCircleTextView.setVisibility(View.VISIBLE);
            mCircleTextView.setText(textView);
        }
        int selectPosition = 0;
        for(int i = 0 ;i<cityList.size();i++){
            if(cityList.get(i).getFirstPinYin().equals(textView)){
                selectPosition = i ;
                break;
            }
        }
        mLayoutManager.scrollToPositionWithOffset(selectPosition,0);
    }

    @Override
    public void itemClick(int position) {
        Toast.makeText(MainActivity.this, "你选择了:"+cityList.get(position).getCityName(), Toast.LENGTH_SHORT).show();
    }

    public class PinyinComparator implements Comparator<City>{

        @Override
        public int compare(City cityFirst, City citySecond) {
            //返回参与比较的前后两个字符串的asc码的差值
            return cityFirst.getCityPinyin().compareTo(citySecond.getCityPinyin());
        }
    }

}
