参见：[Supporting Different Languages and Cultures](https://developer.android.com/training/basics/supporting-devices/languages.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [创建区域文件夹与资源文件](#%E5%88%9B%E5%BB%BA%E5%8C%BA%E5%9F%9F%E6%96%87%E4%BB%B6%E5%A4%B9%E4%B8%8E%E8%B5%84%E6%BA%90%E6%96%87%E4%BB%B6)

# 还需阅读
- [Localization Checklist](https://developer.android.com/distribute/tools/localization-checklist.html)

- [Localization with Resources](https://developer.android.com/guide/topics/resources/localization.html)

- [App Translation Service](https://support.google.com/l10n/answer/6359997)

- [Bidirectional text](https://en.wikipedia.org/wiki/Bi-directional_text)

# 概述
App 可以包含针对特定文化的资源。例如，app 可以包含指定文化的字符串，该字符串依据当前区域的不同翻译成不同语言。让特定文化的资源与 app 的其它部分分离是种良好的实践。Android 可以基于系统区域设置解析特定语言与特定文化的资源。可以在项目中使用资源文件夹来支持不同的区域。

你可以特定资源来适应用户的文化。你可以提供适合用户语言与文化的任何[资源类型](https://developer.android.com/guide/topics/resources/available-resources.html)。例如，下面的截图中展示了某个 app 在设备默认区域（`en_US`）和西班牙区域（`es_ES`）显示的字符串和图片。

![图1.app 基于当前区域使用不同资源](https://developer.android.com/images/training/languages_01.png)

如果你通过 Android SDK 工具（见：[Create an Android Project](https://developer.android.com/training/basics/firstapp/creating-project.html)）创建你的项目，该工具会在项目的顶层创建一个 `res/` 文件夹。在 `res/` 文件夹内有对不同类型资源的子文件夹。里面同样有一些默认的文件如用来保存字符串值的 `res/values/strings.xml`。

支持不同的语言不只是单纯地使用指定区域的资源。一些用户选择的语言习惯的显示方式是从右到左（RTL）的，比如阿拉伯语或希伯来语。还有些用户可能会选择从右到左显示内容，即使它们使用的语言通常是从左到右显示，比如英语。为支持这两种用户，你的 app 应该：

- 在 RTL 区域使用 RTL UI
- 检测并声明格式化信息中显示的文本的方向。通常，你只要调用[一个方法](https://developer.android.com/training/basics/supporting-devices/languages.html#FormatTextExplanationSolution)来决定你文本数据的方向

# 创建区域文件夹与资源文件
为增加对更多区域的支持，在 `res/` 文件夹下添加子文件夹。每个子文件夹的命名需遵循如下格式：

`<resource type>-b+<language code>[+<country code>]`

例如，`values-b+es/` 文件夹内包含了语言代码为 `es` 地区的字符串资源。类似的，`mipmap-b+es+ES/` 包含了为语言代码为 `es` 且地区代码为 `ES` 的区域准备的图标。Android 在运行时按照设置中的区域加载适合的资源。更多信息，见：[Providing Alternative Resources](https://developer.android.com/guide/topics/resources/providing-resources.html#AlternativeResources)。

在你决定支持某区域后，创建资源子文件夹和文件，例如：
```sh
MyProject/
    res/
       values/
           strings.xml
       values-b+es/
           strings.xml
       mipmap/
           country_flag.png
       mipmap-b+es+ES/
           country_flag.png
```

例如，下面是一些对不同语言的不同文件：
英语字符串（默认区域），`/values/strings.xml`：
```xml
<resources>
    <string name="hello_world">Hello World!</string>
</resources>
```
西班牙语字符串（`es` 区域），`/values-es/strings.xml`：
```xml
<resources>
    <string name="hello_world">¡Hola Mundo!</string>
</resources>
```
美国国旗图标（默认区域）
`/mipmap/country_flag.png`：
![](https://developer.android.com/images/training/languages_us_flag.png)
