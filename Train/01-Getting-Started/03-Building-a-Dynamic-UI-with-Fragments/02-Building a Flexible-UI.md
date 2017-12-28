参见：[Building a Flexible UI](https://developer.android.com/training/basics/fragments/fragment-ui.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)
- [Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html)

# 可以尝试
- [FragmentBasics.zip](Sample/FragmentBasics.zip)

# 概述
当你设计的 app 要支持多种屏幕尺寸时，你可以基于可用的屏幕空间在不同的布局配置中复用 fragment 来优化用户体验。

例如，手持设备在单面板 UI 时同一时间可能更适合只显示一个 fragment。相反，在有更宽屏幕尺寸的平板上你可能想并列 fragment 来向用户显示更多信息。

![图1.两个 fragment 在同一个 activity 上依据不同的配置上显示，配置依据不同的屏幕尺寸加载。在大屏上，两个 fragment 并列显示，但在手持设备上同一时间只显示一个 fragment，当用户浏览时两个 fragment 相互替换](https://developer.android.com/images/training/basics/fragments-screen-mock.png)

[FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html) 类提供了一些方法让你在运行时向某个 activity 中添加、移除、替换 fragment 以创建动态的体验。