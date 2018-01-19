参见：[Intents and Intent Filters](https://developer.android.com/guide/components/intents-filters.html)

- [另见](#%E5%8F%A6%E8%A7%81)
- [概述](#%E6%A6%82%E8%BF%B0)
- [Intent 的类型](#intent-%E7%9A%84%E7%B1%BB%E5%9E%8B)

# 另见
- [Interacting with Other Apps](https://developer.android.com/training/basics/intents/index.html)
- [Sharing Content](https://developer.android.com/training/sharing/index.html)

# 概述

[Intent](https://developer.android.com/reference/android/content/Intent.html) 是一个消息对象，你可以用它向另一个 [app 组件](https://developer.android.com/guide/components/fundamentals.html#Components) 请求操作。尽管 intent 以多种方式促进了组件间的通信，但这有三种基本的使用情况：

- **启动某个 activity**
    
    app 内的单个 [Activity](https://developer.android.com/reference/android/app/Activity.html) 代表单个屏幕。你可以通过向 [startActivity()](https://developer.android.com/reference/android/content/Context.html#startActivity(android.content.Intent)) 传递一个 [Intent](https://developer.android.com/reference/android/content/Intent.html) 来启动某个 [Activity](https://developer.android.com/reference/android/app/Activity.html) 的新实例。[Intent](https://developer.android.com/reference/android/content/Intent.html) 描述了需要启动的 activity 并携带了所有必须的数据。
    
    如果你想在 activity 结束时接收返回的结果，你可以调用 [startActivityForResult()](https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int))，你的 activity 会在 [onActivityResult()](https://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent)) 回调中接收结果，结果以另一个独立 [Intent](https://developer.android.com/reference/android/content/Intent.html) 对象的形式。

    更多信息，见 [Activities](https://developer.android.com/guide/components/activities.html) 指南。

- **启动某个 service**

    [Service](https://developer.android.com/reference/android/app/Service.html) 是执行后台任务且没有 UI 的组件。在 Android 5.0（API level 21）及以后，你可以通过 [JobScheduler](https://developer.android.com/reference/android/app/job/JobScheduler.html) 启动服务。更多关于 [JobScheduler](https://developer.android.com/reference/android/app/job/JobScheduler.html) 的信息，见它的 [API-reference documentation](https://developer.android.com/reference/android/app/job/JobScheduler.html)

    在 Android 5.0（API level 21）之前的版本，你可以使用 [Service](https://developer.android.com/reference/android/app/Service.html) 类的方法来启动 service。你可以通过向 [startService()](https://developer.android.com/reference/android/content/Context.html#startService(android.content.Intent)) 传递 [Intent](https://developer.android.com/reference/android/content/Intent.html) 来启动服务执行一次操作。[Intent](https://developer.android.com/reference/android/content/Intent.html) 描述了要启动的服务以及携带必要的数据。

    如果服务设计成客户-服务接口，你可以通过向 [bindService()](https://developer.android.com/reference/android/content/Context.html#bindService(android.content.Intent,%20android.content.ServiceConnection,%20int)) 传入 [Intent](https://developer.android.com/reference/android/content/Intent.html) 从其它组件绑定到服务。更多信息，见 [Services](https://developer.android.com/guide/components/services.html) 指南。

- **发送一个广播**

    广播是任何 app 都可以接收的信息。系统为系统事件发送各种广播，比如当系统启动或设备开始充电。你可以通过向 [sendBroadcast()](https://developer.android.com/reference/android/content/Context.html#sendBroadcast(android.content.Intent)) 或 [sendOrderedBroadcast()](https://developer.android.com/reference/android/content/Context.html#sendOrderedBroadcast(android.content.Intent,%20java.lang.String)) 传入 [Intent](https://developer.android.com/reference/android/content/Intent.html) 来给其它 app 发送广播。

# Intent 的类型

有两种类型的 intent：

- **显式 intent** 明确哪个 app 能符合这个 intent，通过提供目标 app 的包名或一个完整的组件类名。你通常使用显式 intent 来启动你自己 app 中的组件，因为你知道你想启动的 activity 或 service 的类名。例如，你想启动你 app 内某个 activity 来相应用户的 action，或者启动服务来在后台下载文件。
- **隐式 intent** 不指定组件的名字，但它大体声明了要执行的 action，这些 action 允许其它 app 的组件来处理。例如，如果你想在地图上显示用户位置，你可以用一个隐式 intent 来请求其它有能力的 app 在地图上显示指定位置。

图1.展示了在启动 activity 时 intent 是如何使用的。若 [Intent](https://developer.android.com/reference/android/content/Intent.html) 对象显式指定了一个 activity 组件，系统则立即启动该组件。

> ![](https://developer.android.com/images/components/intent-filters@2x.png)
> **图1.** 隐式 intent 如何经系统传递后启动另一个 activity：**[1]** *Activity A* 创建一个带 action 描述的 [Intent](https://developer.android.com/reference/android/content/Intent.html) 并将其传递给 [startActivity()](https://developer.android.com/reference/android/content/Context.html#startActivity(android.content.Intent))。**[2]** Android 系统搜索所有 app 的 intent filter 看是否能匹配 intent。当发现匹配，**[3]** 系统通过调用 activity（*Activity B*）的 [onCreate()](https://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)) 方法启动该 activity，并向其传入该 [Intent](https://developer.android.com/reference/android/content/Intent.html)。

当你使用隐式 intent，Android 系统会查找适合的组件并启动；查找方式是将 intent 的内容同设备上的所有 app 的 intent filters 进行比较，这些 intent fileter 声明在 app 的 [manifest 文件](https://developer.android.com/guide/topics/manifest/manifest-intro.html) 中。如果 intent 匹配到一个 intent filter，系统启动该组件并向其传递该 [Intent](https://developer.android.com/reference/android/content/Intent.html) 对象。如果有多个 intent filter 匹配，系统显示一个对话框让用户选择要使用的 app。