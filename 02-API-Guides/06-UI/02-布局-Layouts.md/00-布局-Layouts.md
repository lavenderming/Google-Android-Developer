参见：[Layouts](https://developer.android.com/guide/topics/ui/declaring-layout.html)

- [关键类](#%E5%85%B3%E9%94%AE%E7%B1%BB)
- [另见](#%E5%8F%A6%E8%A7%81)
- [概述](#%E6%A6%82%E8%BF%B0)
- [写 XML](#%E5%86%99-xml)

# 关键类
- [View](https://developer.android.com/reference/android/view/View.html)
- [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html)
- [ViewGroup.LayoutParams](https://developer.android.com/reference/android/view/ViewGroup.LayoutParams.html)

# 另见
- [Build a Simple User Interface](https://developer.android.com/training/basics/firstapp/building-ui.html)

# 概述

布局定义了可视化的 UI 结构，比如 [activity](https://developer.android.com/guide/components/activities.html) 或 [app widget](https://developer.android.com/guide/topics/appwidgets/index.html) 的 UI。你可以通过两种方式声明布局：

- **在 XML 中声明 UI 元素**。Android 提供了直接的 XML 词汇，这些词汇对应 View 类及其子类，比如那些部件（widget）和布局的 XML 元素。
- **在运行时实例化布局元素**。你的 app 可以通过编程的方式创建 View 和 ViewGroup 对象（并管理它们的属性）。

Android 框架让你灵活组合这两种方法来声明和管理你 app 的 UI。例如，你可以在 XML 中声明你 app 的默认布局，包括要出现在屏幕上的屏幕元素和其属性。之后在运行时你可以在 app 中添加代码来修改屏幕对象的状态，包括那些在 XML 中声明的元素。

> - 你还应该尝试 [Hierarchy Viewer](https://developer.android.com/studio/profile/hierarchy-viewer.html) 工具，来 debug 布局 —— 它揭示布局属性值，通过 padding/margin 指示器绘制线框（wireframes），且当你在模拟器或设备上 debug 时完整渲染 view。
> 
> - [layoutopt](https://developer.android.com/tools/debugging/debugging-ui.html#layoutopt) 工具让你快速分析布局和层次结构中的低效等问题。

在 XML 中声明 UI 的优势在于它让你更好地将 app 的表现和控制 app 行为的代码分离。你的 UI 描述在 app 代码的外部，这意味着你可以调整或修改它们而无需修改源代码和重新编译。例如，你可以为不同的屏幕方向、不同的屏幕尺寸、不同的语言创建对应的 XML 布局。此外，在 XML 中声明布局更容易可视化 UI 的结构，因此能更容易 debug 问题。综上，该文档关注教你如何在 XML 中声明布局。如果你对在运行时实例化 View 对象更感兴趣，见 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 和 [View](https://developer.android.com/reference/android/view/View.html) 类参考。

总体来说，用于声明 UI 元素的 XML 词汇紧随类以及方法的结构和命名，元素名对应类名、属性名对应方法名。事实上，这种对应非常直接，足以让你猜出 XML 属性对应的方法，或猜出类对应的 XML 元素。然而，注意词汇不一定时独有的，所以在某些情况下，会有一些微小的命名区别。例如，EditText 元素的 `text` 属性对应 `EditText.setText()`。

> **小技巧：** 在 [Common Layout Objects](https://developer.android.com/guide/topics/ui/layout-objects.html) 中学习不同布局类型的区别。

# 写 XML 

使用 Android 的 XML 词汇，你可以快速设计 UI 布局和其包含的屏幕元素，就和你在 HTML 中创建网页一样 —— 通过一系列内嵌的元素。

每个布局文件必须包含一个根元素，该元素必须是一个 View 或 ViewGroup 对象。一旦你定义了根元素，你就可以向其中添加布局对象或部件作为子元素，逐步构建你的布局的 view 层次结构。例如，下面是一个使用 [LinearLayout](https://developer.android.com/reference/android/widget/LinearLayout.html) 来包含一个 [TextView](https://developer.android.com/reference/android/widget/TextView.html) 和一个 [Button](https://developer.android.com/reference/android/widget/Button.html) 的 XML 布局。

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="vertical" >
    <TextView android:id="@+id/text"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="Hello, I am a TextView" />
    <Button android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello, I am a Button" />
</LinearLayout>
```

在 XML 中声明布局后，保存为带 `.xml` 的文件，并将其放入 Android 项目的 `res/layout/` 文件夹下让其正确编译。

更多关于 XML 布局文件的语法，见 [Layout Resources](https://developer.android.com/guide/topics/resources/layout-resource.html) 文档。

