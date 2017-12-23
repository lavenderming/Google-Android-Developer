参见：[Processes and Threads](https://developer.android.com/guide/components/processes-and-threads.html)

当 app 的一个组件启动且 app 没有任何其它组件运行，Android 系统会启动一个新的仅有一个线程的 Linux 进程来执行应用。默认情况下，同一个 app 的所有组件运行在同一个进程和线程（被称为主（main）线程）。如果 app 的一个组件启动时已经有一个进程在为该 app 工作（这种情况是因为 app 的其它组件已在运行），那么该组件会在已有的进程中启动并使用相同的线程来执行。但是，你可以安排 app 中的不同组件运行于不同的进程，同样也可以为任何进程添加额外的线程。

该文讨论 Android app 中的进程与线程如何工作。

# 进程
默认情况，同一 app 的所有组件运行于同一进程且大多数 app 不用改变这种情况。但是，如果你发现你需要控制哪个特定组件属于某进程，你可以在 manifest 文件中做出控制。

manifest 内各种类型的组件元素——[\<activity>](https://developer.android.com/guide/topics/manifest/activity-element.html)、[\<service>](https://developer.android.com/guide/topics/manifest/service-element.html)、[\<receiver>](https://developer.android.com/guide/topics/manifest/receiver-element.html) 以及 [\<provider>](https://developer.android.com/guide/topics/manifest/provider-element.html)，都支持 `android:process` 属性，该属性指定组件应该运行于哪个进程。你可以设置该属性让每个组件运行于它独有的进程中或让一些组件共享一个不被其它组件分享的进程。你还可以设置 `android:process` 让不同 app 的组件运行于同一个进程——只要这些app 共享相同的 Linux 用户 ID 以及拥有相同的签名。

[\<application>](https://developer.android.com/guide/topics/manifest/application-element.html) 元素也支持 `android:process` 属性，这里可以设置应用于所有组件的 `android:process` 默认值。

