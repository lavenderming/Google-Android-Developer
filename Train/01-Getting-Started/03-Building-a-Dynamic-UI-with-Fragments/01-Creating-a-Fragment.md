参见：[Creating a Fragment](https://developer.android.com/training/basics/fragments/creating.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [创建一个 Fragment 类](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA-fragment-%E7%B1%BB)

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)

# 可以尝试
- [FragmentBasics.zip](http://developer.android.com/shareables/training/FragmentBasics.zip)

# 概述
可以把 fragment 认为是 activity 的模块化部分，它有自己的生命周期，接收自己的输入事件，在 activity 运行时可以添加或删除（有点像一个可以用在不同 activity 中的“小 Activity”）。本课程展示如何用[支持库](https://developer.android.com/tools/support-library/index.html)继承 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 类，让你的 app 仍然保持对最低运行 Android 1.6 的设备的兼容性。

在开始课程前，必须设置你的 Android 项目使用支持库。如果你从没使用过支持库，依据 [Support Library Setup](https://developer.android.com/tools/support-library/setup.html) 文档中的步骤设置项目使用 **v4** 库。如果你通过使用 **v7 appcompat** 库在 activity 中包含了 [app bar](https://developer.android.com/training/appbar/index.html)，它兼容到 Android 2.1（API 级别 7）且同样包括 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) API。

# 创建一个 Fragment 类
为创建 fragment，先继承 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 类，然后重写核心生命周期方法来插入 app 的逻辑，类似使用 [Activity](https://developer.android.com/reference/android/app/Activity.html) 类。

一个不同之处是在创建 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 时你必须使用 [onCreateView()](https://developer.android.com/reference/android/support/v4/app/Fragment.html#onCreateView(android.view.LayoutInflater,\ android.view.ViewGroup,\ android.os.Bundle)) 回调函数来定义布局。事实上这是让 fragment 运行你需要的唯一回调。例子：
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