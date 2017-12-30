- [心得](#%E5%BF%83%E5%BE%97)
    - [onCreate 与 onStart 各自作用](#oncreate-%E4%B8%8E-onstart-%E5%90%84%E8%87%AA%E4%BD%9C%E7%94%A8)
- [问题](#%E9%97%AE%E9%A2%98)
    - [如何解决 `Frameworks detected: Android framework is detected in the project Configure`](#%E5%A6%82%E4%BD%95%E8%A7%A3%E5%86%B3-frameworks-detected-android-framework-is-detected-in-the-project-configure)
    - [如何修改 Android Studio 创建项目时在项目根目录自动生成的 `.gitignore` 文件](#%E5%A6%82%E4%BD%95%E4%BF%AE%E6%94%B9-android-studio-%E5%88%9B%E5%BB%BA%E9%A1%B9%E7%9B%AE%E6%97%B6%E5%9C%A8%E9%A1%B9%E7%9B%AE%E6%A0%B9%E7%9B%AE%E5%BD%95%E8%87%AA%E5%8A%A8%E7%94%9F%E6%88%90%E7%9A%84-gitignore-%E6%96%87%E4%BB%B6)

# 心得
## onCreate 与 onStart 各自作用
从这个项目中看，onCreate 主要用于创建布局（包括各种各样的小部件）而 onStart 主要用于向各种 view 中添加内容。

# 问题
## 如何解决 `Frameworks detected: Android framework is detected in the project Configure`

这个项目来自 Google 为 fragment 提供的样例项目。项目通过 `FragmentBasics.zip` 提供，或许是由于项目过于久远，当时的开发 IDE 并非 Android Studio，由 `FragmentBasics.zip` 解压出的 `FragmentBasics` 文件夹没法作为 AS 项目直接打开运行，会提示：`Frameworks detected: Android framework is detected in the project Configure`。

参考了：[Frameworks detected in android studio](https://stackoverflow.com/questions/33231460/frameworks-detected-in-android-studio)，我发现可以通过 [bemzkie](https://stackoverflow.com/users/4473155/bernzkie) 回答的方法解决这一问题，即不直接打开项目，而是通过 **File > New Project > Import Project** 来打开。这样打开项目，Android Studio 会提示你选择一个新的路径，它会在新路径里创建一份原项目的拷贝，这也是这个文件夹的由来。

## 如何修改 Android Studio 创建项目时在项目根目录自动生成的 `.gitignore` 文件
Android Studio 自动创建的 `.gitignore` 文件不错，但可能不符合自己的需求，于是我找到了：[一劳永逸修改 Android Studio .gitignore 默认模板](http://blog.csdn.net/ziwang_/article/details/78498578)

方法如下：

找到 Android Studio 的安装地址，依次打开 **`\plugins\android\lib\templates\gradle-projects\NewAndroidProject\root\`**，找到 `project_ignore` 文件并打开，看到了熟悉的内容。对，该文件的内容就是根目录下自动生成的 `.gitignore` 的内容。

比如我当时是按默认把 Android Studio 装在 C 盘了，我这份文件的路径就是：`C:\Program Files\Android\Android Studio\plugins\android\lib\templates\gradle-projects\NewAndroidProject\root\project_ignore`。

然后要替换其内容，参考：[What should be in my .gitignore for an Android Studio project?](https://stackoverflow.com/questions/16736856/what-should-be-in-my-gitignore-for-an-android-studio-project)，我将其替换为如下内容：
```sh
#-------- 原生 .gitignore start -------

#*.iml
#.gradle
#/local.properties
#/.idea/workspace.xml
#/.idea/libraries
#.DS_Store
#/build
#/captures
#.externalNativeBuild

#-------- 原生 .gitignore end --------

#-------- 自定义 .gitignore start ----
#----参考：https://stackoverflow.com/questions/16736856/what-should-be-in-my-gitignore-for-an-android-studio-project

#built application files
*.apk
*.ap_

# files for the dex VM
*.dex

# Java class files
*.class

# generated files
bin/
gen/

# 本地配置文件 (sdk 路径等)
local.properties

# Windows thumbnail db
Thumbs.db

# OSX 文件
.DS_Store

# Android Studio
*.iml
.idea
#.idea/workspace.xml - 移除 # 并删掉 .idea 如果你觉得能更符合你的需求.
.gradle
build/
.navigation
captures/
output.json 

#NDK
obj/
.externalNativeBuild

#-------- 自定义 .gitignore end ----
```
到此，修改完成！