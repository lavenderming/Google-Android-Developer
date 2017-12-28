参见：[Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)

- [依赖与前置要求](#%E4%BE%9D%E8%B5%96%E4%B8%8E%E5%89%8D%E7%BD%AE%E8%A6%81%E6%B1%82)
- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [课程](#%E8%AF%BE%E7%A8%8B)

# 依赖与前置要求
- Activity 生命周期的基础知识（见：[Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/index.html)）
- 创建 [XML layouts](https://developer.android.com/guide/topics/ui/declaring-layout.html) 的经验

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)
- [Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html)

# 可以尝试
- [FragmentBasics.zip](Sample/FragmentBasics.zip)

# 概述
要在 Android 中创建一个动态且多面板的用户界面，你需要将 UI 组件和 activity 行为封装到可以适时移入或移出 Activity 的模块中。

你可以使用 [Fragment](https://developer.android.com/reference/android/app/Fragment.html) 类来创建这些模块，这些模块就像内部 Activity，它们可以定义自己的布局、管理自己的生命周期。

当一个 fragment 指定了自己的布局，它可以在一个 activity 中根据配置和其它 fragment 有不同的组合，比如当对不同屏幕尺寸有不同的布局配置时（小屏幕显示一个 fragment，大屏幕显示两个或多个 fragment）。

本课程将向你展示如何通过 fragment 创建动态用户界面并优化 app 对不同屏幕尺寸设备的用户体验，且所有这些能支持到最早运行 Android 1.6 的设备。

# 课程
- [Creating a Fragment](https://developer.android.com/training/basics/fragments/creating.html)
    
    学习如何创建一个 fragment 并在其回调方法中实现基本行为
- [Building a Flexible UI](https://developer.android.com/training/basics/fragments/fragment-ui.html)

    学习如何构建对不同屏幕尺寸提供不同 fragment 配置的 app 布局
- [Communicating with Other Fragments](https://developer.android.com/training/basics/fragments/communicating.html)

    学习如何建立 fragment 到 activity 以及其它 fragment 的通信。