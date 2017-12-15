package io.github.lavenderming.savingdatausingsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.github.lavenderming.savingdatausingsqlite.FeedReaderContract.FeedEntry;

public class MainActivity extends AppCompatActivity {
    private static final String DUMMY_DATA_TITLE = "Title";
    private static final String DUMMY_DATA_SUBTITLE = "Subtitle";
    private static final String DUMMY_DATA_NEW_TITLE = "NewTitle";

    FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new FeedReaderDbHelper(MainActivity.this);

        // 创建数据库
        findViewById(R.id.create_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.getWritableDatabase();
                updateTextView();
            }
        });

        // 插入数据
        findViewById(R.id.insert_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDummyData();
                updateTextView();
            }
        });

        // 查询数据
        findViewById(R.id.query_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询当前数据库中的所有内容并在 TextView 上更新
                updateTextView();
            }
        });

        // 删除数据
        findViewById(R.id.delete_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllData();
                updateTextView();
            }
        });

        // 更新数据
        findViewById(R.id.update_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                updateTextView();
            }
        });
    }

    private void updateData() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 需要更新的列的新值
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, DUMMY_DATA_NEW_TITLE);

        // 指明需要修改哪行的语句
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { DUMMY_DATA_TITLE };

        int count = db.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    private void deleteAllData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        为删除表中所有行，暂无需 where 子句
//        // 定义 'where' 部分
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
//        // 按照占位符顺序指定对应的值
//        String[] selectionArgs = { DUMMY_DATA_TITLE };
        // 执行 SQL 语句
        db.delete(FeedEntry.TABLE_NAME, null, null);
    }

    private Cursor queryToGetAllData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 指定从数据库返回的列
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_SUBTITLE
        };

        // WHERE 子句 "title" = 'My Title'
//        为获取所有数据，暂不需要 where 子句
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = {DUMMY_DATA_TITLE};

        // 返回数据排列方式
        String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        return db.query(
                FeedEntry.TABLE_NAME,                     // 要查询的表
                projection,                               // 要返回的列
                null,                                // WHERE 子句的列
                null,                            // WHERE 子句的对应值
                null,                                     // 不设定 group
                null,                                     // 不筛选 group
                sortOrder                                 // 排序
        );
    }

    private void insertDummyData() {
        // 获取写模式数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 创建要插入的键值对，列名为键
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, DUMMY_DATA_TITLE);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, DUMMY_DATA_SUBTITLE);

        // 插入新行，返回新行主键值
        db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    private void updateTextView() {
        updateTextView(queryToGetAllData());
    }

    private void updateTextView(Cursor cursor) {
        StringBuilder stringBuilder = new StringBuilder();
        int idColumnIndex = cursor.getColumnIndexOrThrow(FeedEntry._ID);
        int titleColumnIndex = cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE);
        int subtitleColumnIndex = cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUBTITLE);

        while (cursor.moveToNext()) {
            stringBuilder
                    .append(cursor.getString(idColumnIndex)).append(" ")
                    .append(cursor.getString(titleColumnIndex)).append(" ")
                    .append(cursor.getString(subtitleColumnIndex)).append("\n");
        }

        String result = stringBuilder.toString();
        if (result.isEmpty()) {
            result = "数据库当前没有数据。";
        }
        ((TextView)findViewById(R.id.show_data)).setText(result);
    }
}
