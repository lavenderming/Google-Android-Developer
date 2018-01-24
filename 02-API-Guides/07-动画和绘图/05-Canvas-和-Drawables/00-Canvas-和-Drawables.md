参见：[Canvas and Drawables](https://developer.android.com/guide/topics/graphics/2d-graphics.html#drawables)

- [概述](#%E6%A6%82%E8%BF%B0)

# 概述

Android 框架提供了一组二维绘制 API 让你将自定义的图形绘制到 canvas 上或修改已有的 view 的外观。你通常通过以下两种方式绘制 2D 图形：

- 在布局的 [View](https://developer.android.com/reference/android/view/View.html) 对象上绘制你的图形或动画。选择这种方式，系统的渲染管线处理你的图形 —— 你的责任是定义 view 内的图形。
- 在 [Canvas](https://developer.android.com/reference/android/graphics/Canvas.html) 对象上绘制你的图形。选择这种方式，你需要将你的 canvas 传入适合类的 [onDraw(Canvas)](https://developer.android.com/reference/android/view/View.html#onDraw(android.graphics.Canvas)) 方法。你还可以使用 [Canvas](https://developer.android.com/reference/android/graphics/Canvas.html) 的绘制方法。该方式还让你能控制任何动画。

在 view 上绘制适用于绘制简单的、不会动态变化的图形，且不是如游戏 app 那样性能紧张的 app。比如，当你想在静态 app 中显示静态图形或预定义的动画时，应该绘制在 view 上。更多信息，见 [Drawables](https://developer.android.com/guide/topics/graphics/2d-graphics.html#drawables)。

当你的 app 需要规律性自行重绘时，在 canvas 上绘制是更好的选择。如视频游戏类 app，应该绘制到 canvas 上。不过，这有多种方式来达成这点：

- 在 app 的主线程中，当你创建布局中的自定义 view 组件时，调用 [invalidate()](https://developer.android.com/reference/android/view/View.html#invalidate()) 并处理 [onDraw(Canvas)](https://developer.android.com/reference/android/view/View.html#onDraw(android.graphics.Canvas)) 回调。
- 在管理 [SurfaceView](https://developer.android.com/reference/android/view/SurfaceView.html) 的工作线程，使用 canvas 绘制方法。无需调用 `invalidate()`
