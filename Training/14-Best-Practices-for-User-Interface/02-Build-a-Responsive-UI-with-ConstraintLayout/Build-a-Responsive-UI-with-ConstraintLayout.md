参见：[Build a Responsive UI with ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [约束概览](#%E7%BA%A6%E6%9D%9F%E6%A6%82%E8%A7%88)
- [添加 ConstraintLayout 到项目](#%E6%B7%BB%E5%8A%A0-constraintlayout-%E5%88%B0%E9%A1%B9%E7%9B%AE)

# 概述

[ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 允许你在平面的 view 层次结构（没有内嵌的 view groups）中创建大而复杂的布局。它类似 [RelativeLayout](https://developer.android.com/reference/android/widget/RelativeLayout.html)：所有 view 的位置取决于它的兄弟 view 和父布局之间的关系，但 [ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 比 `RelativeLayout` 更灵活且更方便同 Android Studio 布局编辑器一起使用。

由于布局 API 和布局编辑器都为对方而开发，`ConstraintLayout` 的所有功能都能直接通过布局编辑器的可视化工具完成。所以你完全可以通过拖拽来构建你的 `ConstraintLayout`，无需去编辑 XML。

<div style="position:relative;height:0;padding-bottom:56.12%"><iframe src="https://www.youtube.com/embed/XamMbnzI5vE?ecver=2" style="position:absolute;width:100%;height:100%;left:0" width="642" height="360" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe></div>

[ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 通过 API 库使用，该库适配 Android 2.3（API 级别 9）及更高版本。本页提供在 3.0 或更高版本 Android Studio 中通过 `ConstraintLayout` 构建布局的指南。如果你需要更多关于布局编辑器本身的信息，见 Android Studio 指南中 [Build a UI with Layout Editor](https://developer.android.com/studio/write/layout-editor.html)。

查看更多使用 `ConstraintLayout` 创建的布局，见：[Constraint Layout Examples project on GitHub](https://github.com/googlesamples/android-ConstraintLayoutExamples)

# 约束概览
为定义 `ConstraintLayout` 中 view 的位置，你必须至少给 view 添加一个横向和一个纵向的约束。每个约束都代表了与其它 view、或父布局、或不可见的参考线的连接或对齐。一个约束定义了 view 沿横轴或纵轴的位置，所以每个 view 在每个轴上必须至少有一个约束，但通常需要多个。

当你将一个 view 拖拽入布局编辑器后，它将位于你放置它的地方，即使它还没有约束。但是，这只是为了编辑的方便，如果在设备上运行时布局中的一个 view 没有约束，它将处于 \[0,0\]（左上角） 的位置。

如下图，布局在编辑器中看起来不错，但 view C 没有纵向的约束。当这个布局在设备上绘制时，view C 水平方向会和 view A 的左右边对齐，但由于没有纵向约束，view C 会出现于屏幕的顶部。

![图1.编辑器显示 view C 在 A 下，但 C 没有纵向约束](https://developer.android.com/training/constraint-layout/images/constraint-fail_2x.png)

![图2.View C 现在受纵向约束低于 view A](https://developer.android.com/training/constraint-layout/images/constraint-fail-fixed_2x.png)

尽管缺少约束不会导致编译时错误，布局编辑器仍会在工具栏上将缺少约束作为错误表明。点击 **Show Warnings and Errors** ![](https://developer.android.com/studio/images/buttons/layout-editor-errors.png) 查看错误和其它警告。为避免你遗失约束，布局编辑器可以为你自动添加约束，通过 [Autoconnect and infer constraints](https://developer.android.com/training/constraint-layout/index.html#use-autoconnect-and-infer-constraints) 功能。

# 添加 ConstraintLayout 到项目
