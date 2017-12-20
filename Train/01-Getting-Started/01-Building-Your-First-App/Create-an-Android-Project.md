参见：[Create an Android Project](https://developer.android.google.cn/training/basics/firstapp/creating-project.html)

该课向你展示如何通过 [Android Studio](https://developer.android.google.cn/studio/index.html) 创建一个新的 Android 项目并介绍项目中的一些文件。

1. 在 **Welcome to Android Studio** 窗口，点击 **Start a new Android Studio project**。

![](https://developer.android.google.cn/training/basics/firstapp/images/studio-welcome_2x.png)

如果你已经打开了一个项目，点击 **File > New Project**。

2. 在 **New Project** 页，输入如下值：

- Application Name: "My First App"
- Company Domain: "example.com"

你可能想更改 **project location**，但请保存其它选项如上。

3. 点击 **Next**。

4. 在 **Target Android Devices** 页，保持值选项并点击 **Next**。

5. 在 **Add an Activity to Mobile** 页，选择 **Empty Activity** 并点击 **Next**。

6. 在 **Configure Activity** 页，保持默认值并点击 **Finish**。

稍等一会，Android Studio 打开 IDE。

![](https://developer.android.google.cn/training/basics/firstapp/images/studio-editor_2x.png)

现在，花些时间查看一些重要的文件。

首先，确保 **Project** 小窗口已打开（点击 View > Tool Windows > Project）且该小窗口顶部下拉列表中的 **Android** 页已选择。之后，你可以查看下列文件：

- **app > java > com.example.myfirstapp > MainActivity.java**

这是 main activity（app 的 entry point）。当构建并运行 app，系统启动该 [Activity](https://developer.android.google.cn/reference/android/app/Activity.html) 的一个实例并加载它的布局。

- **app > res > layout > activity_main.xml**

该 XML 文件定义了 activity 的 UI 布局。其包含一个带有 `Hello world!` 文字的 [TextView](https://developer.android.google.cn/reference/android/widget/TextView.html) 元素

- **Gradle Scripts > build.gradle**

你会看到有两个文件都叫这名：一个文件是项目的，另一个则是 `app` 模块的。每个模块都有其自己的 `build.gradle` 文件，但该项目目前只有一个模块。你将主要使用模块的 `build.gradle` 文件来配置配置 Gradle 工具如何编译和构建你的 app。更多关于该文件的信息，见：[Configure Your Build](https://developer.android.google.cn/studio/build/index.html)

为运行该 app，继续[下一课](Run-Your-App.md)。












