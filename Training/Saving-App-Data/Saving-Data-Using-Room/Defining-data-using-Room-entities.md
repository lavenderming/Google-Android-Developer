参见：[Defining data using Room entities](https://developer.android.google.cn/training/data-storage/room/defining-data.html)

- [使用主键](#%E4%BD%BF%E7%94%A8%E4%B8%BB%E9%94%AE)
- [注解索引与唯一性](#%E6%B3%A8%E8%A7%A3%E7%B4%A2%E5%BC%95%E4%B8%8E%E5%94%AF%E4%B8%80%E6%80%A7)
- [定义对象间的关系](#%E5%AE%9A%E4%B9%89%E5%AF%B9%E8%B1%A1%E9%97%B4%E7%9A%84%E5%85%B3%E7%B3%BB)
- [系列文章](#%E7%B3%BB%E5%88%97%E6%96%87%E7%AB%A0)
    - [上一篇](#%E4%B8%8A%E4%B8%80%E7%AF%87)
    - [下一篇](#%E4%B8%8B%E4%B8%80%E7%AF%87)

在使用 [Room persistence library](https://developer.android.google.cn/training/data-storage/room/index.html) 时，你将相关字段定义为 entity。对每个 entity，都会在关联的 [Database](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html) 对象中创建对应的表来保存 item。

默认情况下，Room 会为定义在 entity 中的每个字段创建列。若 entity 的某个字段不需要创建对应列，可以使用 [@Ignore](https://developer.android.google.cn/reference/android/arch/persistence/room/Ignore.html) 注解该字段。必须通过 [Database](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html) 类中的 [entities](https://developer.android.google.cn/reference/android/arch/persistence/room/Database.html#entities()) 数组引用 entity 类。

如何定义 entity 的栗子：
```java
@Entity
class User {
    @PrimaryKey
    public int id;

    public String firstName;
    public String lastName;

    @Ignore
    Bitmap picture;
}
```
为维持字段，Room 必须可以访问该字段。所以，你可以将字段设为 `public` 或对私有字段提供 getter 和 setter 方法。若使用 getter 和 setter，注意 Room 中的 getter 和 setter 方法基于 JavaBeans conventions。

> 笔记：Entity 既可以使用空构造函数（若它对应的 [DAO](https://developer.android.google.cn/training/data-storage/room/accessing-data.html) 类可以访问他的每个持久字段）或者一个有参构造函数，其参数类型与字段匹配对应的持久字段。Room 可以使用全部或部分的构造函数，比如一个接收部分字段的构造函数。

# 使用主键
每个 entity 必须定义至少一个字段作为主键。即使当只有一个字段时，也要为该字段添加 [@PrimaryKey](https://developer.android.google.cn/reference/android/arch/persistence/room/PrimaryKey.html) 注解。如果想让 Room 给 entity 自动分配 id，可以设置 `@PrimaryKey` 的 [autoGenerate](https://developer.android.google.cn/reference/android/arch/persistence/room/PrimaryKey.html#autoGenerate()) 属性。如果 entity 有组合的主键，可以使用 [@Entity](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html) 注解的 [primaryKeys](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html#primaryKeys()) 属性，如下所示：

```java
@Entity(primaryKeys = {"firstName", "lastName"})
class User {
    public String firstName;
    public String lastName;

    @Ignore
    Bitmap picture;
}
```

默认情况下，Room 使用类名作为数据库表名。如果想设置不同的名字，设置 [@Entity](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html) 注解的 [tableName](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html#tableName()) 属性，如下：

```java
@Entity(tableName = "users")
class User {
    ...
}
```
> 注意：SQLite 中的表名是大小写敏感的

类似 [tableName](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html#tableName()) 属性，Room 使用字段名作为数据库内表的列名，若想用个不同的名字，给字段添加 [@ColumnInfo](https://developer.android.google.cn/reference/android/arch/persistence/room/ColumnInfo.html) 注解，如下所示：

```java
@Entity(tableName = "users")
class User {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Ignore
    Bitmap picture;
}
```

# 注解索引与唯一性
取决于你不同的数据访问方法，你可能需要索引数据库内的特定字段来加速 query 过程。为 entity 添加索引可以通过在 [@Entity](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html) 注解中包含 [indices](https://developer.android.google.cn/reference/android/arch/persistence/room/Entity.html#indices()) 属性，列出想包含进索引或符合索引的列名。如下所示：

```java
@Entity(indices = {@Index("name"),
        @Index(value = {"last_name", "address"})})
class User {
    @PrimaryKey
    public int id;

    public String firstName;
    public String address;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Ignore
    Bitmap picture;
}
```

有时，数据库内某些字段或某些组字段必须是唯一的。可以通过设置 [@Index](https://developer.android.google.cn/reference/android/arch/persistence/room/Index.html) 注解的 [unique](https://developer.android.google.cn/reference/android/arch/persistence/room/Index.html#unique()) 属性为 `true` 来强制这种唯一性。栗子如下：

```java
@Entity(indices = {@Index(value = {"first_name", "last_name"},
        unique = true)})
class User {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Ignore
    Bitmap picture;
}
```

# 定义对象间的关系
由于 SQLite 是关系型数据库，你可以指定对象间的关系。尽管大多数对象关系映射库允许 entity 对象间相互引用，Room 明确禁止这种行为。原因见：[了解为何 Room 不允许对象引用](https://developer.android.google.cn/training/data-storage/room/referencing-data.html#understand-no-object-references)。

尽管你不能使用直接的关系，Room 任然允许在 entity 间定义外键约束。

例如，这有个其它的 entity 名为 `Book`，你可以通过 [@ForeignKey](https://developer.android.google.cn/reference/android/arch/persistence/room/ForeignKey.html) 注解定义该 entity 与 `User` entity 间的关系。代码如下：
```java
@Entity(foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "id",
                                  childColumns = "user_id"))
class Book {
    @PrimaryKey
    public int bookId;

    public String title;

    @ColumnInfo(name = "user_id")
    public int userId;
}
```

> **阿懂的碎碎念**：机智啊，这里用 parent 和 child 来描述外键约束中列的参照关系，更清晰明了。





# 系列文章
## 上一篇
- [Saving-Data-Using-the-Room-Persistence-Library](Saving-Data-Using-the-Room-Persistence-Library.md)
## 下一篇