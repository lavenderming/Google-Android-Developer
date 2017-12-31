参见：[Supporting Different Screens](https://developer.android.com/training/basics/supporting-devices/screens.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [创建不同布局](#%E5%88%9B%E5%BB%BA%E4%B8%8D%E5%90%8C%E5%B8%83%E5%B1%80)
- [创建不同的位图](#%E5%88%9B%E5%BB%BA%E4%B8%8D%E5%90%8C%E7%9A%84%E4%BD%8D%E5%9B%BE)

# 还需阅读
- [Designing for Multiple Screens](https://developer.android.com/training/multiscreen/index.html)
- [Providing Resources](https://developer.android.com/guide/topics/resources/providing-resources.html)
- [Iconography design guide](https://developer.android.com/design/style/iconography.html)

# 概述
Android 主要使用两种属性给设备的屏幕分类：尺寸和密度。app 可能被安装到屏幕在尺寸和密度两方面区间的设备中。因此，应该包含可选资源来优化 app 对屏幕的不同尺寸、密度的表现。

- 四种大概的尺寸：
    - small, 
    - normal, 
    - large, 
    - xlarge
- 四种大概的密度：
    - low (ldpi), 
    - medium (mdpi), 
    - high (hdpi), 
    - extra high (xhdpi)

为声明你希望用于不同屏幕的不同布局和位图，你必须将这些可选资源放在不同的目录中，类似你放置不同语言的字符串。

注意屏幕方向（横向或纵向）的改变同样被认为是一种屏幕尺寸的变化，所以许多 app 应该修正每个方向的布局来优化用户体验。

# 创建不同布局
为优化用户在不同屏幕尺寸上的体验，应该为每个你要支持的屏幕尺寸创建特定的布局 XML 文件。每个布局应该保存到合适的资源文件夹中，文件夹命名上添加一个 `-<screen_size>` 后缀。如，large 屏幕的特定布局应该保存在 `res/layout-large/` 文件夹下。

> 笔记：Android 自动缩放你的布局来更好地搭配屏幕。因此，针对不同屏幕尺寸的布局不用关心 UI 元素的绝对尺寸，而是关注影响用户体验的布局结构（如重要的 view 相对于其兄弟 view 的尺寸和位置）。

例如，下面的项目包好了一个默认的布局和一个为 large 屏幕准备的可选布局。

```
MyProject/
    res/
        layout/
            main.xml
        layout-large/
            main.xml
```

文件名必须相同，但它们的内容不同，以此来提供对应屏幕尺寸的优化 UI。

和往常一样在 app 中引用布局文件

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.main);
}
```

依据 app 运行的设备的屏幕尺寸，系统加载适合布局文件夹下的布局文件。更多关于 Android 选择合适资源的信息，见：[Providing Resources](https://developer.android.com/guide/topics/resources/providing-resources.html#BestMatch) 

另一个例子，一个为横向（landscape orientation）屏幕提供可选布局的项目：

```
MyProject/
    res/
        layout/
            main.xml
        layout-land/
            main.xml
```

默认情况下，`layout/main.xml` 文件用于纵屏。

如果你想为横屏，包括大屏横屏提供特定布局，则需要同时使用 `large`、`land` 两个修饰符：

```
MyProject/
    res/
        layout/              # 默认 (纵屏)
            main.xml
        layout-land/         # 横屏
            main.xml
        layout-large/        # large (纵屏)
            main.xml
        layout-large-land/   # large 横屏
            main.xml
```

> 笔记：Android 3.2 及以上版本支持定义屏幕尺寸的高级方法，允许您根据 DIP 指定最小宽度和高度的屏幕，为其指定资源。本课不含这种新技术，更多信息请见：[Designing for Multiple Screens](https://developer.android.com/training/multiscreen/index.html)

# 创建不同的位图

应该始终提供能适当缩放到每个大概密度的位图资源：low, medium, high and extra-high 密度。这有助于在所有屏幕密度上实现良好的图形质量和性能。

为生成这些图片，你应该从矢量格式的原始资源开始，并使用以下尺寸比例生成各个密度的图像：
- xhdpi: 2.0
- hdpi: 1.5
- mdpi: 1.0 (baseline)
- ldpi: 0.75

这意味着如果你为 xhdpi 的设备生成 200\*200 的图片，你应该为 hdpi 生成 150\*150 的相同内容图片，为 mdpi 生成 100\*100，为 ldpi 的设备生成 75\*75。

之后，将文件放在合适的可绘制资源文件夹中：
```
MyProject/
    res/
        drawable-xhdpi/
            awesomeimage.png
        drawable-hdpi/
            awesomeimage.png
        drawable-mdpi/
            awesomeimage.png
        drawable-ldpi/
            awesomeimage.png
```

任何时刻你引用 `@drawable/awesomeimage`，系统依据屏幕密度选择合适的位图。

> 笔记：ldpi 资源不总是必须的。当你提供了 hdpi 的资源，系统将其缩小一半来适配 ldpi 的屏幕。

更多关于创建图标资源的技巧和原则，见：[Iconography design guide](https://developer.android.com/design/style/iconography.html)。