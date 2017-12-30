参见：[Adding Swipe-to-Refresh To Your App](https://developer.android.com/training/swipe/add-swipe-interface.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [示例 app](#%E7%A4%BA%E4%BE%8B-app)
- [概述](#%E6%A6%82%E8%BF%B0)
- [添加 SwipeRefreshLayout 小部件](#%E6%B7%BB%E5%8A%A0-swiperefreshlayout-%E5%B0%8F%E9%83%A8%E4%BB%B6)

# 还需阅读
- [Accessibility](https://developer.android.com/guide/topics/ui/accessibility/index.html)

- [Adding the Action Bar](https://developer.android.com/training/basics/actionbar/index.html)

# 示例 app
- [SwipeRefreshLayoutBasic](https://github.com/googlesamples/android-SwipeRefreshLayoutBasic/)
- [SwipeRefreshListFragment](https://github.com/googlesamples/android-SwipeRefreshListFragment/)

# 概述

下拉刷新 UI 模式完全在 SwipeRefreshLayout 小部件中实现，它能捕获下拉手势，显示独特的进度条，并触发 app 中的回调函数。实现该功能需要先将该小部件添加到布局文件中作为 [ListView](https://developer.android.com/reference/android/widget/ListView.html) 或 [GridView](https://developer.android.com/reference/android/widget/GridView.html) 的父布局，然后实现当用户下拉时被调用的刷新行为。

本课向你展示如何向现有的布局中添加这个小部件，还向你展示如何把刷新 action 添加到 app bar 的浮动菜单中，这样无法使用下拉手势的用户就可以通过外部设备手动触发刷新了。

# 添加 SwipeRefreshLayout 小部件
为添加下拉刷新部件到一个现有 app，将 [SwipeRefreshLayout](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html) 作为单个 [ListView](https://developer.android.com/reference/android/widget/ListView.html) 或 [GridView](https://developer.android.com/reference/android/widget/GridView.html) 的父布局添加。记住 [SwipeRefreshLayout](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html) 只支持单个的  [ListView](https://developer.android.com/reference/android/widget/ListView.html) 或 [GridView](https://developer.android.com/reference/android/widget/GridView.html) 作为子布局。

下面的例子显示了如何将 [SwipeRefreshLayout](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html) 部件添加到一个包含一个
[ListView](https://developer.android.com/reference/android/widget/ListView.html) 的布局文件中：

```xml
<android.support.v4.widget.SwipeRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/swiperefresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</android.support.v4.widget.SwipeRefreshLayout>
```
