package lin.updateapk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.File;

/**
 * Created by yo on 2016/8/8.
 * app更新下载后天服务
 */
public class UpdateService extends Service{
    private String apkUrl;
    private String filePath;
    private NotificationManager notificationManager;
    private Notification notification;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        filePath = Environment.getExternalStorageDirectory()+"/imooc.lin.apk";

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            notifyUser(getString(R.string.update_download_failed),
                    getString(R.string.update_download_failed_msg),0);
            stopSelf();
        }
        apkUrl = intent.getStringExtra("apkUrl");
        notifyUser(getString(R.string.update_download_start),
                getString(R.string.update_download_start),0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        UpdateManager.getInstance().startDownloads(apkUrl, filePath, new UpDateDownloadListener() {
            @Override
            public void start() {

            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                Log.d("service", "onProgressChanged: "+progress);
                notifyUser(getString(R.string.update_download_progressing),
                        getString(R.string.update_download_progressing),progress);
            }

            @Override
            public void onFinished(long completeSize, String downloadUrl) {
                Log.d("service", "onFinished");
                notifyUser(getString(R.string.update_download_finish),
                        getString(R.string.update_download_finish),100);
                stopSelf();

            }

            @Override
            public void onFailure() {
                Log.d("service", "onFailure");
                notifyUser(getString(R.string.update_download_failed),
                        getString(R.string.update_download_failed_msg),0);
                stopSelf();
            }
        });
    }

    //更新我们的notification来告诉我们当前用户的下载进度
    private void notifyUser(String result, String reason,int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name));
        if(progress>0 && progress<100){
            builder.setProgress(100,progress,false);
            builder.setContentText(progress+"%");
        }else if(progress>=100){
            builder.setProgress(0,0,false);
            builder.setContentText("下载完成");
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(result);
//        可以实现点击通知栏安装程序
//        builder.setContentIntent(progress>=100?getContentIntent():
//        PendingIntent.getActivity(this,0,new Intent(),
//                PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.build();
        notificationManager.notify(0,notification);
        if(progress>=100){
            startInstallApk();
        }
    }


    private void startInstallApk() {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+apkFile.getAbsolutePath()),
               "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private PendingIntent getContentIntent() {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+apkFile.getAbsolutePath()),
               "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
