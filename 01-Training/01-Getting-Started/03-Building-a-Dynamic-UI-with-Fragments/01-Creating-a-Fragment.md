参见：[Creating a Fragment](https://developer.android.com/training/basics/fragments/creating.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [创建一个 Fragment 类](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA-fragment-%E7%B1%BB)
- [通过 XML 添加 Fragment 到 Activity](#%E9%80%9A%E8%BF%87-xml-%E6%B7%BB%E5%8A%A0-fragment-%E5%88%B0-activity)

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)

# 可以尝试
- [FragmentBasics.zip](Sample/FragmentBasics.zip)

# 概述
可以把 fragment 认为是 activity 的模块化部分，它有自己的生命周期，接收自己的输入事件，在 activity 运行时可以添加或删除（有点像一个可以用在不同 activity 中的“小 Activity”）。本课程展示如何用[支持库](https://developer.android.com/tools/support-library/index.html)继承 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 类，让你的 app 仍然保持对最低运行 Android 1.6 的设备的兼容性。

在开始课程前，必须设置你的 Android 项目使用支持库。如果你从没使用过支持库，依据 [Support Library Setup](https://developer.android.com/tools/support-library/setup.html) 文档中的步骤设置项目使用 **v4** 库。如果你通过使用 **v7 appcompat** 库在 activity 中包含了 [app bar](https://developer.android.com/training/appbar/index.html)，它兼容到 Android 2.1（API 级别 7）且同样包括 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) API。

# 创建一个 Fragment 类
为创建 fragment，先继承 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 类，然后重写核心生命周期方法来插入 app 的逻辑，类似使用 [Activity](https://developer.android.com/reference/android/app/Activity.html) 类。

一个不同之处是在创建 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 时你必须使用 [onCreateView()](https://developer.android.com/reference/android/support/v4/app/Fragment.html#onCreateView(android.view.LayoutInflater,android.view.ViewGroup,android.os.Bundle)) 回调函数来定义布局。事实上这是让 fragment 运行你需要的唯一回调。例子：
```java
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ArticleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate 这个 fragment 的布局
        return inflater.inflate(R.layout.article_view, container, false);
    }
}
```

就像 activity，fragment 应该实现它的其它生命周期回调，这样可以让你管理它从 activity 添加或删除时、或 Activity 自身状态改变时的状态。例如，当 activity 的 [onPause()](https://developer.android.com/reference/android/app/Activity.html#onPause()) 方法被调用，activity 上的任何 fragment 都会收到对自己 [onPause()](https://developer.android.com/reference/android/support/v4/app/Fragment.html#onPause()) 的调用。

更多关于 fragment 生命周期和回调方法的信息，见：[Fragments](https://developer.android.com/guide/components/fragments.html) 开发者指南。

# 通过 XML 添加 Fragment 到 Activity

即使 fragment 是可复用、模块化的 UI 组件，每个 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 类的实例也必须关联到一个父 [FragmentActivity](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html)。你可以通过在 activity 的 XML 布局文件中定义每个 fragment 来完成这种关联。

> 笔记：[FragmentActivity](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html) 是支持库提供的特殊 activity，用于处理系统版本是 API 11 之前 fragment。如果你支持的最低系统版本是 API 11 或更高，那么你可以使用一般的 [Activity](https://developer.android.com/reference/android/app/Activity.html)。

这有个在设备屏幕为“large”时在一个 activity 内添加两个 fragment 的 activity 布局文件（由目录名中的 `large` 限定符指定）。

```xml
<!-- res/layout-large/news_articles.xml -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">

    <fragment android:name="com.example.android.fragments.HeadlinesFragment"
              android:id="@+id/headlines_fragment"
              android:layout_weight="1"
              android:layout_width="0dp"
              android:layout_height="match_parent" />

    <fragment android:name="com.example.android.fragments.ArticleFragment"
              android:id="@+id/article_fragment"
              android:layout_weight="2"
              android:layout_width="0dp"
              android:layout_height="match_parent" />

</LinearLayout>
```
> 小提示：更多关于如何为不同屏幕尺寸创建布局，见 [Supporting Different Screen Sizes](https://developer.android.com/training/multiscreen/screensizes.html)

然后在 activity 中应用该布局：
```java
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
    }
}
```

如果你使用 [v7 appcompat library](https://developer.android.com/tools/support-library/features.html#v7-appcompat)，你的 activity 应该继承 [AppCompatActivity](https://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html)，它是 [FragmentActivity](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html) 类的子类。更多信息见：[Adding the App Bar](https://developer.android.com/training/appbar/index.html)。

> 笔记：当你通过在 activity 的 XML 布局文件中定义 fragment 来添加 fragment，你 **不能** 在运行时移除这个 fragment。如果你计划在用户交互的过程中转换 fragment，你必须在 activity 第一次启动时将 fragment 添加到 activity 中，如下节课中所示。
