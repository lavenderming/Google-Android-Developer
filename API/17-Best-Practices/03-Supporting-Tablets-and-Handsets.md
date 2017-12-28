参见：[Supporting Tablets and Handsets](https://developer.android.com/guide/practices/tablets-and-handsets.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [基本指南](#%E5%9F%BA%E6%9C%AC%E6%8C%87%E5%8D%97)

# 概述
Android 平台运行在各种屏幕尺寸上，且 Android 系统能优雅地调整你应用的 UI 来适应这些尺寸。通常，你需要做的只是设计灵活的 UI 以及通过提供 [替代资源](https://developer.android.com/guide/topics/resources/providing-resources.html#AlternativeResources) 优化一些元素在不同尺寸上的表现（如重新定位一些 view 的替代布局或 view 的替代尺寸值）。然而，有时你可能想进一步优化对不同屏幕尺寸的全面用户体验。例如，平板提供了更多空间所以你的 app 可以同时提供多组信息；而手机往往需要你将各组信息分别显示。所以即使为手机设计的 UI 可以适当地拉伸以契合平板，但这种设计没能完全利用平板屏幕的潜力来加强用户体验。

在 Android 3.0（API 级别 11）中，Android 引入了一组新的框架 API，这些 API 帮助你设计更高效利用大屏的 activity：[Fragment](https://developer.android.com/reference/android/app/Fragment.html) API。Fragments 允许你将 UI 中不同的行为的组件分隔到不同部分，这样，运行在平板上时可以在一个 activity 中组合它们以创建多面板布局，运行在手机上时可以将它们分别放置于不同的 activity。Android 3.0 还引入了 [ActionBar](https://developer.android.com/reference/android/app/ActionBar.html)，它提供了一个位于屏幕顶部的 UI 来区别 app、还提供了用户功能（action）和导航。

这份文档提供了帮助你创建使用 fragment 和 action bar 来优化在平板和手机端用户体验的 app 的指南。

在继续这份指南前，很重要的一点是你已阅读了 [Supporting Multiple Screens](https://developer.android.com/guide/practices/screens_support.html) 指南。该文档中描述了一些基础设计原则，这些原则分别介绍了如何通过灵活的布局和一些可替代的图片开发支持不同屏幕尺寸、分辨率的 UI。

# 基本指南