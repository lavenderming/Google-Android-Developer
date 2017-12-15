package io.github.lavenderming.savingdatausingsqlite;

import android.provider.BaseColumns;

/**
 * Created by 阿懂 on 2017/12/14.
 *
 * Contract 是一个定义数据库表、列等的名字的类
 */

public class FeedReaderContract {
    // 为防止该类被实例化，将构造函数设为私有
    private FeedReaderContract() {}

    /* 定义数据库内某个表表名、列名的内部类 */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
