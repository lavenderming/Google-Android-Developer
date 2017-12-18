参见：[Run Your App](https://developer.android.google.cn/training/basics/firstapp/running-app.html)

- [在真实设备中运行](#%E5%9C%A8%E7%9C%9F%E5%AE%9E%E8%AE%BE%E5%A4%87%E4%B8%AD%E8%BF%90%E8%A1%8C)
- [在模拟器中运行](#%E5%9C%A8%E6%A8%A1%E6%8B%9F%E5%99%A8%E4%B8%AD%E8%BF%90%E8%A1%8C)

在[上一课](\Create-an-Android-Project.md)，你创建了一个显示 `Hello World` 的 Android 项目。你现在可以在真实设备或模拟器中运行该 app。

# 在真实设备中运行
如下设置你的设备：

1. 将设备通过 USB 连接到你的开发机。若你在 Windows 中开发，你可能需要为你的设备[安装适合的 USB 驱动](https://developer.android.google.cn/studio/run/oem-usb.html)

2. 通过如下步骤开启**开发者选项**中的 **USB 调试**：
    
    1. 打开**系统设置**
    1. 选择**关于手机**
    1. 滑到底部点击**版本号** 7 下
    1. 返回上一界面找到底部附近的**开发者选项**
  
    打开**开发者选项**，下滑找到并打开**USB调试**

如下设置在设备上运行 app：

1. 在 Android Studio 中，点击 Project 窗口的 app 模块，之后点击 **Run > Run**（或点击工具栏的 **Run** ![](https://developer.android.google.cn/studio/images/buttons/toolbar-run.png) 按钮）
1. 在 **Select Deployment Target** 窗口，选择你的设备，点击 **OK**。
![](https://developer.android.google.cn/training/basics/firstapp/images/run-device_2x.png)

Android Studio 将 app 安装到连接的设备上并启动 app。

这就是在设备上运行“hello world”！要继续开发，继续[下一课](/Build-a-Simple-User-Interface.md)

# 在模拟器中运行

如下设置在模拟器上运行 app：

1. 在 Android Studio 中，点击 Project 窗口的 app 模块，之后点击 **Run > Run**（或点击工具栏的 **Run** ![](https://developer.android.google.cn/studio/images/buttons/toolbar-run.png) 按钮）
1. 在 **Select Deployment Target** 窗口，点击 **Create New Virtual Device**。
![](https://developer.android.google.cn/training/basics/firstapp/images/run-avd_2x.png)
1. 在 **Select Hardware** 页，选择一个手机设备，如 Pixel，之后点击 **Next**。
1. 在 **System Image** 页，选择 API 等级最高的版本。如果你还没有已安装的版本，会出现 **Download** 连接，点击该连接完成下载。
1. 点击 **Next**。
1. 在 **Android Virtual Device (AVD)** 页，保持默认设置并点击 **Finish**
1. 返回 **Select Deployment Target** 窗口，选择你刚创建的设备并点击 **OK**

Android Studio 将 app 安装到模拟器上并启动 app。

这就是在模拟器上运行“hello world”！要继续开发，继续[下一课](/Build-a-Simple-User-Interface.md)

