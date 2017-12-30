参见：[Saving Key-Value Sets](https://developer.android.com/training/data-storage/shared-preferences.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [概述](#%E6%A6%82%E8%BF%B0)
- [获取 SharedPreferences 的句柄](#%E8%8E%B7%E5%8F%96-sharedpreferences-%E7%9A%84%E5%8F%A5%E6%9F%84)
- [向 Shared Preferences 中写入数据](#%E5%90%91-shared-preferences-%E4%B8%AD%E5%86%99%E5%85%A5%E6%95%B0%E6%8D%AE)
- [从 Shared Preferences 中读取数据](#%E4%BB%8E-shared-preferences-%E4%B8%AD%E8%AF%BB%E5%8F%96%E6%95%B0%E6%8D%AE)

# 还需阅读
- [Using Shared Preferences](https://developer.android.com/guide/topics/data/data-storage.html#pref)

# 概述
如果你有一小组相关联的键值对需要保存，你应该使用 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) API。一个 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 对象指向一个包含键值对的文件，且提供了一些简单的方法来读取和写入这些键值对。每个 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 文件都由系统框架管理，且可私有可共享。

本节教你如何使用 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) API 来保存以及获取简单值。

> 笔记：[SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) API 只用来读取和写入键值对，不要把它和 [Preference](https://developer.android.com/reference/android/preference/Preference.html) API 混淆，后者帮助你创建 app 的用户界面（尽管后者使用 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 作为保存设置的实现）。更多关于使用 [Preference](https://developer.android.com/reference/android/preference/Preference.html) API 的信息，见：[Settings](https://developer.android.com/guide/topics/ui/settings.html) 指南。

# 获取 SharedPreferences 的句柄
你可以通过下面的方法创建新的或访问一个已存在的 Shared Preference 文件：

- [getSharedPreferences()](https://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String,int)) —— 如果你需要多个由名字区分的 Shared Preference 文件，则使用该方法，名字通过方法的第一个参数传入。该方法可以从 app 内的任意一个 [Context](https://developer.android.com/reference/android/content/Context.html) 调用。
- [getPreferences()](https://developer.android.com/reference/android/app/Activity.html#getPreferences(int)) —— 如果你的 [activity](https://developer.android.com/reference/android/app/Activity.html) 需要使用*一个* Shared Preference 文件，则可以在该 activity 内使用此方法。无需提供名字，该方法返回一个属于该 activity 的默认 Shared Preference 文件。

例如，下面的代码在一个 [Fragment](https://developer.android.com/reference/android/app/Fragment.html) 内执行。它访问由字符串资源 `R.string.preference_file_key` 指定的 shared preferences 文件，然后用私有模式打开该文件所以文件只能在你的 app 内访问。

```java
Context context = getActivity();
SharedPreferences sharedPref = context.getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
```

在命名你的 shared preference 文件时，你应该使用能唯一标识你 app 的名字，如：
`com.example.myapp.PREFERENCE_FILE_KEY`

此外，如果你的 activity 只需要一个 shared preference 文件，你可以使用 [getPreferences()](https://developer.android.com/reference/android/app/Activity.html#getPreferences(int)) 方法：
```java
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
```

> **注意：**如果你在创建 shared preference 文件时使用了 [MODE_WORLD_READABLE](https://developer.android.com/reference/android/content/Context.html#MODE_WORLD_READABLE) 或 [MODE_WORLD_WRITEABLE](https://developer.android.com/reference/android/content/Context.html#MODE_WORLD_WRITEABLE)，那么任何知道你 shared preference 文件的标识符的 app 都可以访问你的数据。

# 向 Shared Preferences 中写入数据
为向 shared preferences 文件写入数据，调用要写入的 [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences.html) 上的 [edit()](https://developer.android.com/reference/android/content/SharedPreferences.html#edit()) 方法创建 [SharedPreferences.Editor](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html)。

通过像 [putInt()](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putInt(java.lang.String,int))、[putString()](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putString(java.lang.String,java.lang.String)) 等方法传入你想写入的键值对，然后调用 [commit()](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()) 来保存更改。例如：
```java
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPref.edit();
editor.putInt(getString(R.string.saved_high_score), newHighScore);
editor.commit();
```

# 从 Shared Preferences 中读取数据
为从 shared preferences 文件中获取数据，调用像 [getInt()](https://developer.android.com/reference/android/content/SharedPreferences.html#getInt(java.lang.String,int))、[getString()](https://developer.android.com/reference/android/content/SharedPreferences.html#getString(java.lang.String,java.lang.String))，提供你想获取的值的键以及当键不存在时的默认返回值。例如：
```java
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
```
