- [问题](#%E9%97%AE%E9%A2%98)
    - [如何解决 `Frameworks detected: Android framework is detected in the project Configure`](#%E5%A6%82%E4%BD%95%E8%A7%A3%E5%86%B3-frameworks-detected-android-framework-is-detected-in-the-project-configure)

# 心得
## onCreate 与 onStart 各自作用
从这个项目中看，onCreate 主要用于创建布局（包括各种各样的小部件）而 onStart 主要用于向各种 view 中添加内容。

# 问题
## 如何解决 `Frameworks detected: Android framework is detected in the project Configure`

这个项目来自 Google 为 fragment 提供的样例项目。项目通过 `FragmentBasics.zip` 提供，或许是由于项目过于久远，当时的开发 IDE 并非 Android Studio，由 `FragmentBasics.zip` 解压出的 `FragmentBasics` 文件夹没法作为 AS 项目直接打开运行，会提示：`Frameworks detected: Android framework is detected in the project Configure`。

参考了：[Frameworks detected in android studio](https://stackoverflow.com/questions/33231460/frameworks-detected-in-android-studio)，我发现可以通过 [bemzkie](https://stackoverflow.com/users/4473155/bernzkie) 回答的方法解决这一问题，即不直接打开项目，而是通过 **File > New Project > Import Project** 来打开。这样打开项目，Android Studio 会提示你选择一个新的路径，它会在新路径里创建一份原项目的拷贝，这也是这个文件夹的由来。
