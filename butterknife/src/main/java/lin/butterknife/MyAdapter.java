package lin.butterknife;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yo on 2016/8/5.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<String> list;
    public MyAdapter(Context context,List<String> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_adapter,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.item_tv)
        TextView tv_title;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
