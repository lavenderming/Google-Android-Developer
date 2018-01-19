参见：[Fragments](https://developer.android.com/guide/components/fragments.html)

- [关键类](#%E5%85%B3%E9%94%AE%E7%B1%BB)
- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [设计哲学](#%E8%AE%BE%E8%AE%A1%E5%93%B2%E5%AD%A6)
- [创建一个 Fragment](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA-fragment)
- [暂跳](#%E6%9A%82%E8%B7%B3)
- [处理 fragment 生命周期](#%E5%A4%84%E7%90%86-fragment-%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F)

# 关键类
- [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html)
- [FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html)
- [FragmentTransaction](https://developer.android.com/reference/android/support/v4/app/FragmentTransaction.html)

# 还需阅读
- [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)
- [Handling Lifecycles with Lifecycle-Aware Components](https://developer.android.com/topic/libraries/architecture/lifecycle.html)
- [Guide to App Architecture](https://developer.android.com/topic/libraries/architecture/guide.html)
- [Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html)

# 概述
一个 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 在一个 [Activity](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html) 中作行为（behavior）或者部分 UI。可以在单个 Activity 中组合多个 Fragment 来创建一个多面板的 UI，也可以在多个 Activity 中重复使用某个 Fragment。你可以将 Fragment 视为 Activity 一个子模块，它有自己的生命周期、接收自己的输入事件、并且可以在运行期间增加或移除（有点像可以在不同 activity 中重复使用的“小 activity”）。

Fragment 必须处于某个 Activity 之内，且 Fragment 的生命周期直接受它所在的 Activity 的生命周期的影响。如，当 Activity 进入 Paused 状态，Activity 里的所有 Fragment 也进入 Paused 状态；当 Activity 被销毁，Activity 里的所有 Fragment 也被销毁。当 Activity 正在运行（处于 resumed [生命周期状态](https://developer.android.com/guide/components/activities.html#Lifecycle)），你可以独立地操纵各个 Fragment，比如添加或移除它们。同样可以将诸如此类的 Fragment 事务（指添加或移除等操作）添加到由 Activity 管理的 back stack——activity back stack 内的每条记录都表示一次已发生的 Fragment 事务。back stack 允许用户通过返回键回退 Fragment 事务（类似浏览器中的后退）。

当将 Fragment 作为 Activity 布局的一部分添加时，Fragment 存在于 Activity 视图层次的一个 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 内，且 Fragment 定义了自己的 view 布局。在 Activity 中插入 Fragment 有两种方式：一种是在 Activity 的布局文件中用 `<fragment>` 元素声明 Fragment；另一种是通过代码将 Fragment 添加到一个已存在的 [ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) 中。然而，Fragment 不是一定要成为 Activity 布局的一部分，你可以使用没有布局的 Fragment 作为 Activity 中一个不可见的 worker。

更多关于管理生命周期的信息，另见：[Handling Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle.html)。

# 设计哲学
Android 在 Android 3.0（API 级别 11）时引进了 Fragment，首要目的是在大屏设备中支持更动态灵活的 UI 设计，因为如平板等大屏设备中有更多空间去结合和交换 UI 组件，Fragment 使这类设计无需管理视图层次结构的复杂变化。通过将 Activity 的布局分隔成 Fragment，你可以在运行时修改 Activity 的外观，并在由 Activity 管理的 back stack 中保存这些修改。现在，通过 [fragment 支持库](https://developer.android.com/topic/libraries/support-library/packages.html#v4-fragment) 可以在更早的 Android 版本中使用 Fragments。

例如：一个新闻 app 使用一个 Fragment 在左侧显示文章列表并在右侧的另一个 Fragment 中显示一篇文章的内容——两个 Fragment 并排处于同一个 Activity，并且每个 Fragment 都有自己自定义的一套生命周期回调函数，处理它们各自的用户输入事件。因此，无需用一个 Activity 选择文章，另一个 Activity 读取文章，用户可以在同一个 Activity 中选择并读取该文章，如图：

![图1.](https://developer.android.com/images/fundamentals/fragments.png)

应该将每个 Fragment 设计为模块并能被 Activity 组件重复使用。由于每个 Fragment 都定义了自己的布局、且通过自己的生命周期回调函数定义了自己的行为，所以你可以在多个 Activity 内包含一个 Fragment。因此，Fragments 的设计应该以重复使用为目的并且避免在一个 Fragment 直接操纵另一个 Fragment。这非常重要，因为模块化的 Fragment 可以让你在不同的屏幕大小上使用不同的 Fragment 组合。当你的 app 要支持不同屏幕，你可以基于屏幕空间在不同的布局配置中重复使用你的 fragment 来优化用户体验。例如，在手机中，多个 fragment 可能不适合在同一个 activity 中，因此分离 fragment 以提供单面板的 UI 是必要的。

依旧以上述的新闻 app 举例，当 app 运行于平板大小的设备时，app 可以在一个 Activity 中内嵌两个 Fragment；但在手机大小的设备中时，可以使用一个 Activity，该 Activity 内嵌显示文章列表的 Fragment，当点击文章列表中的具体某篇文章时，启动另一个 Activity，内嵌读取具体文章内容的 Fragment。因此通过在不同的组合中重复使用 fragment，app 既支持平板，也支持手机。

更多关于为不同屏幕配置设计不同 fragment 组合的 app 的信息，见：[Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html) 指南。

# 创建一个 Fragment

> ![](https://developer.android.com/images/fragment_lifecycle.png)
> 图2.fragment 的生命周期（当 fragment 的 activity 运行时）

为创建 Fragment，必须继承 [Fragment](https://developer.android.google.cn/reference/android/app/Fragment.html) 类（或继承 Fragment 类的已有子类）。 [Fragment](https://developer.android.google.cn/reference/android/app/Fragment.html) 类的代码看起来有点像 [Activity](https://developer.android.google.cn/reference/android/app/Activity.html) 类。它包含了与 Activity 类相似的回调函数，例如：[onCreate()](https://developer.android.google.cn/reference/android/app/Fragment.html#onCreate(android.os.Bundle))、[onStart()](https://developer.android.google.cn/reference/android/app/Fragment.html#onStart())、[onPause()](https://developer.android.google.cn/reference/android/app/Fragment.html#onPause())、[onStop()](https://developer.android.google.cn/reference/android/app/Fragment.html#onStop())。事实上，如果将已有的 app 改为使用 Fragment，可能只要简单地将原本 Activity 回调函数中的代码移动到对应的 Fragment 回调函数中去即可。

通常，创建 Fragment 至少需要实现以下几个生命周期函数：

- [onCreate()](https://developer.android.google.cn/reference/android/app/Fragment.html#onCreate(android.os.Bundle))

    当系统创建 Fragment 时调用该函数。在你的实现中，该函数应该初始化 Fragment 的核心组件，这些核心组件在 Fragment 进入 Paused 或 Stopped 后，再进入 resumed 状态可以保留。

- [onCreateView()](https://developer.android.google.cn/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)) 

    当 Fragment 要第一次绘制自己的 UI 时，系统调用该回调函数。为绘制 UI 该函数必须返回一个 [View](https://developer.android.google.cn/reference/android/view/View.html) 作为你的 Fragment 布局的根。若 Fragment 不需要提供 UI，可以返回 null。

- [onPause()](https://developer.android.google.cn/reference/android/app/Activity.html#onPause())

    系统调用该函数表明用户离开了该 Fragment（这不意味着该 Fragment 一定会被销毁）。这通常是你提交需要保存的用户更改的地方（因为用户可能不再回来了）。

大多数 app 的每个 fragment 都应该至少实现这三个方法，但这仍有一些其它回调方法你可以用来处理 fragment 生命周期的各种状态。所有生命周期回调方法都会在 [Handling the Fragment Lifecycle](https://developer.android.com/guide/components/fragments.html#Lifecycle) 中作更具体的讨论。

# 暂跳

# 处理 fragment 生命周期

> ![](https://developer.android.com/images/activity_fragment_lifecycle.png)
> 图3. activity 生命周期对 fragment 生命周期的影响。



