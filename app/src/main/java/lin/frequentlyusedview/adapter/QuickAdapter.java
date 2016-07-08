package lin.frequentlyusedview.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import lin.frequentlyusedview.R;
import lin.frequentlyusedview.entity.Status;

/**
 * Created by yo on 2016/7/6.
 */
public class QuickAdapter extends BaseQuickAdapter<Status> {

    public QuickAdapter(){
        super(R.layout.item,initData(100));
    }

    public QuickAdapter(int dataSize){
        super(R.layout.item,initData(dataSize));
    }

    public static List<Status> initData(int size) {
        List<Status> list = new ArrayList<>();
        for(int i = 0 ; i < size ;i++){
            Status status = new Status();
            status.setUserName("Card"+i);
            status.setText("BaseRecyclerViewAdapter content"+" i");
            list.add(status);
        }
        return list;
    }

    @Override
    protected void convert(BaseViewHolder helper, Status status) {
        helper.setText(R.id.item_tv_name,status.getUserName())
                .setText(R.id.item_tv_text,status.getText())
                .setOnClickListener(R.id.item_iv,new OnItemChildClickListener());
    }
}
