参见：[Sending Operations to Multiple Threads](https://developer.android.com/training/multiple-threads/index.html)

- [依赖与前置条件](#%E4%BE%9D%E8%B5%96%E4%B8%8E%E5%89%8D%E7%BD%AE%E6%9D%A1%E4%BB%B6)
- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [尝试](#%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [课程](#%E8%AF%BE%E7%A8%8B)

# 依赖与前置条件
- Android 3.0 (API Level 11) or higher
- [Loading Data in the Background](https://developer.android.com/training/load-data-background/index.html) 培训课程
- [Running in a Background Service](https://developer.android.com/training/run-background-service/index.html) 培训课程

# 还需阅读
- [Processes and Threads](https://developer.android.com/guide/components/processes-and-threads.html)

# 尝试
- [ThreadSample.zip](https://developer.android.com/shareables/training/ThreadSample.zip)

# 概述
当你将长时间、数据密集的操作分割成运行于多线程上的更小操作时，它们的速度和效率往往会提升。在拥有多核 CPU 的设备上系统可以并行执行线程，而不是让每个子操作等待机会运行。比如，在解码多个图像文件以便用缩略图显示时，每个图片在各自的线程上解码运行速度明显快于单线程上的解码。

本课程教你如何使用一个线程池对象在 app 中创建并使用多线程。你还会学到如何定义运行于线程的代码以及这些线程和 UI 线程间如何通信。

# 课程
- [Specifying the Code to Run on a Thread](https://developer.android.com/training/multiple-threads/define-runnable.html)
    
    学习如何写运行于另一线程（[Thread](https://developer.android.com/reference/java/lang/Thread.html)）的代码，通过定义实现 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 接口的类。
- [Creating a Manager for Multiple Threads](https://developer.android.com/training/multiple-threads/create-threadpool.html)

    学习如何创建用于管理一“池” [Thread](https://developer.android.com/reference/java/lang/Thread.html) 对象以及一“队” [Runnable](https://developer.android.com/reference/java/lang/Runnable.html) 对象的对象，这就是 [ThreadPoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor.html) 对象。

- [Running Code on a Thread Pool Thread](https://developer.android.com/training/multiple-threads/run-code.html)

    学习如何在来自线程池的线程上运行 [Runnable](https://developer.android.com/reference/java/lang/Runnable.html)

- [Communicating with the UI Thread](https://developer.android.com/training/multiple-threads/communicate-ui.html)

    学习从线程池中的线程到 UI 线程的通信。
