参见：[Build a Simple User Interface](https://developer.android.google.cn/training/basics/firstapp/building-ui.html)

在本课中，您将使用 Android Studio 布局编辑器创建包含文本框和按钮的布局。 

在下一课中，点击按钮，app 会把文本输入框的内容发送到另一个 activity。

![最终布局截图](https://developer.android.google.cn/training/basics/firstapp/images/screenshot-activity1.png)

Android app 的用户界面是使用*布局 layout*（[ViewGroup](https://developer.android.google.cn/reference/android/view/ViewGroup.html) 对象）和*小部件 widget*（[View](https://developer.android.google.cn/reference/android/view/View.html) 对象）的层次结构构建的。

布局是不可见的容器，用于控制其子 view 在屏幕上的位置。小部件是 UI 组件，比如按钮、文本框等。

![说明 `ViewGroup` 对象如何在布局中形成分支并包含 `View` 对象](https://developer.android.google.cn/images/viewgroup_2x.png)

Android 为 `ViewGroup` 和 `View` 类提供了 XML 词汇表，所以大多数 UI 都是在 XML 文件中定义的。

但是，本课程不教你编写 XML，而是教你如何使用 Android Studio 的布局编辑器来创建布局，因为通过拖拽放置 view 构建布局更轻松。

# 打开布局编辑器

> 笔记：该课默认你使用 [Android Studio 3.0](https://developer.android.google.cn/studio/) 且你已根据之前的课程[创建了你的 Android 项目](\Create-an-Android-Project.md)

开始前，如下设置你的工作区：

1. 在 Android Studio 的 Project 窗口，打开 **app > res > layout > activity_main.xml**。
1. 为使布局编辑器有更多空间，通过选择 **View > Tool Windows > Project** 隐藏 Project 窗口（或点击 Android Studio 左侧的 Project ![](https://developer.android.google.cn/studio/images/buttons/window-project.png)）
1. 若编辑器显示 XML 源文件，点击窗口底部的 **Design** 选项卡。
1. 点击 **Select Design Surface** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-design.png) 并选择 **Blueprint**
1. 
