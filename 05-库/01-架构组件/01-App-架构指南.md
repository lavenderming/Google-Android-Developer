参见：[Guide to App Architecture](https://developer.android.com/topic/libraries/architecture/guide.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [app 开发者面临的常见问题](#app-%E5%BC%80%E5%8F%91%E8%80%85%E9%9D%A2%E4%B8%B4%E7%9A%84%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)
- [通用架构原则](#%E9%80%9A%E7%94%A8%E6%9E%B6%E6%9E%84%E5%8E%9F%E5%88%99)
- [推荐的 app 架构](#%E6%8E%A8%E8%8D%90%E7%9A%84-app-%E6%9E%B6%E6%9E%84)
    - [构建用户界面](#%E6%9E%84%E5%BB%BA%E7%94%A8%E6%88%B7%E7%95%8C%E9%9D%A2)
    - [获取数据](#%E8%8E%B7%E5%8F%96%E6%95%B0%E6%8D%AE)

# 概述
该指南适用于那些过去是基础 app 开发人员，而如今想了解构建健壮、生产级质量的 app 的最佳实践和推荐架构。

> **笔记：** 该指南假定读者已经熟悉 Android Framework。如果你是新开发者，请前往 [Getting Started](https://developer.android.com/training/index.html) 系列培训，那里涵盖了本指南的前置话题。

# app 开发者面临的常见问题
不像传统桌面应用，它们大多数情况下，只有一个从启动页快捷方式进入系统的 entry point 且作为整体运行，而 Android app 有更复杂的结构。一个典型的 Android app 由许多 [app components](https://developer.android.com/guide/components/fundamentals.html#Components) 构成，包括：activities, fragments, services, content providers 和 broadcast receivers。

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
      // update UI
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


