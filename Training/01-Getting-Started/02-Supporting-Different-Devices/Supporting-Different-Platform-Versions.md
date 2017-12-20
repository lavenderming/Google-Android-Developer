参见：[Supporting Different Platform Versions](https://developer.android.com/training/basics/supporting-devices/platforms.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [指定最低和目标 API 级别](#%E6%8C%87%E5%AE%9A%E6%9C%80%E4%BD%8E%E5%92%8C%E7%9B%AE%E6%A0%87-api-%E7%BA%A7%E5%88%AB)
- [在运行时检测系统版本](#%E5%9C%A8%E8%BF%90%E8%A1%8C%E6%97%B6%E6%A3%80%E6%B5%8B%E7%B3%BB%E7%BB%9F%E7%89%88%E6%9C%AC)
- [使用平台样式和主题](#%E4%BD%BF%E7%94%A8%E5%B9%B3%E5%8F%B0%E6%A0%B7%E5%BC%8F%E5%92%8C%E4%B8%BB%E9%A2%98)

# 还需阅读
- [Android API Levels](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels)
- [Android Support Library](https://developer.android.com/tools/support-library/index.html)

# 概述
尽管最新版本的 Android 常提供出色的 API，你仍应该支持旧版本的 Android 直到更多设备更新。本课程教你如何使用最新 API 的同时继续支持旧版。

[平台版本](http://developer.android.com/about/dashboards/index.html) 的 dashboard 会定期更新以展示已激活设备在不同的 Android 版本的比例，数据基于访问 Google Play Store 的设备。通常，支持 90% 以上的激活设备，且目标版本为最新版本。

> 建议：为了在多个Android版本中提供最佳的功能，应该在应用中使用 [Android Support Library](https://developer.android.com/tools/support-library/index.html)，这使您可以在旧版本上使用最近几个新平台的 API。

# 指定最低和目标 API 级别
[AndroidManifest.xml](https://developer.android.com/guide/topics/manifest/manifest-intro.html) 文件描述了 app 的详细情况以及指明 app 支持的 Android 版本。特别是 [\<uses-sdk>](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html) 元素的 `minSdkVersion` 和 `targetSdkVersion` 属性，分别定义了 app 适配的最低 API 级别和设计与测试 app 的最高 API 级别。

例如：
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" ... >
    <uses-sdk android:minSdkVersion="4" android:targetSdkVersion="15" />
    ...
</manifest>
```

当新版 Android 发布，一些样式和行为可能改变。为允许你的 app 利用这些更改以及确保你的 app 匹配各个用户设备的样式，你应该设定 [targetSdkVersion](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html#target) 值为最新的 Android 版本。

# 在运行时检测系统版本
Android 在 [Build](https://developer.android.com/reference/android/os/Build.html) 常量类中为每个平台版本提供了唯一的代码。在 app 中使用这些代码来构建条件，以确保只有当这些 API 在系统上可用时，才会执行更高 API 级别的代码。

```java
private void setUpActionBar() {
    // 确保 app 运行于 Honeycomb 或更高版本才能使用 ActionBar 的 API
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
```

> 笔记：在解析 XML 文件时，Android 忽略当前设备不支持的 XML 属性。所以你可以放心使用仅在新版本中支持的 XML 属性，无需当心旧版本遇到这些代码时会发生 breaking。例如，如果你设置 `targetSdkVersion="11"`，app 默认在 Android 3.0（API 11）或更高版本中包含 [ActionBar](https://developer.android.com/reference/android/app/ActionBar.html)。然后为在 action bar 上添加 menu item，需要在 menu 的 XML 资源文件中设置 `android:showAsAction="ifRoom"`。在跨版本的 XML 文件中这样做是可行的，因为旧版的 Android 会忽略 `showAsAction` 属性（即你无需在 `res/menu-v11/` 文件夹下弄一个特殊版本）。

# 使用平台样式和主题
Android 提供了一些统一用户体验的主题，这些主题给予 app 和底层操作系统一致的外观和体验。这些主题可以在 manifest 文件中应用到 app上。通过使用这些內建的样式和主题，你的应用将自动地跟随新的 Android 发行版的外观和体验。

使 activity 看起来像对话框：
```xml
<activity android:theme="@android:style/Theme.Dialog">
```

使 activity 有透明的背景
```xml
<activity android:theme="@android:style/Theme.Translucent">
```

为应用定义在 `/res/values/styles.xml` 文件中的自定义主题：
```xml
<activity android:theme="@style/CustomTheme">
```

为使主题应用到整个 app，在 [\<application>](https://developer.android.com/guide/topics/manifest/application-element.html) 元素中添加 `android:theme` 属性：
```xml
<application android:theme="@style/CustomTheme">
```

跟多关于如何创建和使用主题，见：[Styles and Themes](https://developer.android.com/guide/topics/ui/themes.html)