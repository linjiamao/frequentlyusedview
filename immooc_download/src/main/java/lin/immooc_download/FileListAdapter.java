package lin.immooc_download;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import lin.immooc_download.entity.FileInfo;
import lin.immooc_download.service.DownLoadService;

/**
 * Created by yo on 2016/7/7.
 */
public class FileListAdapter extends BaseAdapter{
    private Context context;
    private List<FileInfo> fileInfoList = null;
    public FileListAdapter(Context context,List<FileInfo> list){
        this.context=context;
        fileInfoList =list;
    }
    @Override
    public int getCount() {
        return fileInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView ==null){
            convertView = View.inflate(context,R.layout.listitem,null);
            holder = new ViewHolder();
            holder.tvFile = (TextView) convertView.findViewById(R.id.tvFileName);
            holder.btPause = (Button) convertView.findViewById(R.id.btPause);
            holder.btStart = (Button) convertView.findViewById(R.id.btStart);
            holder.pbFile = (ProgressBar) convertView.findViewById(R.id.pbProgress);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final FileInfo fileInfo = fileInfoList.get(position);
        holder.tvFile.setText(fileInfo.getFileName());
        holder.btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_START);
                intent.putExtra("fileInfo",fileInfo);
                context.startService(intent);
            }
        });
        holder.btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_PAUSE);
                intent.putExtra("fileInfo",fileInfo);
                context.startService(intent);
            }
        });
        holder.pbFile.setProgress(fileInfo.getFinished());
        return convertView;
    }

    /**
     * 更新列表项中的进度条
     */
    public void updateProgress(int id,int progress){
        FileInfo fileInfo = fileInfoList.get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }
    class ViewHolder {
        TextView tvFile;
        Button btPause;
        Button btStart;
        ProgressBar pbFile;
    }
}
