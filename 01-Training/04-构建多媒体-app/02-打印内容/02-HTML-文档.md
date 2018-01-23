参见：[Printing HTML Documents](https://developer.android.com/training/printing/html-docs.html)

- [概述](#%E6%A6%82%E8%BF%B0)

# 概述

在 Android 中打印不只是一张简单照片的内容时需要编写由文本和图片组成的打印文档。Android 框架提供的一种编写方式是通过 HTML，且这种方法可以用最少的代码将其打印出来。

在 Android 4.4（API level 19）中，[WebView](https://developer.android.com/reference/android/webkit/WebView.html) 类升级为可以打印 HTML 内容。该类允许你加载本地 HTML 资源或从网络下载网页、创建打印作业并将其发送到 Android 的打印服务。

本课程展示如何快速构建一个包含文本和图片的 HTML 文档并使用 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 来打印它。


