package lin.immooc_download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yo on 2016/7/7.
 * 数据库帮助类
 */
public class DBHelper  extends SQLiteOpenHelper{
    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String SQL_CRETE = "create table thread_info(id integer primary key autoincrement ," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String SQL_DROP ="drop table if exists thread_info";
    private static DBHelper dbHelper = null;

    public static DBHelper getInstance(Context context){
        if(dbHelper ==null){
            synchronized (DBHelper.class){
                if(dbHelper == null){
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    private DBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CRETE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CRETE);
    }
}
