参见：[Processes and Threads](https://developer.android.com/guide/components/processes-and-threads.html)

当 app 的一个组件启动且 app 没有任何其它组件运行，Android 系统会启动一个新的仅有一个线程的 Linux 进程来执行应用。默认情况下，同一个 app 的所有组件运行在同一个进程和线程（被称为主（main）线程）。如果 app 的一个组件启动时已经有一个进程在为该 app 工作（这种情况是因为 app 的其它组件已在运行），那么该组件会在已有的进程中启动并使用相同的线程来执行。但是，你可以安排 app 中的不同组件运行于不同的进程，同样也可以为任何进程添加额外的线程。

该文讨论 Android app 中的进程与线程如何工作。

# 进程
默认情况，同一 app 的所有组件运行于同一进程且大多数 app 不用改变这种情况。但是，如果你发现你需要控制哪个特定组件属于某进程，你可以在 manifest 文件中做出控制。

manifest 内各种类型的组件元素——[\<activity>](https://developer.android.com/guide/topics/manifest/activity-element.html)、[\<service>](https://developer.android.com/guide/topics/manifest/service-element.html)、[\<receiver>](https://developer.android.com/guide/topics/manifest/receiver-element.html) 以及 [\<provider>](https://developer.android.com/guide/topics/manifest/provider-element.html)，都支持 `android:process` 属性，该属性指定组件应该运行于哪个进程。你可以设置该属性让每个组件运行于它独有的进程中或让一些组件共享一个不被其它组件分享的进程。你还可以设置 `android:process` 让不同 app 的组件运行于同一个进程——只要这些app 共享相同的 Linux 用户 ID 以及拥有相同的签名。

[\<application>](https://developer.android.com/guide/topics/manifest/application-element.html) 元素也支持 `android:process` 属性，这里可以设置应用于所有组件的 `android:process` 默认值。

当内存低且其它更迫切向用户提供服务的组件需要时，Android 可能会决定关闭某进程。相应的，运行于该进程上的 app 组件会被销毁。当又有其它工作需要这些组件执行时，进程会再次启动。

在决定杀掉（kill）哪个进程时，Android 系统会评估他们对用户的相对重要程度。例如，与运行可见 Activity 的进程相比，系统更倾向关闭屏幕上不再可见的 Activity 所在的进程。因此，是否终止进程的决定取决于在该进程中运行的组件的状态。



