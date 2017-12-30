参见：[Specifying the Code to Run on a Thread](https://developer.android.com/training/multiple-threads/define-runnable.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [定义实现 Runnable 的类](#%E5%AE%9A%E4%B9%89%E5%AE%9E%E7%8E%B0-runnable-%E7%9A%84%E7%B1%BB)
- [实现 run() 方法](#%E5%AE%9E%E7%8E%B0-run-%E6%96%B9%E6%B3%95)

# 概述
本课程向你展示如何实现 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 类，它能将它的 [Runnable.run()](https://developer.android.com/reference/java/lang/Runnable.html#run()) 方法内的代码运行于另一线程。你还可以将 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 传递给另一个对象后将其联系到一个线程上运行它。一或多个执行特定操作的 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 对象又被称为任务（task）。

[Thread](https://developer.android.com/reference/java/lang/Thread.html) 和 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 是基础类，这意味着它们本身功能并不强大。它们是一些非常强大的 Android 类，如：[HandlerThread](https://developer.android.com/reference/android/os/HandlerThread.html)、[AsyncTask](https://developer.android.com/reference/android/os/AsyncTask.html)、[IntentService](https://developer.android.com/reference/android/app/IntentService.html)的基础。[Thread](https://developer.android.com/reference/java/lang/Thread.html) 和 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 同样还是 [ThreadPoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor.html) 类的基础。该类能自动管理线程和任务队列，甚至并行运行多个线程。

# 定义实现 Runnable 的类
定义实现 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 的类非常直观，如下：
```java
public class PhotoDecodeRunnable implements Runnable {
    ...
    @Override
    public void run() {
        /*
         * 你想运行于该线程的代码写在这
         */
        ...
    }
    ...
}
```

# 实现 run() 方法
在该类中 [Runnable.run()](https://developer.android.com/reference/java/lang/Runnable.html#run()) 方法包含要被执行的代码。通常，[Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 内做啥都行，但是，记住，[Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 不能运行于 UI 线程，所以它不能直接修改如 [View](https://developer.android.com/reference/android/view/View.html) 对象之类的 UI 对象。为和 UI 对象交互，使用课程 [Communicate with the UI Thread](https://developer.android.com/training/multiple-threads/communicate-ui.html) 内的技术。

在 [run()](https://developer.android.com/reference/java/lang/Runnable.html#run()) 方法的开始，通过调用 [Process.setThreadPriority()](https://developer.android.com/reference/android/os/Process.html#setThreadPriority(int)) 并向其传入 [THREAD_PRIORITY_BACKGROUND](https://developer.android.com/reference/android/os/Process.html#THREAD_PRIORITY_BACKGROUND) 参数将当前线程设置为后台优先。通过这种方式可以减少 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 对象的线程和 UI 线程间的竞争。

还应该通过调用 [Thread.currentThread()](https://developer.android.com/reference/java/lang/Thread.html#currentThread()) 将 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 对象 [Thread](https://developer.android.com/reference/java/lang/Thread.html) 的引用保存在 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 中。

如下设置 [run()](https://developer.android.com/reference/java/lang/Runnable.html#run()) 方法：
```java
class PhotoDecodeRunnable implements Runnable {
...
    /*
     * 定义执行该任务的代码
     */
    @Override
    public void run() {
        // 设置当前线程的优先级
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        ...
        /*
         * 将当前线程保存在 PhotoTask 实例中
         * 让实例可以中断线程
         */
        mPhotoTask.setImageDecodeThread(Thread.currentThread());
        ...
    }
...
}
```