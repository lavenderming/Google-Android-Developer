参见：[Build a Simple User Interface](https://developer.android.google.cn/training/basics/firstapp/building-ui.html)

- [打开布局编辑器](#%E6%89%93%E5%BC%80%E5%B8%83%E5%B1%80%E7%BC%96%E8%BE%91%E5%99%A8)
- [添加一个 text box](#%E6%B7%BB%E5%8A%A0%E4%B8%80%E4%B8%AA-text-box)
- [添加一个 button](#%E6%B7%BB%E5%8A%A0%E4%B8%80%E4%B8%AA-button)
- [更改 UI 字符串](#%E6%9B%B4%E6%94%B9-ui-%E5%AD%97%E7%AC%A6%E4%B8%B2)
- [使 text box 自适应大小](#%E4%BD%BF-text-box-%E8%87%AA%E9%80%82%E5%BA%94%E5%A4%A7%E5%B0%8F)
- [运行 app](#%E8%BF%90%E8%A1%8C-app)

在本课中，您将使用 Android Studio 布局编辑器创建包含文本框和按钮的布局。 

在下一课中，点击按钮，app 会把文本输入框的内容发送到另一个 activity。

![图1.最终布局截图](https://developer.android.google.cn/training/basics/firstapp/images/screenshot-activity1.png)

Android app 的用户界面是使用*布局 layout*（[ViewGroup](https://developer.android.google.cn/reference/android/view/ViewGroup.html) 对象）和*小部件 widget*（[View](https://developer.android.google.cn/reference/android/view/View.html) 对象）的层次结构构建的。

布局是不可见的容器，用于控制其子 view 在屏幕上的位置。小部件是 UI 组件，比如按钮、文本框等。

![图2.说明 `ViewGroup` 对象如何在布局中形成分支并包含 `View` 对象](https://developer.android.google.cn/images/viewgroup_2x.png)

Android 为 `ViewGroup` 和 `View` 类提供了 XML 词汇表，所以大多数 UI 都是在 XML 文件中定义的。

但是，本课程不教你编写 XML，而是教你如何使用 Android Studio 的布局编辑器来创建布局，因为通过拖拽放置 view 构建布局更轻松。

# 打开布局编辑器

> 笔记：该课默认你使用 [Android Studio 3.0](https://developer.android.google.cn/studio/) 且你已根据之前的课程[创建了你的 Android 项目](Create-an-Android-Project.md)

开始前，如下设置你的工作区：

1. 在 Android Studio 的 Project 窗口，打开 **app > res > layout > activity_main.xml**。
1. 为使布局编辑器有更多空间，通过选择 **View > Tool Windows > Project** 隐藏 Project 窗口（或点击 Android Studio 左侧的 Project ![](https://developer.android.google.cn/studio/images/buttons/window-project.png)）
1. 若编辑器显示 XML 源文件，点击窗口底部的 **Design** 选项卡。
1. 点击 **Select Design Surface** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-design.png) 并选择 **Blueprint**
1. 点击工具栏上的 **Show** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-show-constraints.png) 并保证 **Show Constraints** 已勾选。
1. 确保 Autoconnect 关闭。工具栏上的工具提示显示 **Turn On Autoconnect** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-autoconnect-on.png)（因为当前处于关闭状态）
1. 点击工具栏上的 **Default Margins** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-margin.png) 并选择 **16**（你稍后仍然可以对每个 view 的 margin 进行更改）。
1. 点击工具栏上的 **Device in Editor** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-device.png) 并选择 **Pixel XL**

你现在的编辑器应如下图所示：
![图3.布局编辑器显示 `activity_main.xml` 文件](https://developer.android.google.cn/training/basics/firstapp/images/layout-editor_2x.png)

左下方的 **Component Tree** 窗口展示了布局的 view 的层次关系。当前项目的根 view 是一个 `ConstraintLayout`，只包含一个 `TextView` 对象。

`ConstraintLayout` 是一个 layout，它根据同级 view 和父布局来约束每个 view 的位置。通过这种方式，你可以使用一个“平面”的 view 层次创建简单和复杂的布局。即它避免了嵌套布局（如图2所示，一个 layout 处于另一个 layout 内），嵌套布局会绘制 UI 的时间。

![图4.图解 `ConstraintLayout` 内两个 view 的放置](https://developer.android.google.cn/training/basics/firstapp/images/constraint-example_2x.png) 

例如，你可以依据下述定义如上图所示的布局：

- View A 距父布局顶部 16dp。
- View A 距父布局左侧 16dp。
- View B 位于 View A 右侧 16dp。
- View B 与 View A 顶部对齐。

在接下来的部分，你会创建与上述类似的布局。

# 添加一个 text box

![图5.text box 受父布局的顶部和左侧约束](https://developer.android.google.cn/training/basics/firstapp/images/constraint-textbox_2x.png)

1. 首先，你需要移除目前 layout 中的东西。因此，点击 **Component Tree** 窗口中的 **TextView**，然后键盘按 Delete。
1. 从左侧的 **Palette** 窗口，点击左侧面板的 **Text**，然后将 **Plain Text** 拖拽到设计编辑器并将其放在 layout 的顶部附近。这是一个接受 plain text 输入的 [EditText](https://developer.android.google.cn/reference/android/widget/EditText.html) 部件。
1. 点击设计编辑器中的 view，现在可以看到 view 每个角上方形的调整大小柄以及每条边上圆形的约束锚点。
1. 单击并按住顶部的锚点，然后将其向上拖动，直到其与布局的顶部接触并释放。这就是一个约束——它指定 view 应该距离布局的顶部 16dp（由于前面设置默认 margin 是 16dp）。
1. 同样，从 view 的左侧到布局的左侧创建一个约束。

结果如图5的截图所示。

# 添加一个 button
![图6. button 受 text box 右侧及其基线的约束](https://developer.android.google.cn/training/basics/firstapp/images/constraint-button_2x.png)

1. 在 **Palette** 窗口，点击左侧面板的 **Widgets**，然后将 **Button** 拖拽到设计编辑器并将其放在接近右侧的位置。
1. 创建从 button 的左侧到 text box 右侧的约束
1. 要使 view 水平对齐，需要创建与 text baseline 间的约束。因此，点击 button，然后点击 **Edit Baseline** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-action-baseline.png)，**Edit Baseline** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-action-baseline.png) 在设计编辑器中被选 view 的下方。点击后，baseline 锚点出现在 button 内部。单击并按住此锚点，然后将其拖动到显示在文本框中的 baseline 锚点。

结果如图6的截图所示。

> 笔记：还可以使用顶部或底部边缘创建水平对齐，但 button 的图片边缘还包含了 padding，所以如果用此方式对齐这些 view，视觉对齐（visual alignment）会出现错误。

# 更改 UI 字符串
为预览 UI，点击工具栏上的 **Select Design Surface** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-design.png) 并选择 **Design**。注意到文本输入框被预填充了“Name”且按钮被标记为“Button”。所以需要更改这些字符串。

1. 打开 **Project** 窗口然后打开 **app > res > values > strings.xml**

    这是一个用于定义你全部 UI 字符串的 [字符串资源](https://developer.android.google.cn/guide/topics/resources/string-resource.html) 文件。这样能让你在同一个地方管理所有的 UI 字符串，这使字符串容易被查找、更新、本地化（对比在布局或程序代码中硬编码字符串）。

1. 点击编辑器窗口顶部的 **Open editor**。这会打开 [Translations Editor](https://developer.android.google.cn/studio/write/translations-editor.html)，它提供了添加和编辑你的默认字符创的简单界面，并帮助你组织全部的已翻译字符串。

    
1. 点击 **Add Key** ![](https://developer.android.google.cn/studio/images/buttons/add-sign-green-icon.png) 来创建一个新字符串作为 text box 的“提示文字”。
    
    1. 输入“edit_message”作为 key
    1. 输入“Enter a message”作为 value
    1. 点击 **OK**

   ![图7.添加新字符创的对话框](https://developer.android.google.cn/training/basics/firstapp/images/add-string_2x.png)

1. 添加另一个 key “button_send” 与 value “Send”

现在你可以为每个 view 设置这些字符串了。通过点击标签栏中的 **activity_main.xml** 返回布局文件，如下添加字符串：

1. 点击布局中的 text box，如果右侧的 **Attributes** 窗口不可见，点击右侧边栏上的 **Attributes** ![](https://developer.android.google.cn/studio/images/buttons/window-properties.png)。
1. 找到 **text** 属性（当前设置为 “Name”）并删掉值。
1. 找到 **hint** 属性并点击输入框右侧的 **Pick a Resource** ![](https://developer.android.google.cn/studio/images/buttons/pick-resource.png)。在出现的对话框中，双击列表里的 **edit_message**。
1. 现在点击布局中的 button，找到 **text** 属性，点击 **Pick a Resource** ![](https://developer.android.google.cn/studio/images/buttons/pick-resource.png)，后选择 **button_send**。

# 使 text box 自适应大小

为创建一个响应不同屏幕尺寸的布局，现在要让 text box 拉伸来填充所有剩余的水平空间（在扣除 button 和 margin 后）。

在继续前，点击工具栏的 **Show** ![](https://developer.android.google.cn/studio/images/buttons/layout-editor-design.png) 并选择 **Blueprint**。

1. 选择两个 view（点击一个，按住 Shift，点击另一个），然后右击任一 view 选择 **Chain > Create Horizontal Chain**。
    
    ![图8.点击 **Center Horizontally** 的结果](https://developer.android.google.cn/training/basics/firstapp/images/constraint-centered_2x.png)
    
    一个 [chain](https://developer.android.google.cn/training/constraint-layout/index.html#constrain-chain) 是两个或多个 view 间的一个双向约束，它允许你一起布置被 chain 的布局。

1. 选择 button 并打开 **Attributes** 窗口。使用 **Attributes** 窗口顶部的视图检查器将右 margin 设为 16。

1. 现在点击 text box 查看其 attributes。点击宽度检查器两次使其设为 **Match Constraints**，如下图所示：

    ![图9.点击将 width 改为 **Match Constraints**](https://developer.android.google.cn/training/basics/firstapp/images/properties-margin_2x.png)

    “Match constraints” 意为让宽度扩展来满足水平约束与 margin 的定义。因此，text box 拉伸以填充所有剩余的水平空间（在扣除 button 和 margin 后）。

至此，布局完成，它看起来如下图所示：

![图10.现在 text box 拉伸填充完剩余空间](https://developer.android.google.cn/training/basics/firstapp/images/constraint-chain_2x.png)

若布局与上图中展示的不一致，点击编辑器左下方的 Text 标签切换到 XML 文本页面与下面的最终 XML 文件（属性顺序不一致不影响）进行一下对比。

最终的 `activity_main.xml` 文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.myfirstapp.MainActivity">

    <EditText
        android:id="@+id/editText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:ems="10"
        android:hint="@string/edit_message"
        android:inputType="textPersonName"
        app:layout_constraintEnd_toStartOf="@+id/button"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="16dp"
        android:layout_marginStart="16dp"
        android:text="@string/button_send"
        app:layout_constraintBaseline_toBaselineOf="@+id/editText"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/editText" />
</android.support.constraint.ConstraintLayout>
```

更多关于 chain 以及其它所有可以对 `ConstraintLayout` 进行的操作，见：[Build a Responsive UI with ConstraintLayout](https://developer.android.google.cn/training/constraint-layout/index.html)

# 运行 app
若你在[上一课](Run-Your-App.md)中已经把 app 安装到设备，只需点击工具栏上的 **Apply Changes** ![](https://developer.android.google.cn/studio/images/buttons/toolbar-apply-changes.png) 让新布局跟新 app。或者点击 **Run** ![](https://developer.android.google.cn/studio/images/buttons/toolbar-run.png) 安装并运行 app。

布局上的 button 目前仍然没做任何工作。让点击 button 时能打开另一个 activity，[继续下一课](Start-Another-Activity.md)。


