主要用于记录该项目中遇到的问题以及解决方法。

主要还参考了郭霖的《第一行代码——Android（第二版）》 6.4 节

- [问题](#%E9%97%AE%E9%A2%98)
    - [如何更改创建文件时自动生成的作者名](#%E5%A6%82%E4%BD%95%E6%9B%B4%E6%94%B9%E5%88%9B%E5%BB%BA%E6%96%87%E4%BB%B6%E6%97%B6%E8%87%AA%E5%8A%A8%E7%94%9F%E6%88%90%E7%9A%84%E4%BD%9C%E8%80%85%E5%90%8D)
    - [如何查看应用创建的数据库文件](#%E5%A6%82%E4%BD%95%E6%9F%A5%E7%9C%8B%E5%BA%94%E7%94%A8%E5%88%9B%E5%BB%BA%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E6%96%87%E4%BB%B6)
    - [没法打开 /data 文件夹怎么办](#%E6%B2%A1%E6%B3%95%E6%89%93%E5%BC%80-data-%E6%96%87%E4%BB%B6%E5%A4%B9%E6%80%8E%E4%B9%88%E5%8A%9E)

# 问题

## 如何更改创建文件时自动生成的作者名
使用 Android Studio 创建文件时会自动生成如下语句
```java
/**
 * Created by XX on XXXX/XX/XX.
 */
```
此处自动生成的注释默认使用的创建者名称是读取当前系统登录用户的用户名，由于该代码要放在 GitHub 上，所以当前系统登录用户的用户名不太符合我的需要。

经过查找，发现：[Change Author template in Android Studio](https://stackoverflow.com/questions/21160288/change-author-template-in-android-studio)

简单说就是在工具栏的 File -> Settings -> File and Code Templates -> Includes -> File Header 处更改。

但是，直接设置用户名还是不符合我的需求，我的 Android Studio 还要做别的事，而做别的事的时候需要生成系统登录用户的用户名，每次往 GitHub 上放东西的时候都来这改又太麻烦，我发现这里面的设置似乎是通过一门语言来控制的，然后我在描述的最后看到了这句话：

> [Apache Velocity](http://velocity.apache.org/engine/devel/user-guide.html#Velocity_Template_Language_VTL:_An_Introduction) template language is used

搜了 Velocity，大概了解这里使用的是 Velocity 语言。又阅读了描述，发现 Android Studio 有提供 `${PACKAGE_NAME}` 这个变量，该变量的内容为文件所在的包的包名。

想法就是获取包名字符串的前xx位，依据包名设置不同的用户名。可我从没用过 Velocity，不过没关系，我又找到了——这篇博客 [velocity 获取字符串变量的长度](http://blog.csdn.net/mggwct/article/details/51799117) 

于是，更改 File Header 为：

```c
#if ($PACKAGE_NAME.substring(0,22) == "io.github.lavenderming")
#set( $USER = "阿懂")
#end

/**
 * Created by ${USER} on ${DATE}.
 */
```

It works! 其它关于 Velocity 的学习就留待之后了，毕竟已经在此耽搁太多时间，该进入项目了。

## 如何查看应用创建的数据库文件
见：[View contents of database file in Android Studio](https://stackoverflow.com/questions/17529766/view-contents-of-database-file-in-android-studio) 

上面的文章介绍了 Android Studio 中应该到哪里去查看应用创建的数据库

由于我第一次运行软件时使用的是实体设备，而我没有实体设备的 root 权限，而我也不想 root 我的实体设备，所以查看应用的数据库失败。

所以我选择了模拟器，开始时选择 Pixel XL，装了 API 25 的系统。然后使用时发现 boom...

我没法进入 data 文件夹，于是...

## 没法打开 /data 文件夹怎么办
见：[Android Studio DDMS can't open /data folder in an emulator phone](https://stackoverflow.com/questions/44453922/android-studio-ddms-cant-open-data-folder-in-an-emulator-phone)

依稀记得这个问题似乎在我学习 Udacity 的 Android 入门课程时出现过，当时的解决方法忘了。

这里，我选择了给模拟器机子安装 API 23 的系统，bingo！

