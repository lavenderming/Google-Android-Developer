参见：[Saving Files](https://developer.android.google.cn/training/data-storage/files.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [选择内部或外部存储](#%E9%80%89%E6%8B%A9%E5%86%85%E9%83%A8%E6%88%96%E5%A4%96%E9%83%A8%E5%AD%98%E5%82%A8)
- [获取外部存储权限](#%E8%8E%B7%E5%8F%96%E5%A4%96%E9%83%A8%E5%AD%98%E5%82%A8%E6%9D%83%E9%99%90)
- [在内部存储中保存文件](#%E5%9C%A8%E5%86%85%E9%83%A8%E5%AD%98%E5%82%A8%E4%B8%AD%E4%BF%9D%E5%AD%98%E6%96%87%E4%BB%B6)
- [把文件保存到外部存储](#%E6%8A%8A%E6%96%87%E4%BB%B6%E4%BF%9D%E5%AD%98%E5%88%B0%E5%A4%96%E9%83%A8%E5%AD%98%E5%82%A8)
- [查询可用空间](#%E6%9F%A5%E8%AF%A2%E5%8F%AF%E7%94%A8%E7%A9%BA%E9%97%B4)
- [删除文件](#%E5%88%A0%E9%99%A4%E6%96%87%E4%BB%B6)

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
当向内部存储保存文件时，你可以通过调用下列两种方法之一获取适合的目录，目录作 [File](https://developer.android.com/reference/java/io/File.html) 类型返回：

- [getFilesDir()](https://developer.android.com/reference/android/content/Context.html#getFilesDir()) 

    返回一个表示你 app 内部存储目录的 [File](https://developer.android.com/reference/java/io/File.html)

- [getCacheDir()](https://developer.android.com/reference/android/content/Context.html#getCacheDir()) 

    返回 [File](https://developer.android.com/reference/java/io/File.html) 对象，该对象表示内部存储中用于保存你 app 缓存文件的目录。确保在文件不再需要时删除且在你使用任何时间限定一个合理的大小限制，如 1MB。当系统存储低时，系统会没有提示地删除缓存文件。

为在这些文件夹中创建文件，你可以使用 [File()](https://developer.android.com/reference/java/io/File.html#File(java.io.File,java.lang.String)) 构造函数，第一个参数传入由上面方法创建的内部存储目录，第二个方法指定要创建的文件名：

```java
File file = new File(context.getFilesDir(), filename);
```

此外，你也可以调用 [Context](https://developer.android.com/reference/android/content/Context.html) 的 [openFileOutput()](https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,int)) 来获取内部存储中文件的 [FileOutputStream](https://developer.android.com/reference/java/io/FileOutputStream.html) 并向其中写入数据。例如，下面的例子展示了如何向文件中写入一些文本：

```java
String filename = "myfile";
String string = "Hello world!";
FileOutputStream outputStream;

try {
    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
    outputStream.write(string.getBytes());
    outputStream.close();
} catch (Exception e) {
    e.printStackTrace();
}
```

或者你需要缓存一些文件，你可以使用 [createTempFile()](https://developer.android.com/reference/java/io/File.html#createTempFile(java.lang.String,java.lang.String))。例如，下面的方法从 [URL](https://developer.android.com/reference/java/net/URL.html) 中提取文件名，然后用该文件名在内部缓存文件夹中创建文件：

```java
public File getTempFile(Context context, String url) {
    File file;
    try {
        String fileName = Uri.parse(url).getLastPathSegment();
        file = File.createTempFile(fileName, null, context.getCacheDir());
    } catch (IOException e) {
        // 创建文件过程中的错误
    }
    return file;
}
```

> **笔记：** 你 app 的内部存储目录在 android 文件系统的特殊位置，由你 app 的包名生成。技术上讲，如果你把文件模式设为 `readable`，则其它 app 可以读取你的内部存储中的文件；当然，则需要其他 app 知道你 app 的包名以及文件名。除非你显式设置文件为可读或可写，否则其它 app 不能浏览你的内部文件夹以及没有你文件的读写权限。所以只要你对你内部存储中的文件使用 [MODE_PRIVATE](https://developer.android.com/reference/android/content/Context.html#MODE_PRIVATE)，它就不会被其它 app 访问。

# 把文件保存到外部存储
由于外部存储可能无法使用 —— 比如当存储连接到 PC 或移除提供外部存储的 SD 卡 —— 你应该在每次访问它前检查它是否可用。你可以通过调用 [Environment](https://developer.android.com/reference/android/os/Environment.html) 的 [getExternalStorageState()](https://developer.android.com/reference/android/os/Environment.html#getExternalStorageState()) 查询外部存储的状态，如果它的返回值等于 [MEDIA_MOUNTED](https://developer.android.com/reference/android/os/Environment.html#MEDIA_MOUNTED)，那么你可以读取或写入文件。例如，你可以用下面的方法检测外部存储是否可用：

```java
/* 检测外部存储是否可读也可写 */
public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
        return true;
    }
    return false;
}

/* 检测外部存储至少可读 */
public boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        return true;
    }
    return false;
}
```

尽管外部存储会被用户或其它 app 改变，但仍有两类的文件你可以保存在这：

- 公开文件
    
    可以自由被其它 app 和用户使用的文件。当用户卸载你的 app，这些文件仍然对用户可用。

    例如，由你的 app 获取的图片或其它下载的文件。

- 私有文件

    那些属于你 app 且在用户卸载 app 后应该被删除的文件。尽管这些文件由于保存于外部存储，技术上可以被用户以及其它 app 访问，但它们不会为你 app 外的其它使用提供价值。当用户卸载你的 app 后，系统删除所有你 app 外部私有目录中的文件。

    例如，由你 app 下载的额外资源或临时媒体文件。

如果你想在外部存储上保存公共文件，使用 [Environment](https://developer.android.com/reference/android/os/Environment.html) 的 [getExternalStoragePublicDirectory()](https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)) 方法获取外部存储上的适合文件夹，文件夹以 [File](https://developer.android.com/reference/java/io/File.html) 类型返回。该方法接收一个指定你想保存的文件的类型的参数，比如 [DIRECTORY_MUSIC](https://developer.android.com/reference/android/os/Environment.html#DIRECTORY_MUSIC) 或 [DIRECTORY_PICTURES](https://developer.android.com/reference/android/os/Environment.html#DIRECTORY_PICTURES)，据此，文件可以和其它同类型公共文件保存在一起。例如：

```java
public File getAlbumStorageDir(String albumName) {
    // 在用户公共图片目录下创建一个目录
    File file = new File(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), 
        albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
```

若没有符合你要求的预定义子文件夹，你还可以调用 [getExternalFilesDir()](https://developer.android.com/reference/android/content/Context.html#getExternalFilesDir(java.lang.String)) 并传入 `null`。这返回 app 在外部存储的私有根目录。

切记调用 [getExternalFilesDir()](https://developer.android.com/reference/android/content/Context.html#getExternalFilesDir(java.lang.String)) 创建的文件夹的父文件夹在用户卸载 app 时会被删除。如果你要保存的文件在用户卸载 app 后仍然可用 —— 比如你的 app 是相机 app 且用户希望在卸载后保留照片 —— 那你应该使用 [getExternalStoragePublicDirectory()](https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String))

不管调用 [getExternalStoragePublicDirectory()](https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)) 保存可以共享的文件还是调用 [getExternalFilesDir()](https://developer.android.com/reference/android/content/Context.html#getExternalFilesDir(java.lang.String)) 保存 app 私有的文件，你的文件夹名都应该使用由 API 提供的常量，比如：[DIRECTORY_PICTURES](https://developer.android.com/reference/android/os/Environment.html#DIRECTORY_PICTURES)。这些文件夹名确保文件被系统以合适的方式对待。例如，保存在 [DIRECTORY_RINGTONES](https://developer.android.com/reference/android/os/Environment.html#DIRECTORY_RINGTONES) 文件夹下的文件会被系统多媒体浏览器归类为铃声而不是音乐。

# 查询可用空间

如果你可以提前知道你需要保存多少数据，你可以通过调用 [getFreeSpace()](https://developer.android.com/reference/java/io/File.html#getFreeSpace()) 或 [getTotalSpace()](https://developer.android.com/reference/java/io/File.html#getTotalSpace()) 计算是否有足够的可用容量而不导致 [IOException](https://developer.android.com/reference/java/io/IOException.html)。这俩方法分别提供了当前可用容量以及存储总容量。这些信息对于防止存储高于某个阈值也是有用的。

但是系统不保证你可以写入 [getFreeSpace()](https://developer.android.com/reference/java/io/File.html#getFreeSpace()) 返回所代表的字节的数据。如果返回的数字比你想保存的数据大几 MB，或文件系统还没超过 90%，那么保存数据应该是安全的。否则，你应该不写入数据。

> **笔记：** 你不需要在保存文件前检测可用容量。你可以直接写入文件，然后 `catch` 可能产生的 [IOException](https://developer.android.com/reference/java/io/IOException.html)。比如你不知道你到底需要多少空间的时候。具体来说，如果你想在保存文件前改变文件的编码，比如把 `PNG` 的图片转为 `JPEG` 格式，在完成前你无法知道文件的大小。

# 删除文件
你应该总是把你不再需要的文件删除。最直接的删除方式是获取文件的引用后直接调用其上的 [delete()](https://developer.android.com/reference/java/io/File.html#delete())。
```java
myFile.delete();
``` 

如果文件保存在内部存储，你还可以通过 [Context](https://developer.android.com/reference/android/content/Context.html) 的 [deleteFile()](https://developer.android.com/reference/android/content/Context.html#deleteFile(java.lang.String)) 定位并删除文件：

```java
myContext.deleteFile(fileName);
```

> **笔记：** 当用户卸载你的 app 时，系统会删除如下文件：
> - 所有你保存在内部存储中的文件
> - 所有你通过 [getExternalFilesDir()](https://developer.android.com/reference/android/content/Context.html#getExternalFilesDir(java.lang.String)) 保存在外部存储中的东西。
>
> 但你仍应该人为地定期删除通过 [getCacheDir()](https://developer.android.com/reference/android/content/Context.html#getCacheDir()) 创建的缓存文件，并且还应该定期删除你不再需要的文件。


