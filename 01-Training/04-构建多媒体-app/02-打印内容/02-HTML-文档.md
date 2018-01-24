参见：[Printing HTML Documents](https://developer.android.com/training/printing/html-docs.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [加载 HTML 文档](#%E5%8A%A0%E8%BD%BD-html-%E6%96%87%E6%A1%A3)
- [创建打印作业](#%E5%88%9B%E5%BB%BA%E6%89%93%E5%8D%B0%E4%BD%9C%E4%B8%9A)

# 概述

在 Android 中打印不只是一张简单照片的内容时需要编写由文本和图片组成的打印文档。Android 框架提供的一种编写方式是通过 HTML，且这种方法可以用最少的代码将其打印出来。

在 Android 4.4（API level 19）中，[WebView](https://developer.android.com/reference/android/webkit/WebView.html) 类升级为可以打印 HTML 内容。该类允许你加载本地 HTML 资源或从网络下载网页、创建打印作业并将其发送到 Android 的打印服务。

本课程展示如何快速构建一个包含文本和图片的 HTML 文档并使用 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 来打印它。

# 加载 HTML 文档

通过 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 打印 HTML 文档包括加载 HTML 资源或构建 HTML 文档字符串。本节介绍如何构建 HTML 字符串并将其加载到 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 打印。

这个 view 对象通常作为 activity 布局的一部分。但是如果你的 app 不使用 [WebView](https://developer.android.com/reference/android/webkit/WebView.html)，你仍可以特地创建一个该类的实例来打印。创建这个自定义打印 view 的主要步骤如下：

- 创建一个 [WebViewClient](https://developer.android.com/reference/android/webkit/WebViewClient.html) ，用于加载完 HTML 资源后开始打印作业。
- 把 HTML 资源加载到 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 对象中。

下面的代码示例展示如何创建一个简单的 [WebViewClient](https://developer.android.com/reference/android/webkit/WebViewClient.html) 并加载凭空创建的 HTML 文档：

```java
private WebView mWebView;

private void doWebViewPrint() {
    // 创建一个打印专用的 WebView 对象
    WebView webView = new WebView(getActivity());
    webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "页面加载结束 " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
    });

    // 凭空生成 HTML 文档：
    String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
            "testing, testing...</p></body></html>";
    webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

    // 保存一个对 WebView 对象的引用，直到你将 PrintDocumentAdapter
    // 传到 PrintManager
    mWebView = webView;
}
```

> **笔记：** 确保你调用创建打印作业的方法在 [WebViewClient](https://developer.android.com/reference/android/webkit/WebViewClient.html) 的 [onPageFinished()](https://developer.android.com/reference/android/webkit/WebViewClient.html#onPageFinished(android.webkit.WebView,%20java.lang.String)) 方法内。如果你不等到页面加载结束，打印结果可能会不完整或是空白，或直接就打印失败。

> **笔记：** 上面的示例代码保存了一个 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 对象的引用，因此在打印作业创建完成前它不会被垃圾回收。确保你在自己的实现中也这样做，否则打印进程可能失败。

如果你想在页面中包含图片，将图片文件放在项目的 `assets/` 目录下然后在 [loadDataWithBaseURL()](https://developer.android.com/reference/android/webkit/WebView.html#loadDataWithBaseURL(java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String)) 方法的第一个参数中设定，如下所示：

```java
webView.loadDataWithBaseURL("file:///android_asset/images/", htmlBody,
        "text/HTML", "UTF-8", null);
```

你还可以将 [loadDataWithBaseURL()](https://developer.android.com/reference/android/webkit/WebView.html#loadDataWithBaseURL(java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String)) 替换为 [loadUrl()](https://developer.android.com/reference/android/webkit/WebView.html#loadUrl(java.lang.String)) 来加载要打印的网页，如下所示：

```java
// 打印已有网页 (记住要申请 INTERNET 权限!):
webView.loadUrl("http://developer.android.com/about/index.html");
```

当使用 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 来创建打印文档时，你应该注意以下限制：

- 你不能给文档添加页眉、页脚、页码。
- HTML 文档的打印参数不含设置答应页面范围，例如：打印总共 10 页的 HTML 文档的 2~4 页，这种是不支持的
- 一个 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 实例一次只能执行一个打印作业。
- 包含CSS打印属性（如横向属性）的HTML文档不受支持。
- 无法使用 HTML 文档中的 JavaScript 来触发打印。

> **笔记：** 包含在布局中的 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 对象在加载完后也能打印。

如果你想创建更自定义的打印结果并完整控制打印页面的内容绘制，请跳到下一课：[Printing a Custom Document](https://developer.android.com/training/printing/custom-docs.html)

# 创建打印作业

在 [WebView](https://developer.android.com/reference/android/webkit/WebView.html) 加载完你的 HTML 文档后，app 部分的打印流程已接近完成。下一步是访问 [PrintManager](https://developer.android.com/reference/android/print/PrintManager.html)，创建打印适配器，并最终创建打印作业。下面的示例展示如何执行这些步骤：

```java
private void createWebPrintJob(WebView webView) {

    // 获得 PrintManager 实例
    PrintManager printManager = (PrintManager) getActivity()
            .getSystemService(Context.PRINT_SERVICE);

    // 获得打印适配器实例
    PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

    // 通过名字和适配器实例创建打印作业
    String jobName = getString(R.string.app_name) + " Document";
    PrintJob printJob = printManager.print(jobName, printAdapter,
            new PrintAttributes.Builder().build());

    // 保存作业对象以便后续状态检查
    mPrintJobs.add(printJob);
}
```

该示例在 app 中保存了 [PrintJob](https://developer.android.com/reference/android/print/PrintJob.html) 对象的实例，这并非必须。你的 app 可以通过该对象来跟踪打印作业执行的进度。当你想在 app 内监测打印作业的状态（完成、失败或用户取消）时，这种方法很有用。无需创建 app 内的通知，因为打印框架会自动为打印作业创建系统通知。
