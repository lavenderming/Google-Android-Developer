参见：[Intents and Intent Filters](https://developer.android.com/guide/components/intents-filters.html)

- [另见](#%E5%8F%A6%E8%A7%81)
- [概述](#%E6%A6%82%E8%BF%B0)
- [暂略](#%E6%9A%82%E7%95%A5)
- [使用 pending intent](#%E4%BD%BF%E7%94%A8-pending-intent)

# 另见
- [Interacting with Other Apps](https://developer.android.com/training/basics/intents/index.html)
- [Sharing Content](https://developer.android.com/training/sharing/index.html)

# 概述

[Intent](https://developer.android.com/reference/android/content/Intent.html) 是一个用来请求来自其它 [app 组件](https://developer.android.com/guide/components/fundamentals.html#Components) 的 action 的信息对象。尽管 intent 能以多种方式促进组件间的通信，但以下三种是它的基本使用情形：

- 启动 activity


- 启动服务


- 发送广播（broadcast）

    广播是任何 app 都可接收的信息。系统为系统事件发送各种各样的广播，比如当系统启动或设备开始充电。你可以通过向 [sendBroadcast()](https://developer.android.com/reference/android/content/Context.html#sendBroadcast(android.content.Intent)) 或 [sendOrderedBroadcast()](https://developer.android.com/reference/android/content/Context.html#sendOrderedBroadcast(android.content.Intent,%20java.lang.String)) 传入 [Intent](https://developer.android.com/reference/android/content/Intent.html) 给其它 app 发送广播。

# 暂略

# 使用 pending intent

[PendingIntent](https://developer.android.com/reference/android/app/PendingIntent.html) 对象包裹了 [Intent](https://developer.android.com/reference/android/content/Intent.html) 对象。[PendingIntent](https://developer.android.com/reference/android/app/PendingIntent.html) 的首要目的是授予外部 app 权限，使其像在你 app 的独有进程上一样使用包含的 [Intent](https://developer.android.com/reference/android/content/Intent.html)。

pending intent 的主要使用情形如下：

- 

