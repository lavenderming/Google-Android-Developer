参见：[Saving Files](https://developer.android.google.cn/training/data-storage/files.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [选择内部或外部存储](#%E9%80%89%E6%8B%A9%E5%86%85%E9%83%A8%E6%88%96%E5%A4%96%E9%83%A8%E5%AD%98%E5%82%A8)
- [获取外部存储权限](#%E8%8E%B7%E5%8F%96%E5%A4%96%E9%83%A8%E5%AD%98%E5%82%A8%E6%9D%83%E9%99%90)
- [在内部存储中保存文件](#%E5%9C%A8%E5%86%85%E9%83%A8%E5%AD%98%E5%82%A8%E4%B8%AD%E4%BF%9D%E5%AD%98%E6%96%87%E4%BB%B6)

# 还需阅读
- [Using the Internal Storage](https://developer.android.google.cn/guide/topics/data/data-storage.html#filesInternal)
- [Using the External Storage](https://developer.android.google.cn/guide/topics/data/data-storage.html#filesExternal)

# 概述
Android 使用的文件系统类似于其它平台的基于磁盘的文件系统。本节课讲述如何使用 [File](https://developer.android.google.cn/reference/java/io/File.html) API 通过 Android 文件系统读取与写入文件。

[File](https://developer.android.google.cn/reference/java/io/File.html) 对象适合于读取或写入大量、按从始至终顺序、且没有跳跃的数据。例如，图片或通过网络传输的任何东西。

本节课展示如何在 app 中执行基本的文件相关的任务。课程假设你熟悉基础的 Linux 文件系统与 [java.io](https://developer.android.google.cn/reference/java/io/package-summary.html) 中的标准文件输入/输出 API。

# 选择内部或外部存储
所有的 Android 设备都有两块文件存储区域：“内部”存储与“外部”存储。这俩名称来自早期的 Android，当时大多数设备都提供了内置的非易失性存储器（内部存储）以及诸如 micro SD卡（外部存储）之类的可移动存储介质。一些设备将永久存储空间分成“内部”和“外部”分区，所以即使没有可移动存储介质，还是总会有两个存储空间，且不论外部存储是否可移动，API 的行为都是一样的。

下面的列表总结了各个存储空间的情况：

**内部存储**

- 总是可用
- 保存在这里的文件只能被你的 app 访问
- 当用户卸载你的 app，系统会从内部存储中移除所有你 app 的文件

    当你想确保不论用户还是其它 app 都不能访问你的文件时，使用内部存储。

**外部存储**

- 不总是可用，因为用户可以将 USB 存储作为外部存储安装并且在某些情况下从设备移除。
- 它是全局可读的，所以保存在这里的文件可以在你控制之外读取。
- 当用户卸载你的 app 时，系统只会删除你保存在通过 [getExternalFilesDir()](https://developer.android.google.cn/reference/android/content/Context.html#getExternalFilesDir(java.lang.String)) 获取到的目录中的 app 文件。

    外部存储是保存以下目的文件的好地方：
    - 文件无需访问控制
    - 你想与其他 app 分享的文件
    - 用户可以通过电脑访问的文件

> **笔记：** 在 Android 7.0（API 24）前，内部文件可以被通过解除文件系统的权限让其它应用访问，现在这种方法已经无效。如果你想让内部文件的内容能被其它 app 访问，你的 app 可以使用 [FileProvider](https://developer.android.google.cn/reference/android/support/v4/content/FileProvider.html)。见：[Sharing Files](https://developer.android.google.cn/training/secure-file-sharing/index.html)。

> **小技巧：** 尽管 app 默认被装入内部存储，你可以设置 app manifest 文件的 [android:installLocation](https://developer.android.google.cn/guide/topics/manifest/manifest-element.html#install) 属性让你的 app 可以被安装到外部存储。当 APK 大小非常大且用户有较大的外部存储时，用户会喜欢这种设定。更多信息，见：[App Install Location](https://developer.android.google.cn/guide/topics/data/install-location.html)

# 获取外部存储权限

要像外部存储写入数据，你必须在你的 [manifest file](https://developer.android.google.cn/guide/topics/manifest/manifest-intro.html) 文件中申请 [WRITE_EXTERNAL_STORAGE](https://developer.android.google.cn/reference/android/Manifest.permission.html#WRITE_EXTERNAL_STORAGE) 权限：

```xml
<manifest ...>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    ...
</manifest>
```

> **注意：** 当前，所有 app 都可以读取外部存储中的文件而无需特定权限。然而，这种情况会在未来的发行版中改变。如果你的 app 需要读取外部存储（但不写入数据），那么你需要声明 [READ_EXTERNAL_STORAGE](https://developer.android.google.cn/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE) 权限。为确保你的 app 能继续如预期般工作，你应该现在，在变更发生前，声明该权限。
>    ```xml
>    <manifest ...>
>        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
>        ...
>    </manifest>
>    ```
> 如果你使用了 [WRITE_EXTERNAL_STORAGE](https://developer.android.google.cn/reference/android/Manifest.permission.html#WRITE_EXTERNAL_STORAGE) 权限，它同时隐含了有读取外部存储的权限

你无需任何权限来把文件保存到内部存储。你的 app 总是有权限在其内部存储目录中读取与写入文件。

# 在内部存储中保存文件



