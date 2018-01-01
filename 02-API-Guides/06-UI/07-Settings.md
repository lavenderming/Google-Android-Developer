参见：[Settings](https://developer.android.com/guide/topics/ui/settings.html)

- [核心类](#%E6%A0%B8%E5%BF%83%E7%B1%BB)
- [另见](#%E5%8F%A6%E8%A7%81)
- [概述](#%E6%A6%82%E8%BF%B0)
- [概览](#%E6%A6%82%E8%A7%88)
    - [Preferences](#preferences)
- [在 XML 中定义 Preference](#%E5%9C%A8-xml-%E4%B8%AD%E5%AE%9A%E4%B9%89-preference)
    - [创建设置组](#%E5%88%9B%E5%BB%BA%E8%AE%BE%E7%BD%AE%E7%BB%84)
        - [使用组标题](#%E4%BD%BF%E7%94%A8%E7%BB%84%E6%A0%87%E9%A2%98)
        - [使用子屏幕](#%E4%BD%BF%E7%94%A8%E5%AD%90%E5%B1%8F%E5%B9%95)
    - [使用 intent](#%E4%BD%BF%E7%94%A8-intent)
- [创建 Perference Activity](#%E5%88%9B%E5%BB%BA-perference-activity)
- [使用 Preference Fragments](#%E4%BD%BF%E7%94%A8-preference-fragments)

# 核心类
- [Preference](https://developer.android.com/reference/android/preference/Preference.html)
- [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html)
- [PreferenceFragment](https://developer.android.com/reference/android/preference/PreferenceFragment.html)

# 另见
- [Settings design guide](https://developer.android.com/design/patterns/settings.html)

# 概述

app 通常包含允许用户更改功能和行为的设置。例如，一些 app 允许用户指定是否打开通知以及指定 app 多久与云端同步一次数据。

如果你想为你的 app 提供设置功能，你应该使用 Android 的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) API 来构建，这使它和其它 Android app（包括系统设置）有一致的界面体验。本文描述如何使用 [Preference](https://developer.android.com/reference/android/preference/Preference.html) API 构建你的 app 设置。

> **设置设计**
>
> 关于如何设计你的设置，见：[Settings](https://developer.android.com/design/patterns/settings.html) 设计指南。

![图1. 来自 Android 信息 app 的设置截图。选择一个通过 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 定义的 item 打开一个界面来更改设置](https://developer.android.com/images/ui/settings/settings.png)

# 概览
并非使用 [View](https://developer.android.com/reference/android/view/View.html) 对象来构建用户界面，设置通过在 XML 文件中声明各种各样的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 类的子类。

一个 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象是构建一条设置的构建块。每个 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 表现为设置列表中的一个 item，并向用户提供合适的 UI 来更改设置。例如，一个 [CheckBoxPreference](https://developer.android.com/reference/android/preference/CheckBoxPreference.html) 创建一个显示 checkbox 的列表 item，一个 [ListPreference](https://developer.android.com/reference/android/preference/ListPreference.html) 创建一个能打开对话框、对话框中显示一列选项的 item。

你添加的每个 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 都有对应的键值对，系统使用这些键值对把你 app 的设置保存在默认的 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件中。当用户更改设置时，系统自动更新 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件中的对应值。唯一你需要直接同关联的 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件交互的地方在你需要读取文件中的值来决定你 app 的行为的时候。

每个保存在 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件中的设置值可以是如下数据类型：
- Boolean
- Float
- Int
- Long
- String
- String [Set](https://developer.android.com/reference/java/util/Set.html)

由于你的 app 设置的 UI 是用 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象构建的，你需要使用一个特殊的 [Activity](https://developer.android.com/reference/android/app/Activity.html) 或 [Fragment](https://developer.android.com/reference/android/app/Fragment.html) 子类来显示这一列的设置：
- 如果你的 app 支持 Android 3.0 以前的版本（API 10 或更低），你必须构建一个 [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 类的子类。

- 在 Android 3.0 以及之后的版本，你应该使用普通的 [Activity](https://developer.android.com/reference/android/app/Activity.html)，该 Activity 内容纳了一个显示 app 设置的 [PreferenceFragment](https://developer.android.com/reference/android/preference/PreferenceFragment.html)。当你有多组设置时，你还可以用 [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 为大屏创建一个双面板的布局。

如何创建你的 [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 以及实例化一个 [PreferenceFragment](https://developer.android.com/reference/android/preference/PreferenceFragment.html) 在 [Creating a Preference Activity](https://developer.android.com/guide/topics/ui/settings.html#Activity) 和 [Using Preference Fragments](https://developer.android.com/guide/topics/ui/settings.html#Fragment) 部分讨论。

## Preferences
app 的每个设置由 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 类的某个特定子类表示。每个子类都包含允许你自定义的一组核心属性，如设置的标题以及默认值等。同样，每个子类还包含其特有的属性和用户界面。例如，下图展示了来自信息 app 的设置截图。设置列表中的每个 item 都是一个不同的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象。

一些常用的 preference：

- [CheckBoxPreference](https://developer.android.com/reference/android/preference/CheckBoxPreference.html)

    显示一个带 checkbox 的 item，用于不是启用就是禁用的设置。保存的值类型是 boolean（启用即为 `true`）。

- [ListPreference](https://developer.android.com/reference/android/preference/ListPreference.html)

    打开一个带有一列 radio button 的对话框。保存值的类型可以是支持的任意类型（前文已列出）。

- [EditTextPreference](https://developer.android.com/reference/android/preference/EditTextPreference.html)

    打开一个带有 [EditText](https://developer.android.com/reference/android/widget/EditText.html) 部件的对话框。保存值的类型是 [String](https://developer.android.com/reference/java/lang/String.html)。

所有其它子类以及它们对应的属性，见：[Preference](https://developer.android.com/reference/android/preference/Preference.html) 类。

当然，內建类不能满足你的所有需求，你的 app 可能需要更特别类。例如，当前的平台没有提供一个用于挑选数字或日期的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 类。所以你可能需要定义自己的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 子类。如需帮助，见：[Building a Custom Preference](https://developer.android.com/guide/topics/ui/settings.html#Custom)。

# 在 XML 中定义 Preference
尽管你可以在运行时实例化新的 [Preference](https://developer.android.com/reference/android/preference/Preference.html)，你仍应该依据 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象的层级结构在 XML 文件中定义你的一列设置。更推荐用一个 XML 文件定义你的一堆设置是因为 XML 文件提供了容易阅读且易于更新的结构。此外，你 app 的设置通常是预先确定的，尽管你仍可以在运行时修改多个设置。

每个 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 子类都可以用与类名相匹配的 XML 元素来声明，如：`<CheckBoxPreference>`。

你必须把 XML 文件保存到 `res/xml` 文件夹中。尽管你可以任意命名文件名，但它通常被命名为 `preferences.xml`。你通常只需要一个文件，因为层次结构中的分支（能打开他们自己的设置列表）用内嵌的 [PreferenceScreen](https://developer.android.com/reference/android/preference/PreferenceScreen.html) 类实例声明。

> 笔记：如果你想为你的设置创建多面板的布局，那么你需要分离 xml 文件，为各个 fragment 准备单独的 xml。

XML 文件的根节点必须是一个 [`<PreferenceScreen>`](https://developer.android.com/reference/android/preference/PreferenceScreen.html) 元素。在该元素内你添加各个 [Preference](https://developer.android.com/reference/android/preference/Preference.html)。在 [`<PreferenceScreen>`](https://developer.android.com/reference/android/preference/PreferenceScreen.html) 元素内的每个子元素都会显示为设置列表中的一个 item。

例如：
```xml
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <CheckBoxPreference
        android:key="pref_sync"
        android:title="@string/pref_sync"
        android:summary="@string/pref_sync_summ"
        android:defaultValue="true" />
    <ListPreference
        android:dependency="pref_sync"
        android:key="pref_syncConnectionType"
        android:title="@string/pref_syncConnectionType"
        android:dialogTitle="@string/pref_syncConnectionType"
        android:entries="@array/pref_syncConnectionTypes_entries"
        android:entryValues="@array/pref_syncConnectionTypes_values"
        android:defaultValue="@string/pref_syncConnectionTypes_default" />
</PreferenceScreen>
```

在上述例子中，有一个 [CheckBoxPreference](https://developer.android.com/reference/android/preference/CheckBoxPreference.html) 和一个 [ListPreference](https://developer.android.com/reference/android/preference/ListPreference.html)。两者都有如下属性：

- `android:key`
    
    该属性对要持久保存数据值的 preference 来说是必须的。它是系统用来在 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 中保存设置值的键。

    有几种情况无需该属性：
    - preference 是 [PreferenceCategory](https://developer.android.com/reference/android/preference/PreferenceCategory.html) 或 [PreferenceScreen](https://developer.android.com/reference/android/preference/PreferenceScreen.html) 时
    - preference 通过 [`<intent>`](https://developer.android.com/guide/topics/ui/settings.html#Intents) 元素指定了 [Intent](https://developer.android.com/reference/android/content/Intent.html) 或通过 [android:fragment](android:fragment) 属性指定了 [Fragment](https://developer.android.com/reference/android/app/Fragment.html) 来显示。

- `android:title`

    该属性提供了用户可见的设置名称

- `android:defaultValue`

    该属性指定了系统应在 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件中设置的初始值。应该对所有的设置提供默认值。

更多关于其它支持的属性，见：[Preference](https://developer.android.com/reference/android/preference/Preference.html)（以及各自子类）文档。

当你的设置列表超过 10 项时，你可能想定义一组的设置并给它们添加名称，或是把各组显示在不同的屏幕上。下面的章节介绍了这鞋做法。

## 创建设置组

如果你列出超过 10 项的设置，用户可能难以查找浏览。你可以通过将其中的全部或一些分组，将长列表分为多个短列表。一组相关设置可以通过下面两种方式来展现：
- [使用组标题](https://developer.android.com/guide/topics/ui/settings.html#Titles)
- [使用子屏幕](https://developer.android.com/guide/topics/ui/settings.html#Subscreens)

你可以任意搭配上述分组技术来组织你的设置项。在决定使用哪种以及如何分组设置项时，你应该依据 Android 设计的 [Settings](https://developer.android.com/design/patterns/settings.html) 指南。

### 使用组标题

如果你想分隔不同组并在分隔处提供组标题（如下图），你可以把各组的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象分别放在 [PreferenceCategory](https://developer.android.com/reference/android/preference/PreferenceCategory.html) 内。

![图2. 带名称的设置类别。1.类别通过 [`<PreferenceCategory>`](https://developer.android.com/reference/android/preference/PreferenceCategory.html) 元素指定。2.名称由 `android:title` 属性指定](https://developer.android.com/images/ui/settings/settings-titles.png)

比如：
```xml
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <PreferenceCategory
        android:title="@string/pref_sms_storage_title"
        android:key="pref_key_storage_settings">
        <CheckBoxPreference
            android:key="pref_key_auto_delete"
            android:summary="@string/pref_summary_auto_delete"
            android:title="@string/pref_title_auto_delete"
            android:defaultValue="false"... />
        <Preference
            android:key="pref_key_sms_delete_limit"
            android:dependency="pref_key_auto_delete"
            android:summary="@string/pref_summary_delete_limit"
            android:title="@string/pref_title_sms_delete"... />
        <Preference
            android:key="pref_key_mms_delete_limit"
            android:dependency="pref_key_auto_delete"
            android:summary="@string/pref_summary_delete_limit"
            android:title="@string/pref_title_mms_delete" ... />
    </PreferenceCategory>
    ...
</PreferenceScreen>
```

### 使用子屏幕

如果你想把一组设置放到子屏幕中（如下如图），把同组的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象放到 [PreferenceScreen](https://developer.android.com/reference/android/preference/PreferenceScreen.html) 内。

![图3.设置子屏幕。用 `<PreferenceScreen>` 元素创建一个设置项，当该项被点击是，另外打开一屏幕显示一列它内嵌的设置项](https://developer.android.com/images/ui/settings/settings-subscreen.png)

例如：
```xml
<PreferenceScreen  xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 打开一个子屏幕显示设置项 -->
    <PreferenceScreen
        android:key="button_voicemail_category_key"
        android:title="@string/voicemail"
        android:persistent="false">
        <ListPreference
            android:key="button_voicemail_provider_key"
            android:title="@string/voicemail_provider" ... />
        <!-- opens another nested subscreen -->
        <PreferenceScreen
            android:key="button_voicemail_setting_key"
            android:title="@string/voicemail_settings"
            android:persistent="false">
            ...
        </PreferenceScreen>
        <RingtonePreference
            android:key="button_voicemail_ringtone_key"
            android:title="@string/voicemail_ringtone_title"
            android:ringtoneType="notification" ... />
        ...
    </PreferenceScreen>
    ...
</PreferenceScreen>
```

## 使用 intent

在某些情况下，你可能想让一个 preference 项打开一个 activity 而不是设置页面，比如一个 web 浏览器来浏览网页。 为使用户点击设置项时能触发 [Intent](https://developer.android.com/reference/android/content/Intent.html)，你需要在 `<Preference>` 元素内添加一个 `<intent>` 元素。

例如，下面展示了如何使用 preference 项打开网页：
```xml
<Preference android:title="@string/prefs_web_page" >
    <intent android:action="android.intent.action.VIEW"
            android:data="http://www.example.com" />
</Preference>
```

你可以用下列属性创建隐式或显示的 intent：

- `android:action`

    设置 intent 要执行的 action，类似 [setAction()](https://developer.android.com/reference/android/content/Intent.html#setAction(java.lang.String)) 方法。

- `android:data`

    设置 intent 的 data，类似 [setData()](https://developer.android.com/reference/android/content/Intent.html#setData(android.net.Uri)) 方法。

- `android:mimeType`

    设置 intent 的 MIME 类型，类似 [setType()](https://developer.android.com/reference/android/content/Intent.html#setType(java.lang.String)) 方法。

- `android:targetClass`

    填写组件名的类名部分，类似 [setComponent()](https://developer.android.com/reference/android/content/Intent.html#setComponent(android.content.ComponentName)) 方法。

- `android:targetPackage`

    填写组件名的包名部分，类似 [setComponent()](https://developer.android.com/reference/android/content/Intent.html#setComponent(android.content.ComponentName)) 方法。

> **笔记：** 你必须使用字符串字面值作为这些 intent 属性的值。你不能使用资源字符串来定义属性值，如：`@string/foo`。

# 创建 Perference Activity

为在 activity 中显示你的设置项，你需要创建一个继承 [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 类的类。[PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 是普通 [Activity](https://developer.android.com/reference/android/app/Activity.html) 类的子类，它能基于 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 对象的层次结构显示一列的设置项。当用户更改设置项的值时，[PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 能自动关联到对应的 [Preference](https://developer.android.com/reference/android/preference/Preference.html) 上。

> **笔记：** 如果你为 Android 3.0 以及其后的系统开发 app，你应该使用 [PreferenceFragment](https://developer.android.com/reference/android/preference/PreferenceFragment.html)。前往下一节查看如何 [使用 Preference Fragments](#%E4%BD%BF%E7%94%A8-preference-fragments)

最重要的事是在 [onCreate()](https://developer.android.com/reference/android/preference/PreferenceActivity.html#onCreate(android.os.Bundle)) 回调函数中不要加载 view 布局。而应该调用 [addPreferencesFromResource()](https://developer.android.com/reference/android/preference/PreferenceActivity.html#addPreferencesFromResource(int)) 向 activity 中添加你定义在 XML 文件中的 preferences。

例如，以下是使用 [PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) 所需的最少代码：

```java
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
```

这些代码对一些 app 来讲已经足够，因为一旦用户更改了 preference，系统会立马把更改保存到默认的 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件，其它组件需要检测用户的设置时就可以读取该文件。然而还有许多 app 需要一些额外的代码来监听 preferences 的更改。更多关于监听 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件更改的信息，见：[读取 preferences](https://developer.android.com/guide/topics/ui/settings.html#ReadingPrefs)

# 使用 Preference Fragments


