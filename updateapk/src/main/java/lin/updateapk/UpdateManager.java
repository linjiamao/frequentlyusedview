package lin.updateapk;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yo on 2016/8/8.
 * 下载调度管理器，调用我们的UpdateDownloadRequest
 */
public class UpdateManager {
    private static UpdateManager manager;
    private ThreadPoolExecutor threadPoolExecutor;
    private UpdateDownloadRequest request;
    private UpdateManager(){
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
    public static UpdateManager getInstance(){
        if(manager == null){
            manager = new UpdateManager();
        }
        return manager;
    }
    public void startDownloads(String downloadUrl,String localPath,
                               UpDateDownloadListener listener){
        if(request != null){
            return;
        }
        checkLocalFilePath(localPath);

        //开始真正的去下载任务
        request = new UpdateDownloadRequest(downloadUrl,localPath,listener);
        Future<?> future = threadPoolExecutor.submit(request);
    }

    //用来检查文件路劲是否已经存在
    private void checkLocalFilePath(String localPath) {
        File dir = new File(localPath.substring(0,localPath.lastIndexOf("/")+1));
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(localPath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
