参见：[Guide to App Architecture](https://developer.android.com/topic/libraries/architecture/guide.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [app 开发者面临的常见问题](#app-%E5%BC%80%E5%8F%91%E8%80%85%E9%9D%A2%E4%B8%B4%E7%9A%84%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)
- [通用架构原则](#%E9%80%9A%E7%94%A8%E6%9E%B6%E6%9E%84%E5%8E%9F%E5%88%99)
- [推荐的 app 架构](#%E6%8E%A8%E8%8D%90%E7%9A%84-app-%E6%9E%B6%E6%9E%84)
    - [构建用户界面](#%E6%9E%84%E5%BB%BA%E7%94%A8%E6%88%B7%E7%95%8C%E9%9D%A2)
    - [获取数据](#%E8%8E%B7%E5%8F%96%E6%95%B0%E6%8D%AE)
        - [管理组件间的依赖](#%E7%AE%A1%E7%90%86%E7%BB%84%E4%BB%B6%E9%97%B4%E7%9A%84%E4%BE%9D%E8%B5%96)
    - [连接 ViewModel 和 repository](#%E8%BF%9E%E6%8E%A5-viewmodel-%E5%92%8C-repository)
    - [缓存数据](#%E7%BC%93%E5%AD%98%E6%95%B0%E6%8D%AE)
    - [持久化保存数据](#%E6%8C%81%E4%B9%85%E5%8C%96%E4%BF%9D%E5%AD%98%E6%95%B0%E6%8D%AE)

# 概述
该指南适用于那些过去是基础 app 开发人员，而如今想了解构建健壮、生产级质量的 app 的最佳实践和推荐架构。

> **笔记：** 该指南假定读者已经熟悉 Android Framework。如果你是新开发者，请前往 [Getting Started](https://developer.android.com/training/index.html) 系列培训，那里涵盖了本指南的前置话题。

# app 开发者面临的常见问题
不像传统桌面应用，它们大多数情况下，只有一个从启动页快捷方式进入系统的 entry point 且作为整体运行，而 Android app 有更复杂的结构。一个典型的 Android app 由多个 [app components](https://developer.android.com/guide/components/fundamentals.html#Components) 构成，包括：activities, fragments, services, content providers 和 broadcast receivers。

这些组件中的大多数需要在 app 的 [manifest](https://developer.android.com/guide/topics/manifest/manifest-intro.html) 中声明，而 manifest 文件则被 Android OS 用来判断如何将你的 app 集成到设备的整体用户体验中。并且，如上面提到的，桌面应用通常作为整体运行，而一个正确编写的 Android app 需要更灵活的处理，因为用户需要经常切换不同 app、流程以及任务。

例如，思考下当你在你最爱的社交 app 中分享照片时：app 发送一个相机 intent，然后 Android OS 启动一个相机 app 来处理这个请求；此时，用户离开了社交 app 但用户的体验是一致的；对于相机 app，可能发送另一个 intent，比如启动文件选择器，这可能会启动另一个 app；最终，用户回到社交 app 并分享照片。同样，用户可以被任意时间点的来电打断当前的进程且在结束通话后返回继续分享照片的流程。

在 Android 中，app 间的跳跃行为是很平常的，所以你的 app 必须正确处理这些流程。记住移动设备是资源受限的，因此在任意时间点，操作系统可能需要 kill 掉一些 app 来为新 app 腾出空间。

以上的关键点在于你的 app 组件可以被独立且无序地启动，而且可以在任何时间被系统或用户销毁。由于 app 组件是短暂的且它们的生命周期（从创建到销毁）不在你的控制之下，**因此你不应该在 app 组件内保存任何 app 数据以及状态** 并且你的 app 组件应该不互相依赖。

# 通用架构原则
如果你不能使用 app 组件来保存 app 数据和状态，那 app 应该如何被构建？

首先，你最应该关注是 **[关注点分离](https://en.wikipedia.org/wiki/Separation_of_concerns)**。一个很经常的错误是把所有代码写在某个 [Activity](https://developer.android.com/reference/android/app/Activity.html) 或某个 [Fragment](https://developer.android.com/reference/android/app/Fragment.html) 中。任何不处理 UI 或不执行系统交互的代码都不应该放在这些类中。让这些类尽可能“瘦”会使你避免许多生命周期相关的问题。别忘了你*并不拥有*这些类，它们只是胶水类，是系统和你 app 间的约定（contract）。Android 系统可能在任何任何时间销毁这些类，取决于用户交互或其它因素，比如低内存。所以最好降低你对它们的依赖以提供稳定的用户体验。

第二个重要的原则是你应该 **从模型（model）驱动你的 UI**，最好是一个持久模型。持久的好处有两个：一方面如果系统销毁你的 app 来释放资源，你的用户不会丢失数据；另一方面即使在网络差或没网情况下，你的 app 仍然可以工作。模型是负责为 app 处理数据的组件。它们不依赖 app view 和 app 组件，因此它们与这些组件的生命周期问题隔离。保持 UI 代码简单并将其与 app 逻辑分离能让它更容易管理。将你的 app 基于操作数据职责定义良好的模型类有助于让其可测试并使你的 app 稳定。

# 推荐的 app 架构
在本节，我们通过用例演示如何使用 [Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) 构建 app。

> **笔记：** 一种编写程序的方法适应所有的场景是不可能的。即，该推荐架构应该是绝大多数使用情况的良好起点。如果你已有写 app 的良好方式，你无须改变。

想象我们正在构建显示用户信息的 UI，该用户信息通过 REST API 从我们的私有后端获取。

## 构建用户界面
该 UI 由一个 fragment `UserProfileFragment.java` 和其对应的布局文件 `user_profile_layout.xml`。

为驱动 UI，我们的数据模型需要保存两个数据元素。

- **用户 ID**：用户的标识符。向 fragment 传递该信息的最佳方式是使用 fragment argument。如果 Android 系统销毁了你的进程，该信息会被保存所以在下次 app 重启时信息还可用。
- **用户对象**：一个保存用户数据的 POJO。

我们会基于 **ViewModel** 创建 `UserProfileViewModel` 来保存这些数据。

一个 [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel.html) 为一个特定的 UI 组件提供数据，比如 fragment 或 activity，并处理与业务部分数据处理的通信，比如调用其它组件来加载数据或转交用户的修改。ViewModel 不知晓 View 且不受配置变更的影响，比如由旋转导致的 activity 重建。

现在我们有 3 份文件：

- `user_profile.xml`：为屏幕定义的 UI
- `UserProfileViewModel.java`：为 UI 准备数据的类
- `UserProfileFragment.java`：显示在 ViewModel 以及对用户交互做出响应的 UI 控制器。

以下是最初实现（为简洁起见省略布局文件）：
```java
public class UserProfileViewModel extends ViewModel {
    private String userId;
    private User user;
    public void init(String userId) {
        this.userId = userId;
    }
    public User getUser() {
        return user;
    }
}
```

```java
public class UserProfileFragment extends Fragment {
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile, container, false);
    }
}
```

现在，我们有三个代码模块，我们该如何连接它们？最后，当 ViewModel 的 user 字段被赋值，我们需要一种方式来通知 UI。这就是 *LiveData* 类起作用的地方。

[**LiveData**](https://developer.android.com/topic/libraries/architecture/livedata.html) 是一个可观测的数据持有者。它允许你 app 的组件观测 [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html) 对象的变化而无需在其间创建显式且死板依赖路径。LiveData 还遵从 app 组件（activity、fragment、service）的生命周期变化，且能正确处理以防止对象泄漏，因此你的 app 不会花费更多内存。

> **笔记：** 如果你已经使用像 [RxJava](https://github.com/ReactiveX/RxJava) 或 [Agera](https://github.com/google/agera) 之类的库，你可以继续使用它们而不是 LiveData。但当你使用它们或其它方法，要确保你适当地处理了生命周期，比如当生命周期拥有者被停止（stopped），你的数据流要暂停；当生命周期拥有者被销毁，数据流也应该销毁。你还可以添加 `android.arch.lifecycle:reactivestreams` 将 LiveData 和其它反应流库（如：RxJava2）一同使用。

现在我们将 `UserProfileViewModel` 内的 `User` 字段替换为 `LiveData<User>`，这样当数据更新时 fragment 会接收到通知。[LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html) 最好的地方在于它能感知生命周期变化，所以当它们不再需要时它能自动清理引用。

```java
public class UserProfileViewModel extends ViewModel {
    ...
    // private User user; 该行删去
    private LiveData<User> user; // 该行新增
    public LiveData<User> getUser() {
        return user;
    }
}
```

现在我们修改 `UserProfileFragment` 来观测数据并更新 UI

```java
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel.getUser().observe(this, user -> {
      // 更新 UI
    });
}
```

每次用户数据更新，[onChanged](https://developer.android.com/reference/android/arch/lifecycle/Observer.html#onChanged(T)) 回调函数会被触发，然后 UI 会刷新。

如果你熟悉其它使用可观测回调的库，你可能已经意识到我们无需重写 fragment 的 [onStop](https://developer.android.com/reference/android/app/Fragment.html#onStop()) 方法来停止观测数据。由于 LiveData 能感知生命周期，这意味着除非 fragment 处于活动状态（接收到 [onStart()](https://developer.android.com/reference/android/app/Fragment.html#onStart()) 且还没接收到 [onStop()](https://developer.android.com/reference/android/app/Fragment.html#onStop())），否则 LiveData 不会触发回调。当 fragment 接收到 [onDestroy()](https://developer.android.com/reference/android/app/Fragment.html#onDestroy()) LiveData 会自动移除观察者。

我们同样无需做任何事情来处理配置改变（例如，用户旋转屏幕）。当配置变化，ViewModel 会自动保存数据，一旦新 fragment 可用，它会收到同一个 ViewModel 的实例且回调会立即随当前数据被调用。这就是 ViewModel 无需直接引用 View 的原因，它们可以在 View 的生命周期外生存。见：[The lifecycle of a ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html#the_lifecycle_of_a_viewmodel)。

## 获取数据

现在我们已经将 ViewModel 连接到 fragment，但 ViewModel 如何获取用户数据？在这个例子中，假设我们的后端提供了 REST API。我们使用 [Retrofit](http://square.github.io/retrofit/) 库来访问我们的后端，你可以自由使用提供类似功能的其它库。

这是用来同后端通信的 retrofit `Webservice`

```java
public interface Webservice {
    /**
     * @GET 定义一个 HTTP GET 请求
     * @Path("user") 注解位于参数 userId 旁将该参数标识为
     * 替代 @GET 路径中的 {user} 占位符
     */
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}
``` 

`ViewModel` 的一种幼稚实现是直接调用 `Webservice` 来获取数据并将其赋值给 user 对象。即使这样可以运行，随着你 app 的增长，代码将极难维持。因为这样做给 ViewModel 类太多职责，这样违背了我们之前提到的 *关注点分离* 原则。此外，ViewModel 的生存期与 [Activity](https://developer.android.com/reference/android/app/Activity.html) 或 
[Fragment](https://developer.android.com/reference/android/app/Fragment.html) 的生命周期紧密联系，所以当生命周期结束便会丢失所有数据，这是种差的用户体验。所以，我们的 ViewModel 应该将该功能委托给 **Repository** 模块。

**Repository** 模块负责处理数据操作。它们向 app 的其余部分提供了干净的 API。它们知晓从哪获取数据以及从什么 API 更新数据。你可以把 Repository 视为不同数据源（持久模型、web 服务、缓存等）间的协调者。

下面展示的 `UserRepository` 类使用 `WebService` 来获取用户数据项：

```java
public class UserRepository {
    private Webservice webservice;
    // ...
    public LiveData<User> getUser(int userId) {
        // 这不是最优实现, 我们会在后面修复
        final MutableLiveData<User> data = new MutableLiveData<>();
        webservice.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // 为简洁起见此处省略错误
                data.setValue(response.body());
            }
        });
        return data;
    }
}
```

尽管 repository 模块看起来没有必要，但它起了很重要的作用：它为 app 的其它部分抽象了数据源。现在 ViewModel 不知道数据是通过 `Webservice` 获取的，这意味着我们可以在需要时将其替换为其它实现。

> **笔记：** 为简洁起见我们忽略了网络错误。关于另一种显示错误和加载状态的实现，见 [Addendum: exposing network status](https://developer.android.com/topic/libraries/architecture/guide.html#addendum)。

### 管理组件间的依赖
上面的 `UserRepository` 类需要一个 `Webservice` 实例来完成工作。`UserRepository` 可以简单创建一个 `Webservice` 实例，但这样做需要 `UserRepository` 知道 `Webservice` 类的依赖来构建 `Webservice`。这将是非常复杂且重复的代码（比如：每个需要 `Webservice` 实例的类都需要知道如何通过 `Webservice` 的依赖来创建 `Webservice`）。此外，`UserRepository` 可能不是唯一需要 `Webservice` 的类，如果每个需要的类都创建一个新的 `Webservice`，这是非常消耗资源的。

这有两种模式你可以用来解决这个问题：
- [Dependency Injection](https://en.wikipedia.org/wiki/Dependency_injection)：依赖注入允许类定义它们的依赖而无需创建它们。在运行时，其它类有职责提供这些依赖。我们推荐在 Android app 中使用 Google 的 [Dagger 2](https://google.github.io/dagger/) 库来实现依赖。Dagger 2 通过遍历依赖关系树来自动构建对象，并为依赖关系提供编译时检测。

- [Service Locator](https://en.wikipedia.org/wiki/Service_locator_pattern)：服务定位器提供一个注册点，在这里类可以获取它们依赖而不用创建依赖对象。这相对于依赖注入实现起来较为简单，所以如果你不熟悉依赖注入，可以使用服务定位器来替代。

这些模式允许你规模化你的代码，因为它们为管理依赖提供了清晰的模式，且不会重复代码或增加复杂性。它们两者都能为测试替换实现，这也是用它们的主要好处之一。

在这个例子中，我们要使用 [Dagger 2](https://google.github.io/dagger/) 来管理依赖。

## 连接 ViewModel 和 repository

现在我们修改 `UserProfileViewModel` 来使用 repository：

```java
public class UserProfileViewModel extends ViewModel {
    private LiveData<User> user;
    private UserRepository userRepo;

    @Inject // UserRepository 参数由 Dagger 2 提供
    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(String userId) {
        if (this.user != null) {
            // 每个 Fragment 都会创建对应的 ViewModel
            // 所以我们知道 userId 不会变化
            return;
        }
        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
```

## 缓存数据

上面实现的 repository 很好地对 web 服务的调用进行了抽象，但由于它只依赖于一个数据源，还不是很实用。

上面实现的 `UserRepository` 的问题是在获取数据后，它没有在任何地方保存该数据。如果用户离开 `UserProfileFragment` 再返回，app 需要重新获取数据。两种理由可以说明这种方式的不好之处：一方面浪费了珍贵的网络带宽，另一方面强迫用户等待新的查询执行完毕。为处理这个问题，我们要给 `UserRepository` 添加新的数据源将 `User` 对象缓存在内存中。

```java
@Singleton  // 告知 Dagger 该类应该只创建一次
public class UserRepository {
    private Webservice webservice;
    // 简单的内存缓存，为简略省略细节
    private UserCache userCache;
    public LiveData<User> getUser(String userId) {
        LiveData<User> cached = userCache.get(userId);
        if (cached != null) {
            return cached;
        }

        final MutableLiveData<User> data = new MutableLiveData<>();
        userCache.put(userId, data);
        // 这仍然不是最好的但已比之前好了
        // 完整的实现必须处理错误情况
        webservice.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }
        });
        return data;
    }
}
```

## 持久化保存数据

在我们当前的实现中，如果用户旋转屏幕或离开然后返回 app，现有的 UI 会立即可见，因为 repository 从内存缓存取回数据。但用户离开 app 一小时，在 Android 系统杀死进程后返回会发生什么呢？

在当前的实现中，我们需要重新从网络获取数据。这不仅用户体验差，而且用移动网络重新获取相同数据很浪费。你可以简单地通过缓存 web 请求来修复这个问题，但这又造成新问题。如果显示的用户数据来自其它类型的请求（比如获取一列好友）会发生什么？那样你的 app 可能会显示不一致的数据，这是一种混乱的用户体验。例如，同种的用户数据可能不一致，因为好友列表请求和用户请求可能在不同的时间执行。你的 app 需要合并它们来防止展示不一致的数据。

处理这种情况的一种适合方式是使用持久模型。这就到了 [Room](https://developer.android.com/training/data-storage/room/index.html) 持久库来救场的时候了。

[Room](https://developer.android.com/training/data-storage/room/index.html) 是一个对象映射库，它提供了最少模板代码的本地数据持久保存。在编译时他会将每个请求同数据库架构验证，所以错误的 SQL 查询会导致编译期错误而不是运行时失败。Room 抽象了与原始数据库表以及查询交互的工作实现细节。它还允许观测数据库数据（包括集和连接查询）的变化，并通过 *LiveData* 将这些变化暴露出来。此外，它显式定义了线程约束，这解决了很多常见问题（比如在主线程访问存储）。

> **笔记：** 如果你的 app 已经使用其它持久方案像 SQLite 对象-关系 映射（ORM），你无须将已有方案替换为 [Room](https://developer.android.com/training/data-storage/room/index.html)。但是，如果你正在编写一个新 app 或重构一个已有 app，我们推荐使用 Room 来持久化保存你 app 的数据。这样你可以利用该库的抽象和查询验证能力。

为使用 Room，我们需要定义本地数据库架构。首先，使用 [@Entity](https://developer.android.com/reference/android/arch/persistence/room/Entity.html) 注解 `User` 类，将其标注为你数据库中的表。

```java
@Entity
class User {
  @PrimaryKey
  private int id;
  private String name;
  private String lastName;
  // 字段的 getters 和 setters
}
```

然后，为你的 app 创建一个继承 [RoomDatabase](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase.html) 的数据库类：

```java
@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
}
```

注意 `MyDatabase` 是抽象的。Room 会自动提供它的实现。更多细节见 [Room](https://developer.android.com/topic/libraries/architecture/room.html) 文档。

现在我们需要一种方式来向数据库中插入用户数据。为此，我们要创建一个 [data access object (DAO)](https://en.wikipedia.org/wiki/Data_access_object)

```java
@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void save(User user);
    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> load(String userId);
}
```

然后从数据库类中返回 DAO 的引用。

```java
@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

注意 `load` 方法返回 `LiveData<User>`。这样 Room 知晓什么时候数据库被修改，且当数据变化时它会自动通知所有处于活动状态的观测者。因为使用了 *LiveData*，这是高效的，因为它只会在至少有一个活动的观测者时更新数据。

> **笔记：** Room 基于表修改检查无效输入，这意味着它可能发送错误通知。

现在我们修改 `UserRepository` 来集成 Room 数据源

```java
@Singleton
public class UserRepository {
    private final Webservice webservice;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        // 直接从数据库返回 LiveData 
        return userDao.load(userId);
    }

    private void refreshUser(final String userId) {
        executor.execute(() -> {
            // running in a background thread
            // check if user was fetched recently
            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
            if (!userExists) {
                // refresh the data
                Response response = webservice.getUser(userId).execute();
                // TODO check for error etc.
                // Update the database.The LiveData will automatically refresh so
                // we don't need to do anything else here besides updating the database
                userDao.save(response.body());
            }
        });
    }
}
```




