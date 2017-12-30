参见：[Communicating with Other Fragments](https://developer.android.com/training/basics/fragments/communicating.html)

- [还需阅读](#%E8%BF%98%E9%9C%80%E9%98%85%E8%AF%BB)
- [可以尝试](#%E5%8F%AF%E4%BB%A5%E5%B0%9D%E8%AF%95)
- [概述](#%E6%A6%82%E8%BF%B0)
- [在 fragment 中定义一个向 activity 传递信息的接口](#%E5%9C%A8-fragment-%E4%B8%AD%E5%AE%9A%E4%B9%89%E4%B8%80%E4%B8%AA%E5%90%91-activity-%E4%BC%A0%E9%80%92%E4%BF%A1%E6%81%AF%E7%9A%84%E6%8E%A5%E5%8F%A3)
- [在 activity 中实现接口](#%E5%9C%A8-activity-%E4%B8%AD%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3)
- [发送信息到 fragment](#%E5%8F%91%E9%80%81%E4%BF%A1%E6%81%AF%E5%88%B0-fragment)

# 还需阅读
- [Fragments](https://developer.android.com/guide/components/fragments.html)

# 可以尝试
- [FragmentBasics.zip](Sample/FragmentBasics.zip)

# 概述
为了复用 Fragment UI 组件，你应该将每个 Fragment 构建为自包含、模块化的组件，它有自己的布局和行为。一旦你定义了这种可复用的 fragment，你可以将它们和 activity 联系以及与应用逻辑关联来构建整体的 UI。

你经常想在两个 fragment 间通信，比如基于一个 fragment 的用户事件改变另一个 fragment 的内容。所有 fragment 到 fragment 的通信都通过关联 activity 来实现，两个 fragment 应该永远不直接通信。

# 在 fragment 中定义一个向 activity 传递信息的接口
为允许 fragment 向它的 activity 传递信息，你可以在 fragment 类内定义一个接口并在要使用该 fragment 的 activity 内实现该接口。fragment 在其 [onAttach()](https://developer.android.com/reference/android/support/v4/app/Fragment.html#onAttach(android.content.Context)) 生命周期方法中获取接口的实现并在后面调用该接口的方法与 activity 通信。

一个 fragment 与 activity 通信的例子：
```java
public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    // 父 activity 必须实现这个接口让 fragment 可以传递信息
    public interface OnHeadlineSelectedListener {
        /** 当列表中的 item 被选中时由 HeadlinesFragment 调用，触发实现该接口的操作 */
        public void onArticleSelected(int position);
    }

    /** onAttach 在 fragment 第一次被联系到它的 activity 上时被调用 */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 确保父 activity 实现回调接口，如果没实现，抛出异常
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    ...
}
```

现在 fragment 可以使用 `OnHeadlineSelectedListener` 接口的 `mCallback` 实例，调用实例的 `onArticleSelected()` 方法（或接口中的其它方法）向 activity 发送信息了。

例如，下面的 `onListItemClick()` 方法在 fragment 的 list item 被用户点击时被调用。fragment 使用回调接口把信息传递到父 activity 中。

```java
@Override
public void onListItemClick(ListView l, View v, int position, long id) {
    // 向父 activity 发送信息
    mCallback.onArticleSelected(position);
}
```

# 在 activity 中实现接口
为了接收来自 fragment 的回调事件，使用 fragment 的 activity 必须实现 fragment 类内定义的接口。

例如，下面的 activity 实现了上面栗子中的接口：
```java
public static class MainActivity extends Activity
        implements HeadlinesFragment.OnHeadlineSelectedListener{
    ...

    public void onArticleSelected(int position) {
        // 用户从 HeadlinesFragment 中选中了文章的标题
        // 在这里完成显示文章的操作
    }
}
```

# 发送信息到 fragment
父 activity 通过调用 [FragmentManager](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html) 的 [findFragmentById()](https://developer.android.com/reference/android/support/v4/app/FragmentManager.html#findFragmentById(int)) 获取 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 实例，然后即可向 fragment 发送信息。

例如，要把上面栗子中用户选中的文章标题传给 activity 上的另一个 fragment，让另一个 fragment 显示文章的内容，则应该：

```java
public class MainActivity extends FragmentActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {
    ...

    /**
     * Activity 实现 HeadlinesFragment.OnHeadlineSelectedListener 接口
     * 该接口将使 HeadlinesFragment 不直接操作其它 fragment
     * @param position
     */
    public void onArticleSelected(int position) {
        // 用户从 HeadlinesFragment 中选中了一篇文章的标题

        // 从 activity 布局中查找 article fragment
        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // 若 article frag 存在, 说明处于双面板布局

            // 调用显示文章内容的 ArticleFragment 中的方法更新它的内容
            articleFrag.updateArticleView(position);

        } else {
            // 若不存在，说明处于单面板布局，则需要变换 fragment

            // 创建 fragment 并设置其参数
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            // 创建 fragment 事务
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // 不论当前的 fragment_container view 是什么，将其替换为新的 fragment
            // 将事务添加到 back stack 让用户可以回退
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // 提交事务
            transaction.commit();
        }
    }
}
```
