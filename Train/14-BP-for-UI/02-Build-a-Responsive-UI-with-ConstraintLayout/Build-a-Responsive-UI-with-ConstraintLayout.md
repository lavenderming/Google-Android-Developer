参见：[Build a Responsive UI with ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [约束概述](#%E7%BA%A6%E6%9D%9F%E6%A6%82%E8%BF%B0)
- [添加 ConstraintLayout 到项目](#%E6%B7%BB%E5%8A%A0-constraintlayout-%E5%88%B0%E9%A1%B9%E7%9B%AE)
    - [转换布局](#%E8%BD%AC%E6%8D%A2%E5%B8%83%E5%B1%80)
    - [创建一个新布局](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E6%96%B0%E5%B8%83%E5%B1%80)
- [添加一个约束](#%E6%B7%BB%E5%8A%A0%E4%B8%80%E4%B8%AA%E7%BA%A6%E6%9D%9F)
    - [父定位](#%E7%88%B6%E5%AE%9A%E4%BD%8D)
    - [顺序定位](#%E9%A1%BA%E5%BA%8F%E5%AE%9A%E4%BD%8D)

# 概述

[ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 允许你在平面的 view 层次结构（没有内嵌的 view groups）中创建大而复杂的布局。它类似 [RelativeLayout](https://developer.android.com/reference/android/widget/RelativeLayout.html)：所有 view 的位置取决于它的兄弟 view 和父布局之间的关系，但 [ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 比 `RelativeLayout` 更灵活且更方便同 Android Studio 布局编辑器一起使用。

由于布局 API 和布局编辑器都为对方而开发，`ConstraintLayout` 的所有功能都能直接通过布局编辑器的可视化工具完成。所以你完全可以通过拖拽来构建你的 `ConstraintLayout`，无需去编辑 XML。

<div style="position:relative;height:0;padding-bottom:56.12%"><iframe src="https://www.youtube.com/embed/XamMbnzI5vE?ecver=2" style="position:absolute;width:100%;height:100%;left:0" width="642" height="360" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe></div>


[ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout.html) 通过 API 库使用，该库适配 Android 2.3（API 级别 9）及更高版本。本页提供在 3.0 或更高版本 Android Studio 中通过 `ConstraintLayout` 构建布局的指南。如果你需要更多关于布局编辑器本身的信息，见 Android Studio 指南中 [Build a UI with Layout Editor](https://developer.android.com/studio/write/layout-editor.html)。

查看更多使用 `ConstraintLayout` 创建的布局，见：[Constraint Layout Examples project on GitHub](https://github.com/googlesamples/android-ConstraintLayoutExamples)

# 约束概述
为定义 `ConstraintLayout` 中 view 的位置，你必须至少给 view 添加一个横向和一个纵向的约束。每个约束都代表了与其它 view、或父布局、或不可见的参考线的连接或对齐。一个约束定义了 view 沿横轴或纵轴的位置，所以每个 view 在每个轴上必须至少有一个约束，但通常需要多个。

当你将一个 view 拖拽入布局编辑器后，它将位于你放置它的地方，即使它还没有约束。但是，这只是为了编辑的方便，如果在设备上运行时布局中的一个 view 没有约束，它将处于 \[0,0\]（左上角） 的位置。

如下图，布局在编辑器中看起来不错，但 view C 没有纵向的约束。当这个布局在设备上绘制时，view C 水平方向会和 view A 的左右边对齐，但由于没有纵向约束，view C 会出现于屏幕的顶部。

![图1.编辑器显示 view C 在 A 下，但 C 没有纵向约束](https://developer.android.com/training/constraint-layout/images/constraint-fail_2x.png)

![图2.View C 现在受纵向约束低于 view A](https://developer.android.com/training/constraint-layout/images/constraint-fail-fixed_2x.png)

尽管缺少约束不会导致编译时错误，布局编辑器仍会在工具栏上将缺少约束作为错误表明。点击 **Show Warnings and Errors** ![](https://developer.android.com/studio/images/buttons/layout-editor-errors.png) 查看错误和其它警告。为避免你遗失约束，布局编辑器可以为你自动添加约束，通过 [Autoconnect and infer constraints](https://developer.android.com/training/constraint-layout/index.html#use-autoconnect-and-infer-constraints) 功能。

# 添加 ConstraintLayout 到项目
通过如下步骤在在项目中使用 `ConstraintLayout`：

1. 确保模块级别的 `build.gradle` 文件中声明了 `maven.google.com` 仓库：
    ```
    repositories {
        maven {
            url 'https://maven.google.com'
        }
    }
    ```
1. 在同一个 `build.gradle` 文件中添加作为依赖的库：
    ```
    dependencies {
        compile 'com.android.support.constraint:constraint-layout:1.0.2'
    }
    ```
1. 在工具栏或 sync 提示中，点击 **Sync Project with Gradle Files**

> 阿懂的补充：创建项目时会自动生成 `ConstraintLayout` 需要的仓库、依赖等，但在自动生成的文件中，仓库是添加在项目级别的 `build.gradle` 文件里的。

现在可以构建你的 `ConstraintLayout` 布局了。

## 转换布局

依据如下步骤将一个现存的布局转换为 constraint 布局：
1. 在 Android Studio 中打开布局文件并点击编辑窗口底部的 **Design** 标签。
1. 在 **Component Tree** 窗口，右键需要转换的布局并点击 **Convert layout to ConstraintLayout**

![图3.](https://developer.android.com/training/constraint-layout/images/layout-editor-convert-to-constraint_2x.png)

## 创建一个新布局
通过如下步骤开始一个新的 constraint 布局文件：
1. 在 **Project** 窗口，点击要添加布局的模块文件夹然后在工具栏上选择 **File > New > XML > Layout XML**。
1. 输入布局文件的名字，然后在 **Root Tag** 的输入框中输入 `android.support.constraint.ConstraintLayout`
1. 点击 **Finish**

# 添加一个约束
从 **Palette** 窗口中拖拽一个 view 到编辑器开始。当你在 `ConstraintLayout` 中添加一个 view，这个 view 会显示为一个包围方框，方框的每个四个角各有一个方形的大小调整手柄，四个边上有圆形的约束手柄。

[视频1.将一个 view 的左侧约束到父布局的左侧](https://storage.googleapis.com/androiddevelopers/videos/studio/studio-constraint-first.mp4)

点击以选中一个 view，然后单击并按住其中一个约束手柄，将生成的线拖拽到一个可用的锚点（另一个 view 的边、布局的边、或参考线）。当你释放，约束生成，且由[一个默认的 margin](https://developer.android.com/training/constraint-layout/index.html#adjust-the-view-margins) 分隔两个 view。

当创建约束时，记住如下规则：
- 每个 view 必须至少有两个约束：一个横向，一个纵向。
- 只能在共享同一平面的约束手柄和锚点间创建约束。所以 view 的垂直平面（view 的左边或右边）只能被约束到另一个垂直平面；且 baseline 只能由另一个 baseline 约束。
- 一个约束手柄只能被一个约束使用，当可以创建指向同一个锚点的多个约束（从不同的 view）。

[视频2.添加一个与已有约束对立的约束](https://storage.googleapis.com/androiddevelopers/videos/studio/studio-constraint-second.mp4)

选中 view 后点击约束手柄移除约束。或选中 view 后点击 **Delete Constraints** ![](https://developer.android.com/studio/images/buttons/layout-editor-action-clear.png) 移除所有约束。

如视频2所示，如果你在一个 view 上添加对立约束，约束线会变得像弹簧一样来表示对立的力量。在 view 的尺寸设为 `fixed` 或 `wrap content` 时效果最明显，这时 view 处于两个约束的中间。如果你想拉伸 view 的尺寸来满足约束，[更改 view 的尺寸为 `match constraints`](https://developer.android.com/training/constraint-layout/index.html#adjust-the-view-size)；或者如果你想保持目前的尺寸但移动 view 让它不处于中间，[调整约束倾向](https://developer.android.com/training/constraint-layout/index.html#adjust-the-constraint-bias)。

你可以使用约束达成不同类型的布局行为，如下：

## 父定位

将 view 的边约束到布局的对应边。

如下图，view 的左边连接到父布局的左边。可以通过 margin 定义之间的距离。

![图4.到父布局的一个水平约束](https://developer.android.com/training/constraint-layout/images/parent-constraint_2x.png)

## 顺序定位

