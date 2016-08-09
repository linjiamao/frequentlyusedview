package lin.updateapk;

/**
 * Created by yo on 2016/8/8.
 */
public interface UpDateDownloadListener {
    void start();
    void onProgressChanged(int progress,String downloadUrl);
    void onFinished(long completeSize,String downloadUrl);
    void onFailure();
}
