参见：[Building a Flexible UI](https://developer.android.com/training/basics/fragments/fragment-ui.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [在运行时向 activity 添加 fragment](#%E5%9C%A8%E8%BF%90%E8%A1%8C%E6%97%B6%E5%90%91-activity-%E6%B7%BB%E5%8A%A0-fragment)

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)
- [Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html)

# 可以尝试
- [FragmentBasics.zip](Sample/FragmentBasics.zip)

# 概述
当你设计的 app 要支持多种屏幕尺寸时，你可以基于可用的屏幕空间在不同的布局配置中复用 fragment 来优化用户体验。

例如，手持设备在单面板 UI 时同一时间可能更适合只显示一个 fragment。相反，在有更宽屏幕尺寸的平板上你可能想并列 fragment 来向用户显示更多信息。

![图1.两个 fragment 在同一个 activity 上依据不同的配置上显示，配置依据不同的屏幕尺寸加载。在大屏上，两个 fragment 并列显示，但在手持设备上同一时间只显示一个 fragment，当用户浏览时两个 fragment 相互替换](https://developer.android.com/images/training/basics/fragments-screen-mock.png)

[FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html) 类提供了一些方法让你在运行时向某个 activity 中添加、移除、替换 fragment，以此创建动态的体验。

# 在运行时向 activity 添加 fragment
不同于在 activity 的布局文件中定义 fragment——如[上节课](01-Creating-a-Fragment.md)中展示的通过 `<fragment>` 元素——新方式让你可以在 activity 运行时向其添加 fragment。如果你计划在 activity 运行时更改 fragment，就必须使用新方式。

为执行如添加或删除一个 fragment 的事务，你必须使用 [FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html) 创建 [ FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html)，后者提供了添加、删除、替换以及其他 fragment 事务的 API。

如果你的 activity 允许 fragment 被**删除**或**替换**，你需要在 activity 的 [onCreate()](https://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)) 方法内添加初始状态的 fragment 到 activity 中。

处理 fragment 的一条重要规则——特别是在运行时添加 fragment——是 activity 的布局必须包含一个作为容器的可插入 fragment 的 [View](https://developer.android.com/reference/android/view/View.html)。

下面的布局是[上节课](01-Creating-a-Fragment.md)展示的布局的替代布局，该布局同一时间只能显示一个 fragment。为了将一个 fragment 替换成另一个，这个 activity 布局包含了一个空的 [FrameLayout](https://developer.android.com/reference/android/widget/FrameLayout.html) 作为 fragment 的容器。

注意到该布局的文件名和上节课展示的布局的文件名一致，但该布局的所在文件夹的文件夹名*没有* `large` 限定符，所以该布局在设备屏幕小于 `large` 时使用，因为小屏不适合同时使用两个 fragment。

```xml
<!-- res/layout/news_articles.xml -->
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragment_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
> 阿懂的注释：首先，获取事务，即 [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html) 类

在 activity 内，调用支持库 API 提供的 [getSupportFragmentManager()](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html#getSupportFragmentManager()) 方法获取一个 [FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html)。然后调用 [FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html) 的 [beginTransaction()](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html#beginTransaction()) 创建一个 [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html)。

> 阿懂的注释：然后，给事务添加操作，这里的操作时添加一个 fragment。

调用 [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html) 的 [add()](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html#add(android.support.v4.app.Fragment,java.lang.String)) 方法添加一个 fragment。你可以用相同的 [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html) 执行多个关于这个 activity 的 fragment 事务。

> 阿懂的注释：最后，提交事务

当你准备好更改时，必须调用 [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html) 的 [commit()](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html#commit())。

下面是如何向前面展示的布局添加一个 fragment 的例子
```java

```


