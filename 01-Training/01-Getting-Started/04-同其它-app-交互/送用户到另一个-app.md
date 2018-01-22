参见：[Sending the User to Another App](https://developer.android.com/training/basics/intents/sending.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [构建隐式 Intent](#%E6%9E%84%E5%BB%BA%E9%9A%90%E5%BC%8F-intent)

# 概述

Android 最重要的功能之一就是某个 app 可以按它想执行的“动作（action）”送用户到另一个 app 上。例如，如果你的 app 有个想显示在地图上的业务地址，你无需在自己的 app 中构建一个显示地图的 activity，相反，你可以用 [Intent](https://developer.android.com/reference/android/content/Intent.html) 构建一个显示地图的请求。Android 系统之后会启动一个能在地图上显示地址的 app。

正如在第一课 [Building Your First App](https://developer.android.com/training/basics/firstapp/index.html) 中描述的，你必须使用 intent 来浏览你 app 中的不同 activity。你通常通过 *显式 Intent* 来完成这种操作，这种 intent 定义了你想启动的组件的准确类名。然而，当你想让另一个 app 执行一个动作，比如“显示地图”，你必须使用 *隐式 Intent*。

本节课向你展示如何创建一个特定动作的隐式 intent，且如何使用该 intent 启动另一个 app 的 activity，完成该特定动作。另见下面的内嵌视屏以了解为什么要给隐式 intent 加上运行时检测。

<div style="position:relative;height:0;padding-bottom:56.5%"><iframe src="https://www.youtube.com/embed/HGElAW224dE?ecver=2" style="position:absolute;width:100%;height:100%;left:0" width="637" height="360" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe></div>

# 构建隐式 Intent

隐式 intent 不声明要启动的组件的类名，相反，它声明要执行的动作。动作指明了你想要执行的操作，比如*显示（view）*、*编辑（edit）*、*发送（send）* 或获取某些东西。intent 通常还包括和动作有关的数据，比如你想要显示的地址，或者你想要发送的 Email。取决于你创建的 intent 的不同，数据可能是 [Uri](https://developer.android.com/reference/android/net/Uri.html)， 其它几种数据类型之一，或可能这个 intent 完全不需要数据。

如果你的数据是 [Uri](https://developer.android.com/reference/android/net/Uri.html)，这有个简单的 [Intent(String action, Uri uri)](https://developer.android.com/reference/android/content/Intent.html#Intent(java.lang.String,%20android.net.Uri)) 构造方法可以用来定义动作和数据。

例如，这是构造一个启动电话的 intent 的例子，其中使用了 [Uri](https://developer.android.com/reference/android/net/Uri.html) 数据来指明电话号码：

```java
Uri number = Uri.parse("tel:5551234");
Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
```

当你通过调用 [startActivity()](https://developer.android.com/reference/android/app/Activity.html#startActivity(android.content.Intent)) 触发这个 intent 后，电话 app 被启动并按给定的电话号码初始化出拨打页面。

这有几个其它的 intent 以及他们的动作、[Uri](https://developer.android.com/reference/android/net/Uri.html) 数据对：

- 显示地图
    ```java
    // 基于地址的 map point 
    Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
    // 或基于经纬度的 map point
    // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z 参数是缩放级别
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
    ```

- 显示网页
    ```java
    Uri webpage = Uri.parse("http://www.android.com");
    Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
    ```

其它类型的隐式 intent 需要 “额外（extra）” 数据来提供不同的数据类型，比如字符串。你可以用各种各样的 [putExtra()](https://developer.android.com/reference/android/content/Intent.html#putExtra(java.lang.String,%20java.lang.String)) 方法添加一个或多个 extra 数据。

默认情况下，由系统基于 intent 包含的 [Uri](https://developer.android.com/reference/android/net/Uri.html) 数据判断它需要的 MIME 类型。如果你不在 intent 中包含 [Uri](https://developer.android.com/reference/android/net/Uri.html)，你通常应该使用 [setType()](https://developer.android.com/reference/android/content/Intent.html#setType(java.lang.String)) 来标明 intent 的数据类型。设置 MIME 类型可以进一步指明哪种类型的 activity 能接收到该 intent。

以下是另一些 intent 的例子，这些 intent 添加 extra 数据来限定想要执行的动作：

- 发送带附件的 Email：

    ```java
    Intent emailIntent = new Intent(Intent.ACTION_SEND);
    // 该 intent 没有 URI, 所以声明 MIME 类型为 "text/plain"  
    emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // 接收方
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email 主题");
    emailIntent.putExtra(Intent.EXTRA_TEXT, "Email 信息内容");
    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
    // 你也可以通过传递 Uri 的 ArrayList 来附加多个项目
    ```

- 创建一个日历事件：

    ```java
    Intent calendarIntent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
    Calendar beginTime = Calendar.getInstance().set(2012, 0, 19, 7, 30);
    Calendar endTime = Calendar.getInstance().set(2012, 0, 19, 10, 30);
    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
    calendarIntent.putExtra(Events.TITLE, "事件标题");
    calendarIntent.putExtra(Events.EVENT_LOCATION, "事件地址");
    ```

    > **笔记：** 该声明事件的 intent 只在 API level 14 和更高版本的 Android 中支持。

> **笔记：** 很重要的一点是你要将你的 [Intent](https://developer.android.com/reference/android/content/Intent.html) 尽可能准确地定义。例如，如果你想使用动作设为 [ACTION_VIEW](https://developer.android.com/reference/android/content/Intent.html#ACTION_VIEW) 的 intent 来显示一张图片，你应该指明 MIME 类型是 `image/*`。这样能防止“显示”其它类型数据（比如地图）的 app 被该 intent 触发。

