package lin.updateapk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by yo on 2016/8/8.
 * 真正负责处理文件的下载和线程间的通信
 */
public class UpdateDownloadRequest implements Runnable{
    private String downloadUrl;
    private String localFilePath;
    private UpDateDownloadListener downloadListener;
    private boolean isDownloading =false;
    private long currentLength;

    private DownloadResponseHandler downloadRequestHandler;
    public UpdateDownloadRequest(String downloadUrl,String localFilePath,UpDateDownloadListener downloadListener){
        this.downloadListener = downloadListener;
        this.downloadUrl = downloadUrl;
        this.localFilePath = localFilePath;
        this.isDownloading = true;
        this.downloadRequestHandler = new DownloadResponseHandler();

    }

    //真正的去建立连接的方法
    private void makeRequest()throws IOException,InterruptedException{
        if(!Thread.currentThread().isInterrupted()){
            try {
                Log.d("request", "makeRequest: "+"开始建立连接");
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("Connection","Keep-Alive");
                connection.connect();//阻塞当前的线程
                currentLength = connection.getContentLength();
                Log.d("request", "currentLength: "+currentLength);
                if(!Thread.currentThread().isInterrupted()){
                    //真正的完成文件的下载
                    downloadRequestHandler.sendResponseMessage(connection.getInputStream());
                }

            }catch (IOException e){
                throw e;
            }
        }
    }
    @Override
    public void run() {
        try {
            makeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化数字
     * @param value
     * @return
     */
    private String getTwoPointFloatStr(float value){
        DecimalFormat fnum = new DecimalFormat("0.00");
        return fnum.format(value);
    }

    /**
     * 包含了下载过程中所有可能出现的异常
     */
    public enum FailureCode{
        UnknownHost,Socket,SocketTimeout,ConnectTimeout,
        IO,HttpResponse,JSON,Interrupted
    }

    /**
     * 用来真正的去下载文件，并发送消息和回调的接口
     */
    public class DownloadResponseHandler{
        protected static final int SUCCESS_MESSAGE = 0;
        protected static final int FAILURE_MESSAGE = 1;
        protected static final int START_MESSAGE = 2 ;
        protected static final int FINISH_MESSAGE = 3;
        protected static final int NETWORK_OFF = 4;
        private static final int PROGRESS_CHANGED = 5 ;

        private long mCompleteSize = 0;
        private int progress = 0;

        private Handler handler; // 真正完成线程间通讯

        public DownloadResponseHandler(){
            handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    handleSelfMessage(msg);
                }
            };
        }

        /**
         * 用来发送不同的消息
         */
        protected void sendFinishMessage(){
            sendMessage(obtainMessage(FINISH_MESSAGE,null));
        }
        private void sendProgressChangeMessage(int progress){
            sendMessage(obtainMessage(PROGRESS_CHANGED,new Object[]{progress}));
        }
        protected void sendFailureMessage(FailureCode failureCode){
            sendMessage(obtainMessage(FAILURE_MESSAGE,new Object[]{failureCode}));
        }

        protected void sendMessage(Message msg){
            if(handler != null){
                handler.sendMessage(msg);
            }else{
                handleSelfMessage(msg);
            }
        }

        /**
         * 获取一个消息对象
         * @param responseMessage
         * @param response
         * @return
         */
        protected Message obtainMessage(int responseMessage,Object response){
            Message msg = null;
            if(handler != null){
                msg = handler.obtainMessage(responseMessage,response);
            }else{
                msg = Message.obtain();
                msg.what = responseMessage;
                msg.obj = response;
            }
            return msg;
        }

        protected void handleFailureMessage(FailureCode failureCode){
            onFailure(failureCode);
        }

        /**
         * 各种消息的处理逻辑
         */
        protected void handleProgressChangeMessage(int progress){
            downloadListener.onProgressChanged(progress,"");
        }
        //外部接口的回调
        public void onFinish(){
            downloadListener.onFinished(mCompleteSize,"");
        }

        public void onFailure(FailureCode failureCode){
            downloadListener.onFailure();
        }

        //文件下载方法 ，会发送各种类型的事件
        void sendResponseMessage(InputStream is){
            Log.d("request", "makeRequest: "+"开始下载");
            RandomAccessFile raf = null;
            mCompleteSize = 0;
            byte[] buffer = new byte[1024];
            int length = -1;
            int limit = 0 ;
            try {
                raf = new RandomAccessFile(localFilePath,"rwd");
                while ((length = is.read(buffer)) != -1){
                    if(isDownloading){
                        raf.write(buffer,0,length);
                        mCompleteSize+= length;
                        if(mCompleteSize < currentLength){
                            progress =(int) (Float.parseFloat(getTwoPointFloatStr(mCompleteSize))/currentLength*100);
                            if(limit%30 ==0 && progress<=100){
                                //为了限制一下我们Notification的更新频率
                                Log.d(".........0", "sendResponseMessage: "+progress);
                                sendProgressChangeMessage(progress);
                            }
                            limit++;
                        }
                    }
                }
                sendFinishMessage();
            } catch (IOException e) {
                sendFailureMessage(FailureCode.IO);
            }finally {
                if(is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        sendFailureMessage(FailureCode.IO);
                    }
                }
                if(raf!=null){
                    try {
                        raf.close();
                    } catch (IOException e) {
                        sendFailureMessage(FailureCode.IO);
                    }
                }
            }
        }

        private void handleSelfMessage(Message msg) {
            Object[] response;
            switch (msg.what){
                case FAILURE_MESSAGE:
                    response = (Object[]) msg.obj;
                    handleFailureMessage((FailureCode) response[0]);
                    break;
                case PROGRESS_CHANGED:
                    response = (Object[]) msg.obj;
                    int progress = (int) response[0];
                    Log.d(".......", "handleSelfMessage: "+progress);
                    handleProgressChangeMessage(progress);
                    break;
                case FINISH_MESSAGE:
                    onFinish();
                    break;
            }
        }

    }
}
