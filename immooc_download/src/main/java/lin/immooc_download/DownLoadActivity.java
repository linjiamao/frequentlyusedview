package lin.immooc_download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lin.immooc_download.entity.FileInfo;
import lin.immooc_download.service.DownLoadService;

/**
 * 数据下载 SerVice系列之断点续传
 */
public class DownLoadActivity extends AppCompatActivity {
    private ListView listView;
    private List<FileInfo> fileInfoList = null;
    private FileListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        getSupportActionBar().hide();
        initView();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
        fileInfoList = new ArrayList<>();
        //创建文件信息对象
        initData();
        listAdapter = new FileListAdapter(this,fileInfoList);
        listView.setAdapter(listAdapter);
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadService.ACTION_UPDATE);
        filter.addAction(DownLoadService.ACTION_FINISH);
        registerReceiver(mReceiver,filter);
    }

    private void initData() {
        FileInfo fileInfo = new FileInfo(0,"http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk","网易云音乐.apk",0,0);
        FileInfo fileInfo1 = new FileInfo(1,"http://dl.m.cc.youku.com/android/phone/Youku_Phone_youkuweb.apk","优酷.apk",0,0);
        FileInfo fileInfo2 = new FileInfo(2,"http://dldir1.qq.com/qqmi/TencentVideo_V4.1.0.8897_51.apk","腾讯视频.apk",0,0);
        FileInfo fileInfo3 = new FileInfo(3,"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V10.6.0.620_android_pf145_(Build150721222435).apk","UC浏览器.apk",0,0);
        FileInfo fileInfo4 = new FileInfo(4,"http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk","360安全卫士.apk",0,0);
        FileInfo fileInfo5 = new FileInfo(5,"http://www.51job.com/client/51job_51JOB_1_AND2.9.3.apk","51.job.apk",0,0);
        FileInfo fileInfo6 = new FileInfo(6,"http://upgrade.m.tv.sohu.com/channels/hdv/5.0.0/SohuTV_5.0.0_47_201506112011.apk","搜狐视频.apk",0,0);
        FileInfo fileInfo7 = new FileInfo(7,"http://dldir1.qq.com/qqcontacts/100001_phonebook_4.0.0_3148.apk","微信电话本.apk",0,0);
        FileInfo fileInfo8 = new FileInfo(8,"http://download.alicdn.com/wireless/taobao4android/latest/702757.apk","淘宝.apk",0,0);
        fileInfoList.add(fileInfo);
        fileInfoList.add(fileInfo1);
        fileInfoList.add(fileInfo2);
        fileInfoList.add(fileInfo3);
        fileInfoList.add(fileInfo4);
        fileInfoList.add(fileInfo5);
        fileInfoList.add(fileInfo6);
        fileInfoList.add(fileInfo7);
        fileInfoList.add(fileInfo8);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    //更新UI的广播接收器
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownLoadService.ACTION_UPDATE)){
                int finished = intent.getIntExtra("finished",0);
                int id = intent.getIntExtra("id",0);
                listAdapter.updateProgress(id,finished);
            }else if(intent.getAction().equals(DownLoadService.ACTION_FINISH)){
                //下载结束
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                //更新进度为 0
                listAdapter.updateProgress(fileInfo.getId(),0);
                Toast.makeText(DownLoadActivity.this, fileInfoList.get(fileInfo.getId()).getFileName()+"下载完毕", Toast.LENGTH_SHORT).show();
            }

        }
    };
}
