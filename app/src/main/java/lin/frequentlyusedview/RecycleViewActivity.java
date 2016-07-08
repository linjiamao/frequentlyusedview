package lin.frequentlyusedview;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import lin.frequentlyusedview.adapter.QuickAdapter;

/**
 * listView的升级版 RecycleView 有下拉刷新 和加载更多
 */
public class RecycleViewActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private QuickAdapter quickAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int PAGE_SIZE = 6;
    private static final int TOTAL_COUNTER = 18;
    private int currentCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        initAdapter();
    }

    private void initAdapter() {
        quickAdapter = new QuickAdapter(PAGE_SIZE);
        //添加不同的item的进入动画
//        quickAdapter.openLoadAnimation();
        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerView.setAdapter(quickAdapter);
        currentCounter = quickAdapter.getData().size();
        quickAdapter.setOnLoadMoreListener(this);
        quickAdapter.openLoadMore(PAGE_SIZE,true);
        addHeadView();
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Toast.makeText(RecycleViewActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();
            }
        });
        quickAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Toast.makeText(RecycleViewActivity.this, "点击的是第"+(i+1)+"个图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.headview, (ViewGroup) recyclerView.getParent(),false);
        ((TextView)headView.findViewById(R.id.tv)).setText("click use custom view");
        final View customLoading  = getLayoutInflater().inflate(R.layout.custom_loading,(ViewGroup) recyclerView.getParent(),false);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAdapter.setLoadingView(customLoading);
                recyclerView.setAdapter(quickAdapter);
                Toast.makeText(RecycleViewActivity.this, "use ok", Toast.LENGTH_SHORT).show();
            }
        });
        quickAdapter.addHeaderView(headView);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                quickAdapter.setNewData(quickAdapter.initData(PAGE_SIZE));
                quickAdapter.openLoadMore(PAGE_SIZE,true);
                currentCounter = PAGE_SIZE;
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);

    }

    @Override
    public void onLoadMoreRequested() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if(currentCounter>= TOTAL_COUNTER){
                    quickAdapter.notifyDataChangedAfterLoadMore(false);
                    View view = getLayoutInflater().inflate(R.layout.not_loading,(ViewGroup) recyclerView.getParent(),false);
                    quickAdapter.addFooterView(view);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            quickAdapter.notifyDataChangedAfterLoadMore(quickAdapter.initData(PAGE_SIZE),true);
                            currentCounter = quickAdapter.getData().size();
                        }
                    }, 1000);
                }
            }
        });
    }
}
