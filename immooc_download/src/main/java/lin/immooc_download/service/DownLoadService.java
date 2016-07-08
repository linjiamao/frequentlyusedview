package lin.immooc_download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import lin.immooc_download.entity.FileInfo;

/**
 * Created by yo on 2016/7/6.
 */
public class DownLoadService extends Service{

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/downloads/";
    public static final int MSG_INIT = 0 ;
    //下载任务的集合
    public Map<Integer,DownLoadTask> mTasks = new LinkedHashMap<>();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得Activity传递过来的参数
        if(ACTION_START.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d("test","Start : "+fileInfo.toString());
            //启动初始化线程
//            new InitThread(fileInfo).start();
            DownLoadTask.executorService.execute(new InitThread(fileInfo));
        }else if(ACTION_PAUSE.equals(intent.getAction())){
            //暂停下载
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d("test","Pause : "+fileInfo.toString());
            //从集合中取出下载任务
            DownLoadTask task = mTasks.get(fileInfo.getId());
            if(task!=null){
                //停止下载任务
                task.isPause = true;
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.d("test","Init : "+fileInfo);
                    //启动下载任务
                    DownLoadTask task =
                            new DownLoadTask(DownLoadService.this,fileInfo,3);
                    task.download();
                    //把下载任务添加到集合中
                    mTasks.put(fileInfo.getId(),task);
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread{
        private FileInfo fileInfo = null;
        public InitThread(FileInfo fileInfo){
            this.fileInfo =fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(fileInfo.getUrl());
                connection= (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1 ;
                if(connection.getResponseCode()  == 200){
                    //获得文件的长度
                    length  = connection.getContentLength();
                }
                if(length<=0){
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                //让本地创建问价
                File file = new File(dir,fileInfo.getFileName());
                raf= new RandomAccessFile(file,"rwd");
                //设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                handler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    connection.disconnect();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
