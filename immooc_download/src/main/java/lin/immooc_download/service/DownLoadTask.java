package lin.immooc_download.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lin.immooc_download.db.ThreadDaoImpl;
import lin.immooc_download.entity.FileInfo;

import lin.immooc_download.entity.ThreadInfo;

/**
 * 下载任务类
 * Created by yo on 2016/7/7.
 */
public class DownLoadTask {
    private Context mContext = null;
    private FileInfo mFileInfo  = null;
    private ThreadDaoImpl mDao =null;
    private int mFinished = 0;
    public boolean isPause;
    private int mThreadCount = 1;//线程数量
    /**
     * 方便线程管理
     */
    private List<DownLoadThread> mThreadList = null;//线程集合
    public static ExecutorService executorService =
            Executors.newCachedThreadPool();//线程池
    public DownLoadTask(Context mContext,FileInfo mFileInfo,int mThreadCount){
        this.mContext = mContext;
        this.mFileInfo =mFileInfo;
        this.mThreadCount =mThreadCount;
        mDao = new ThreadDaoImpl(mContext);
    }
    public void download(){
        //读取数据库的线程信息
        List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
       if(threadInfos.size() ==0){
           //获得每个线程下载的长度
           int length = mFileInfo.getLength()/mThreadCount;
           for (int i =0;i<mThreadCount;i++){
               ThreadInfo threadInfo = new ThreadInfo(i,mFileInfo.getUrl(),length*i,(i+1)*length-1,0);
               if(i == mThreadCount-1){//防止最后一个除不尽
                   threadInfo.setEnd(mFileInfo.getLength());
               }
               //添加到线程信息集合
               threadInfos.add(threadInfo);
               //向数据库中插入线程信息
               mDao.insertThread(threadInfo);
           }
       }
        mThreadList = new ArrayList<>();
        //启动多个线程进行下载
        for (ThreadInfo info:threadInfos){
            DownLoadThread thread = new DownLoadThread(info);
//            thread.start();
            executorService.execute(thread);
            mThreadList.add(thread);
        }
    }

    /**
     * 判读是否所有线程都执行完
     */
    private synchronized void checkAllThreadsFinished(){
        boolean allFinished = true;
        //遍历线程集合，判读线程是否都执行完毕
        for (DownLoadThread thread:mThreadList){
            if(!thread.isFinished){
                allFinished =false;
                break;
            }
        }
        if(allFinished){
            //删除线程信息
            mDao.deleteThread(mFileInfo.getUrl());
            //发送广播通知UI下载任务完成
            Intent intent_finish = new Intent(DownLoadService.ACTION_FINISH);
            intent_finish.putExtra("fileInfo",mFileInfo);
            mContext.sendBroadcast(intent_finish);
        }
    }

    /**
     * 下载任务
     */
    class DownLoadThread extends Thread{
        public ThreadInfo mThreadInfo = null;
        /**
         * 标示线程是否下载完毕
         */
        public boolean isFinished =false;
        public DownLoadThread(ThreadInfo mThreadInfo){
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn= (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                conn.setRequestProperty("Range","bytes="+start+"-"+mThreadInfo.getEnd());//注意格式
                //设置文件的写入位置
                File file = new File(DownLoadService.DOWNLOAD_PATH,mFileInfo.getFileName());
                raf= new RandomAccessFile(file,"rwd");
                raf.seek(start);
                Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
                mFinished += mThreadInfo.getFinished();
                //开始下载
                int s= conn.getResponseCode();
                // 206 是断点续传返回的code 200是正常下载返回的
                if(conn.getResponseCode() == 206){
                    //读取数据
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024*4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len= inputStream.read(buffer))!=-1){
                        //写入文件
                        raf.write(buffer,0,len);
                        //把下载进度发送广播给Activity
                        //累加每个线程的完成进度
                        mThreadInfo.setFinished(mThreadInfo.getFinished()+len);
                        //累加全部的进度
                        mFinished += len;
                        if(System.currentTimeMillis()-time>500){
                            time = System.currentTimeMillis();
                            //防止超过int
                            Log.d("test","finished = "+(mFinished*1.0f*100)+"  length = "+ mFileInfo.getLength());
                            int progress = (int) (mFinished*1.0f*100/mFileInfo.getLength());
                            Log.d("test","finished = "+ progress);
                            intent.putExtra("finished",progress);
                            intent.putExtra("id",mFileInfo.getId());
                            mContext.sendBroadcast(intent);
                        }
                        //下载暂停时，保存下载进度
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(),mThreadInfo.getId(),mThreadInfo.getFinished());
                            return;
                        }

                    }
                    //标示线程下载完毕
                    isFinished = true;
                    //检查下载任务是否执行完毕
                    checkAllThreadsFinished();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect();
                    raf.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
