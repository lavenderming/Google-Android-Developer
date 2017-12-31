参见：[UI Overview](https://developer.android.com/guide/topics/ui/overview.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [用户界面布局](#%E7%94%A8%E6%88%B7%E7%95%8C%E9%9D%A2%E5%B8%83%E5%B1%80)
- [用户界面组件](#%E7%94%A8%E6%88%B7%E7%95%8C%E9%9D%A2%E7%BB%84%E4%BB%B6)

# 概述
Android app 的所有用户界面元素都是用 [View](https://developer.android.com/reference/android/view/View.html) 和 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 对象构建的。[View](https://developer.android.com/reference/android/view/View.html) 是可以在屏幕上绘制东西供用户交互的对象。[ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 是容纳其它 [View](https://developer.android.com/reference/android/view/View.html) （以及 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html)）来定义界面布局的对象。

Android 提供了一系列的 [View](https://developer.android.com/reference/android/view/View.html) 和 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 的子类，这些子类提供了常用的输入控制（如按钮和文本框）以及多样的布局模型（如线性或相关布局）。

# 用户界面布局
如下图所示，app 中每个组件的用户界面都是用层次结构的 [View](https://developer.android.com/reference/android/view/View.html) 和 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 对象定义的。每个 ViewGroup 都是组织子 view 的不可见容器，且每个子 view 可能是输入控制部件或其它绘制一部分 UI 的小部件。UI 构成的层次结构树可以依你需要或简单或复杂（但简单有助于性能）。

![图1. 图解定义 UI 布局的 view 层次结构](https://developer.android.com/images/viewgroup_2x.png)

为声明你的布局，你可以在代码中实例化 [View](https://developer.android.com/reference/android/view/View.html) 对象后开始构建层次结构树，但最简单且最高效定义布局的方式是通过 XML 文件。XML 为布局提供了一个可读的结构，类似于 HTML。

View 的 XML 元素的名字与它所表示的 Android 类相对应。因此一个 `<TextView>` 元素在 UI 中创建一个 [TextView](https://developer.android.com/reference/android/widget/TextView.html) 小部件，一个 `<LinearLayout>` 元素创建一个 [LinearLayout](https://developer.android.com/reference/android/widget/LinearLayout.html) View Group。

例如，一个简单的带有一个 text view 和一个 button 的垂直布局：
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="fill_parent"
              android:layout_height="fill_parent"
              android:orientation="vertical" >
    <TextView android:id="@+id/text"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="I am a TextView" />
    <Button android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="I am a Button" />
</LinearLayout>
```

当你在 app 中加载布局资源时，Android 将布局文件中的每个节点初始化为一个运行时对象，你可以使用该对象来定义额外的行为，查询对象的状态，或更改布局。

关于创建 UI 布局的完整指南，见：[XML Layouts](https://developer.android.com/guide/topics/ui/declaring-layout.html)。

# 用户界面组件

你无须使用 [View](https://developer.android.com/reference/android/view/View.html) 和 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 对象来构建你的全部 UI。Android 提供一系列 app 组件，他们提供了标准的 UI 布局，你需要做的只是定义其内容。每种 UI 组件都有其独有的一组 API，这些 API 在它们各自的文档中有描述，如：[Adding the App Bar](https://developer.android.com/training/appbar/index.html)、[Dialogs](https://developer.android.com/guide/topics/ui/dialogs.html) 以及 [Status Notifications](https://developer.android.com/guide/topics/ui/notifiers/notifications.html)。