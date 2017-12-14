参见：[Saving Data Using the Room Persistence Library](https://developer.android.google.cn/training/data-storage/room/index.html)

- [Get started](#get-started)

Room 提供了覆盖 SQLite 的抽象层，在完全发挥 SQLite 能力的同时提供流畅的数据库访问。

将数据持久保存于本地对 app 来讲十分有益。常见的一种情况是缓存数据，这样，当设备没有网络时，用户仍可以离线浏览内容，一旦设备联网，任何用户离线时的改变将同步至网络。

由于 Room 可以完成这些工作，Google 强烈建议使用 Room 代替 SQLite。但是，若你仍喜欢[使用 SQLite API](https://developer.android.google.cn/training/data-storage/sqlite.html) 来操纵数据库，Android 任然提供了直接访问 SQLite 数据库的支持。

Room 中有 3 个主要部件：
- [**Database**](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html)：包含 database holder 且作为主要访问点来连接底层的持久化关系型 app 数据。

  使用 [@Database](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html) 注解的类应满足如下条件：
  
  - 是一个继承了 [RoomDatabase](https://developer.android.google.cn/reference/android/arch/persistence/room/RoomDatabase.html) 的抽象类
  - 注解内包含一列与数据库相关的 entity
  - 类内包含一个抽象方法，该方法没有参数且返回类型是使用 [@Dao](https://developer.android.google.cn/reference/android/arch/persistence/room/Dao.html) 注解的类
  
  运行时可以通过调用 [Room.databaseBuilder()](https://developer.android.google.cn/reference/android/arch/persistence/room/Room.html#databaseBuilder(android.content.Context,java.lang.Class<T>,java.lang.String)) 或 [Room.inMemoryDatabaseBuilder()](https://developer.android.google.cn/reference/android/arch/persistence/room/Room.html#inMemoryDatabaseBuilder(android.content.Context,java.lang.Class<T>)) 获取 [Database](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html) 的实例。

- [**Entity**](https://developer.android.google.cn/training/data-storage/room/defining-data.html)：代表数据库中的表。

- [**DAO**](https://developer.android.google.cn/training/data-storage/room/accessing-data.html)：包含用于访问数据库的方法。

图片表示了上述组件它们之间的关系以及它们和 app 其它部分的关系。

![Room 架构示意图](https://developer.android.google.cn/images/training/data-storage/room_architecture.png)

下面的代码片段表示一个有一个 entity 和一个 DAO 示例数据库：

文件名：User.java
```java
@Entity
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // 由于简洁，此处省略了 Getters 和 setters 函数
    // 但 Room 机制的工作需要它们
}
```

文件名：UserDao.java
```java
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

文件名：AppDatabase.java
```java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

在创建了上述的文件后，可以通过如下代码获取已创建的数据库实例：

```java
AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
```

> 笔记：由于每个 [RoomDatabase](https://developer.android.google.cn/reference/android/arch/persistence/room/RoomDatabase.html) 实例开销极大且极少需要访问多个实例，故实例化 `AppDatabase` 对象时应遵守单例模式。

# Get started
- [Get started -> 前往网页](https://developer.android.google.cn/training/data-storage/room/defining-data.html)


