package lin.immooc_download.db;

import java.util.List;

import lin.immooc_download.entity.ThreadInfo;

/**
 * Created by yo on 2016/7/7.
 * 数据访问接口
 */
public interface ThreadDao {
    /**
     * 插入线程信息
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url);

    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     */
    public void updateThread(String url,int thread_id,int finished);

    /**
     * 查询文件的线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 查询线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url,int thread_id);
}
