参考：[Saving Data Using SQLite](https://developer.android.google.cn/training/data-storage/sqlite.html)

- [定义 Schema 和 Contract](#%E5%AE%9A%E4%B9%89-schema-%E5%92%8C-contract)
- [用一个 SQL Helper 创建数据库](#%E7%94%A8%E4%B8%80%E4%B8%AA-sql-helper-%E5%88%9B%E5%BB%BA%E6%95%B0%E6%8D%AE%E5%BA%93)
- [向数据库中插入数据](#%E5%90%91%E6%95%B0%E6%8D%AE%E5%BA%93%E4%B8%AD%E6%8F%92%E5%85%A5%E6%95%B0%E6%8D%AE)
- [从数据库中读取数据](#%E4%BB%8E%E6%95%B0%E6%8D%AE%E5%BA%93%E4%B8%AD%E8%AF%BB%E5%8F%96%E6%95%B0%E6%8D%AE)
- [从数据库中删除信息](#%E4%BB%8E%E6%95%B0%E6%8D%AE%E5%BA%93%E4%B8%AD%E5%88%A0%E9%99%A4%E4%BF%A1%E6%81%AF)
- [更新数据库中的信息](#%E6%9B%B4%E6%96%B0%E6%95%B0%E6%8D%AE%E5%BA%93%E4%B8%AD%E7%9A%84%E4%BF%A1%E6%81%AF)
- [维持持久的数据库连接](#%E7%BB%B4%E6%8C%81%E6%8C%81%E4%B9%85%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5)

在 Android 中使用数据库的 API 位于 [android.database.sqlite](https://developer.android.google.cn/reference/android/database/sqlite/package-summary.html) 包中。

> 注意：尽管这些 API 很强大，但是它们非常底层，需要大量时间及精力来使用，比如：
> - 没有编译时对原始 SQL 查询语句的检查。当数据图（data graph）发生改变时，需要手动更新受影响的查询语句。
> - 要使用许多样板代码来进行 SQL 查询和数据对象间的转换
> 
> 因此，Google 强烈建议使用 [Room Persistence Library](https://developer.android.google.cn/training/basics/data-storage/room/index.html) 作为访问 APP 中 SQLite 数据库的抽象层。

# 定义 Schema 和 Contract
SQL 数据库的首要原则之一就是 schema：说明数据库如何组织的正式定义。schema 反映在用来创建数据库的 SQL 语句中。因此，最好能创建一个伴随类（companion class），又被称为合同类（contract class），通过系统性和自文档化的方式来明确说明 schema 的设计。

一个 contract 类实际上是一堆常量的 container，这些常量定义了 URI、表、列的名字。这种集中定义的方式也使得一处改动处处受益。

contract 类的一种良好组织方式是将数据库的全局定义放于类的根级别，然后在 contract 类的内部为每个表创建对应的内部类，在表内部类中列出表的列。

> 笔记：内部类通过继承 [BaseColumns](https://developer.android.google.cn/reference/android/provider/BaseColumns.html) 接口，可以继承主键成员变量 `_ID`。创建数据库的表并非一定需要该列，但是一些 Android 类比如 cursor adaptors 默认表的该列存在，所以有该变量可以使数据库和 Android 框架更好地配合。

一个为单个表定义了表名和列明的 contract 类例子：
```java
public final class FeedReaderContract {
    // 为防止该类被实例化，将构造函数设为私有
    private FeedReaderContract() {}

    /* 定义表内容的内部类 */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
```

# 用一个 SQL Helper 创建数据库
当定义好数据库的外观后，应该实现一些方法来创建和维持数据库和表。下面是典型的创建和删除表的语句：
```java
private static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
    FeedEntry._ID + " INTEGER PRIMARY KEY," +
    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
    FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

private static final String SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
```
> **阿懂的补充**：这里的代码应该是放在后面的 FeedReaderDbHelper.java 中的

就像存储于设备 [internal storage](https://developer.android.google.cn/guide/topics/data/data-storage.html#filesInternal) 的文件，Android 将数据库保存于与应用关联的私有磁盘空间。默认情况下该区域其它应用不可访问。

[SQLiteOpenHelper](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html) 类提供了一系列有用的 API。当使用该类来获得数据库的引用，系统只会在需要且不是 app 启动时执行可能的长时间操作，比如创建、更新数据库等。简言之，只需调用 [getWritableDatabase()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#getWritableDatabase()) 或 [getReadableDatabase()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#getReadableDatabase()) 来获取数据库的引用，具体的数据库创建操作，系统会在需要时进行。

> 笔记：由于一些操作可能是长时间操作，确保在后台线程调用 [getWritableDatabase()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#getWritableDatabase()) 或 [getReadableDatabase()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#getReadableDatabase())，如通过 [AsyncTask](https://developer.android.google.cn/reference/android/os/AsyncTask.html) 或 [IntentService](https://developer.android.google.cn/reference/android/app/IntentService.html)。

为使用 [SQLiteOpenHelper](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html)，创建子类复写 [onCreate()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#onCreate(android.database.sqlite.SQLiteDatabase))、 [onUpgrade()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#onUpgrade(android.database.sqlite.SQLiteDatabase,int,int))
 和 [onOpen()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#onOpen(android.database.sqlite.SQLiteDatabase)) 回调函数。你可能还想实现 [onDowngrade()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html#onDowngrade(android.database.sqlite.SQLiteDatabase,int,int))，但该实现并非必须。

 下面是一个实现了 [SQLiteOpenHelper](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html) 类并使用一些上述命令的例子：
 ```java
 public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // 如果改变了数据库的 schema，必须在此处增加数据库版本
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 该例子数据库只用来作在线数据的缓存，所以它的升级策略只是简单地删除全部数据重新创建
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
 ```

 为访问数据库，实例化上面实现的 [SQLiteOpenHelper](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteOpenHelper.html) 类子类

 # 向数据库中插入数据

 通过向 [insert()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteDatabase.html#insert(java.lang.String,java.lang.String,android.content.ContentValues)) 方法传递 [ContentValues](https://developer.android.google.cn/reference/android/content/ContentValues.html) 对象来向数据库中插入数据：
 ```java
 // 获取写模式数据库
SQLiteDatabase db = mDbHelper.getWritableDatabase();

// 创建要插入的键值对，列名为键
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);
values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

// 插入新行，返回新行主键值
long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
 ```

[insert()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteDatabase.html#insert(java.lang.String,java.lang.String,android.content.ContentValues)) 第一个参数为要插入的表名，第二个参数为 null 表示当第三个参数 values 没有值时不插入数据。

# 从数据库中读取数据
通过 [query()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteDatabase.html#query(boolean,java.lang.String,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String,java.lang.String,java.lang.String,java.lang.String)) 方法来读取数据，返回数据封装在 cursor 对象中。例子：
```java
SQLiteDatabase db = mDbHelper.getReadableDatabase();

// 指定从数据库返回的列
String[] projection = {
    FeedEntry._ID,
    FeedEntry.COLUMN_NAME_TITLE,
    FeedEntry.COLUMN_NAME_SUBTITLE
    };

// WHERE 子句 "title" = 'My Title'
String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
String[] selectionArgs = { "My Title" };

// 返回数据排列方式
String sortOrder =
    FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

Cursor cursor = db.query(
    FeedEntry.TABLE_NAME,                     // 要查询的表
    projection,                               // 要返回的列
    selection,                                // WHERE 子句的列
    selectionArgs,                            // WHERE 子句的对应值
    null,                                     // 不设定 group
    null,                                     // 不筛选 group
    sortOrder                                 // 排序
    );
```

> **阿懂的补充**：如果需要查询多表，使用 SQLiteDatabase 的 [rawQuery()](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html)。

要查看 cursor 中的行，使用 [Cursor](https://developer.android.google.cn/reference/android/database/Cursor.html) 的某个 move 方法，该方法在准备读取数据前必须调用。由于 cursor 的开始位置是 `-1`，调用 [moveToNext()](https://developer.android.google.cn/reference/android/database/Cursor.html#moveToNext()) 将“read position” 置于结果集的 first entry，当无法 [moveToNext()](https://developer.android.google.cn/reference/android/database/Cursor.html#moveToNext()) 时该方法返回 false。

对于每一行，可以通过调用 [Cursor](https://developer.android.google.cn/reference/android/database/Cursor.html) 的某个 `get` 方法来获取某列的值，如 [getString()](https://developer.android.google.cn/reference/android/database/Cursor.html#getString(int)) 或 [getLong()](https://developer.android.google.cn/reference/android/database/Cursor.html#getLong(int))。

对于每个 `get` 方法，需要传入列的 index position，通过向 [getColumnIndex()](https://developer.android.google.cn/reference/android/database/Cursor.html#getColumnIndex(java.lang.String)) 或 [getColumnIndexOrThrow()](https://developer.android.google.cn/reference/android/database/Cursor.html#getColumnIndexOrThrow(java.lang.String)) 传入列名获取列对应的 index position。

当结果集遍历结束，调用 cursor 的 [close()](https://developer.android.google.cn/reference/android/database/Cursor.html#close()) 释放资源。

例子：
```java
List itemIds = new ArrayList<>();
while(cursor.moveToNext()) {
  long itemId = cursor.getLong(
      cursor.getColumnIndexOrThrow(FeedEntry._ID));
  itemIds.add(itemId);
}
cursor.close();
``` 

# 从数据库中删除信息
为从表中删除行，需要提供 selection criteria 来标识要删除的行。

数据库 API 提供了创建 selection criteria 的机制来防止 SQL 注入。

该机制将 selection specification 分成 selection clause 和 selection arguments。clause 部分定义了要查看的列与相应的列检测；arguments 部分是 clause 中列检测对应的值。

栗子：
```java
// 定义 'where' 部分
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
// 按照占位符顺序指定对应的值
String[] selectionArgs = { "MyTitle" };
// 执行 SQL 语句
db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
```

# 更新数据库中的信息
当需要修改数据库中某条信息部分列的值时，使用 [update()](https://developer.android.google.cn/reference/android/database/sqlite/SQLiteDatabase.html#update(java.lang.String,android.content.ContentValues,java.lang.String,java.lang.String[])) 方法
栗子：
```java
SQLiteDatabase db = mDbHelper.getWritableDatabase();

// 需要更新的列的新值
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);

// 指明需要修改哪行的语句
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
String[] selectionArgs = { "MyTitle" };

int count = db.update(
    FeedEntry.TABLE_NAME,
    values,
    selection,
    selectionArgs);
```

> 阿懂的发现：此处 `db.update()` 方法的第一个参数原文中为 `FeedReaderDbHelper.FeedEntry.TABLE_NAME` 有误。

# 维持持久的数据库连接

由于数据库关闭后调用 [getWritableDatabase()]() 和 [getReadableDatabase()]() 开销较大，所以在需要访问数据库的期间内应尽可能保持数据库连接。

通常，理想的关闭数据库时机是在 Activity 的 [onDestroy()](https://developer.android.google.cn/reference/android/app/Activity.html#onDestroy()) 中

```java
@Override
protected void onDestroy() {
    mDbHelper.close();
    super.onDestroy();
}
``` 












