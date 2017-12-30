参见：[Creating a Manager for Multiple Threads](https://developer.android.com/training/multiple-threads/create-threadpool.html)

# 概述
上一节中展示了如何定义在另一个线程上执行的任务。如果你只运行该线程一次，上节可能就是你需要知道的全部了。但如果你想基于不同的数据重复执行任务，且同一时间只执行一个任务，[IntentService](https://developer.android.com/reference/android/app/IntentService.html) 则可以满足你的需要。

想在资源可用时自动运行任务，或允许同时运行多任务（或两者都想），你需要提供一个受管理的线程集。可以使用 [ThreadPoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor.html) 的实例来达成上述，当它的线程池中有线程处于空闲状态时，它会执行队列中的某个 task。因此，你只需将 task 添加到队列中即可被执行。

线程池可以并行执行多个 task，所以你应该确保你的代码是线程安全的。将可被多个线程访问的变量放在 `synchronized` 块中。这样可以防止一个线程读取该变量的同时另一个线程改变它。通常，这种情况发生在静态变量中，但也会出现在任何只实例化一次的对象里。想学习更多关于此的内容，阅读 [Processes and Threads](https://developer.android.com/guide/components/processes-and-threads.html) API 指南。

# 定义线程池类