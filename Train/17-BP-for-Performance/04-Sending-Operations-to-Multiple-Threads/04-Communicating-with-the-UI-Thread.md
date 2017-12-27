参见：[Communicating with the UI Thread](https://developer.android.com/training/multiple-threads/communicate-ui.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [尝试](#%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [定义 UI 线程上的 Handler](#%E5%AE%9A%E4%B9%89-ui-%E7%BA%BF%E7%A8%8B%E4%B8%8A%E7%9A%84-handler)

# 还需阅读
- [Processes and Threads](https://developer.android.com/guide/components/processes-and-threads.html)

# 尝试
- [ThreadSample.zip](https://developer.android.com/shareables/training/ThreadSample.zip)

# 概述
上节课中你学到如何开始一个由 [ThreadPoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor.html) 管理的线程上的一个 task。本课中展示如何从 task 中向运行在 UI 线程的对象发送数据。该功能允许你的 task 完成后台工作然后将结果（如 bitmap）返回 UI 元素。

每个 app 都有它自己的一个特殊线程用于运行 UI 对象（如 [View](https://developer.android.com/reference/android/view/View.html) 对象等）。该线程被称为 UI 线程。只有运行于 UI 线程的对象能访问 UI 线程上的其它对象。由于 task 是运行在来自线程池的线程中而不是 UI 线程，所以他们不能访问 UI 对象。为将后台线程的数据移动到 UI 线程，使用运行在 UI 线程的 [Handler](https://developer.android.com/reference/android/os/Handler.html)。

# 定义 UI 线程上的 Handler
[Handler](https://developer.android.com/reference/android/os/Handler.html) 是 Android 系统框架的一部分，用于管理线程。一个 [Handler](https://developer.android.com/reference/android/os/Handler.html) 对象接收信息然后运行处理信息的代码。通常，你为一个新线程创建 [Handler](https://developer.android.com/reference/android/os/Handler.html)，但你也可以创建连接到已有线程的 [Handler](https://developer.android.com/reference/android/os/Handler.html)。当你将 [Handler](https://developer.android.com/reference/android/os/Handler.html) 连接到你的 UI 线程，则处理信息的代码也运行于 UI 线程。

