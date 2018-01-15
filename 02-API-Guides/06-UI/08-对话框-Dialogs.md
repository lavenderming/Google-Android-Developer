参见：[Dialogs](https://developer.android.com/guide/topics/ui/dialogs.html#AddingAList)

- [核心类](#%E6%A0%B8%E5%BF%83%E7%B1%BB)
- [另见](#%E5%8F%A6%E8%A7%81)
- [概述](#%E6%A6%82%E8%BF%B0)
- [创建 Dialog Fragment](#%E5%88%9B%E5%BB%BA-dialog-fragment)
- [构建 Alert Dialog](#%E6%9E%84%E5%BB%BA-alert-dialog)
    - [添加按钮](#%E6%B7%BB%E5%8A%A0%E6%8C%89%E9%92%AE)
- [添加列表](#%E6%B7%BB%E5%8A%A0%E5%88%97%E8%A1%A8)
        - [添加持久多选或单选列表](#%E6%B7%BB%E5%8A%A0%E6%8C%81%E4%B9%85%E5%A4%9A%E9%80%89%E6%88%96%E5%8D%95%E9%80%89%E5%88%97%E8%A1%A8)

# 核心类
- [DialogFragment](https://developer.android.com/reference/android/app/DialogFragment.html)
- [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)

# 另见
- [Dialogs design guide](https://developer.android.com/design/building-blocks/dialogs.html)
- [Pickers (Date/Time dialogs)](https://developer.android.com/guide/topics/ui/controls/pickers.html)

# 概述

对话框是提示用户做出决定或输入额外信息的小窗口。对话框不会覆盖全屏且它往往作为需要用户执行操作才能继续的 modal events。

> **对话框设计**
>
> 关于如何设计你的对话框，包括推荐的语言，见 [Dialogs](https://developer.android.com/design/building-blocks/dialogs.html) 设计指南。

![](https://developer.android.com/images/ui/dialogs.png)

对话框的基本类是 [Dialog](https://developer.android.com/reference/android/app/Dialog.html) 类，但你应该避免直接实例化 [Dialog](https://developer.android.com/reference/android/app/Dialog.html) 类。你应该使用如下子类：

- [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)

    一种可以显示标题、至多三个按钮、一列可选项、或自定义布局的对话框。

- [DatePickerDialog](https://developer.android.com/reference/android/app/DatePickerDialog.html) 或 [TimePickerDialog](https://developer.android.com/reference/android/app/TimePickerDialog.html)

    预定义 UI 的对话框，用于让用户选择日期或时间。

> **避免 ProgressDialog**
> 
> Android 还包含另一种名为 [ProgressDialog](https://developer.android.com/reference/android/app/ProgressDialog.html) 的对话框类，该类用于显示待进度条的对话框。由于它阻止用户在显示进度时同 app 交互，现在已被废弃。如果你需要指示加载或不定的进度，你应该依照 [Progress & Activity](https://developer.android.com/design/building-blocks/progress.html) 并在布局文件中使用 [ProgressBar](https://developer.android.com/reference/android/widget/ProgressBar.html)，而不是使用 [ProgressDialog](https://developer.android.com/reference/android/app/ProgressDialog.html)。

这些类为你的对话框定义了样式和构造，但你应该用 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 作为你对话框的容器。[DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 提供了你所需的所有对创建和管理对话框外观的控制，避免了直接调用 [Dialog](https://developer.android.com/reference/android/app/Dialog.html) 上的方法。

用 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 来管理对话框确保它能正确处理生命周期事件，如当用户点击 *Back* 按钮或旋转屏幕。[DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 还能让你在 large UI 中将对话框 UI 作为内嵌组件来复用它，就像普通的 [Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html) 那样（比如当你想让对话框 UI 在大屏和小屏上有不同的显示）。

该指南的接下来的部分将介绍如何结合 [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html) 来使用 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html)。如果你想创建日期或时间选择器，你应该阅读 [Pickers](https://developer.android.com/guide/topics/ui/controls/pickers.html) 指南。

> **笔记：** 由于 [DialogFragment](https://developer.android.com/reference/android/app/DialogFragment.html) 类最初是随 Android 3.0（API level 11）添加的，所以该文档使用的 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 由 [Support Library](https://developer.android.com/tools/support-library/index.html) 提供。通过添加该库，你的 app 可以在 Android 1.6（API level 4）或更高版本中使用 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 和其它 API。如果你的 app 支持的最低版本大于 API level 11 或更改，你可以使用框架版本的 [DialogFragment](https://developer.android.com/reference/android/app/DialogFragment.html)，但要注意该文档的连接都是指向支持库 API 的。当你使用支持库时，注意你导入的是 `android.support.v4.app.DialogFragment` 类而不是 `android.app.DialogFragment` 类。

# 创建 Dialog Fragment

你可以实现各种各样的对话框设计 —— 包括自定义布局以及那些在 [Dialogs](https://developer.android.com/design/building-blocks/dialogs.html) 设计指南中描述的东西 —— 通过继承 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 并在 [onCreateDialog()](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html#onCreateDialog(android.os.Bundle)) 回调方法中创建 [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)。

例如，下面是一个由 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 管理的基础 [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)：

```java
public class FireMissilesDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用 Builder 类方便构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // 发射导弹！
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // 用户取消该对话框
                   }
               });
        // 创建 AlertDialog 对象并返回
        return builder.create();
    }
}
```

> ![](https://developer.android.com/images/ui/dialog_buttons.png)
> 图1.一个带信息和两个 action button 的对话框。

现在你可以创建一个该类的实例然后调用它的 [show()](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html#show(android.support.v4.app.FragmentManager,java.lang.String))，对话框就会如图一所示。

下一部分介绍如何使用 [AlertDialog.Builder](https://developer.android.com/reference/android/app/AlertDialog.Builder.html) 来创建对话框。

取决于对话框的复杂度，你可以实现 [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html) 中的其它回调方法，包括基本的 [fragment 生命周期方法](https://developer.android.com/guide/components/fragments.html#Lifecycle)。

# 构建 Alert Dialog

[AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html) 类允许你构建各种各样的对话框设计，且它常是你需要唯一对话框类。如图2.所示，alert dialog 中由三个部分：

> ![](https://developer.android.com/images/ui/dialogs_regions.png)
> 图2. dialog 的布局

1. 标题（Title）

    可选设置，且只该在内容区域（Content area）有详细信息、列表、或自定义布局时使用。如果你需要显示一个简单的信息或一个问题（如图1. 的对话框所示），你无需标题。

1. 内容区域（Content area）

    这里可以显示信息、列表、或其它自定义布局。

1. 行为按钮（Action buttons）

    一个对话框内应该不超过 3 个行为按钮。

[AlertDialog.Builder](https://developer.android.com/reference/android/app/AlertDialog.Builder.html) 类提供的 API 能让你创建带这三类内容的 [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)，包括自定义布局。

为创建 [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)：

```java
// 1. 通过它的构造函数实例化一个  AlertDialog.Builder
AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. 链接各种 setter 方法来设置对话框的特征
builder.setMessage(R.string.dialog_message)
       .setTitle(R.string.dialog_title);

// 3. 从 create() 获取 AlertDialog
AlertDialog dialog = builder.create();
```

接下来的小标题展示如何用 [AlertDialog.Builder](https://developer.android.com/reference/android/app/AlertDialog.Builder.html) 类定义各种对话框属性

## 添加按钮

为添加像图2.中的 action button，调用 [setPositiveButton()](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setPositiveButton(int,%20android.content.DialogInterface.OnClickListener)) 和 [setNegativeButton()](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setNegativeButton(int,%20android.content.DialogInterface.OnClickListener)) 方法：

```java
AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// 添加按钮
builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // 用户点击 OK 按钮
           }
       });
builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // 用户取消 dialog
           }
       });
// 设置其它 dialog 属性
...

// 创建 AlertDialog
AlertDialog dialog = builder.create();
```

`set...Button()` 方法需要一个按钮标题（由 [string resource](https://developer.android.com/guide/topics/resources/string-resource.html) 提供）和一个 [DialogInterface.OnClickListener](https://developer.android.com/reference/android/content/DialogInterface.OnClickListener.html) 定义用户点击按钮时采取的 action。

你可以添加三种不同的 action button：

- Positive
    
    用此按钮接受或继续 action（“OK” action）

- Negative

    用此按钮取消 action

- Neutral

    此按钮用于用户既不想继续也不想取消时。它介于 Positive 和 Negative 之间。比如，该按钮可以是 “稍后提醒我”

[AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html) 中上述三种 action button 每种最多只能添加 1 个。即，你不能添加多个 “positive” 按钮。

# 添加列表

> ![](https://developer.android.com/images/ui/dialog_list.png)
> 图3. 带标题和列表的 dialog

[AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html) API 提供了三种列表。

- 普通单选列表
- 持久单选列表（radio buttons）
- 持久多选列表（checkboxes）

为创建如图3.所示的单选列表，使用 [setItems()](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setItems(int,%20android.content.DialogInterface.OnClickListener))：

```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.pick_color)
           .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
               // 'which' 参数包含被选 item 的位置下标
           }
    });
    return builder.create();
}
```

由于列表出现在 dialog 的内容区域，dialog 无法同时显示信息和列表，且你应该通过 [setTitle()](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setTitle(int)) 给 dialog 设置标题。为设置列表的 item，调用 [setItems](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setItems(int,#20android.content.DialogInterface.OnClickListener))，传入一个 array。或者你可以用 [setAdapter()](https://developer.android.com/reference/android/app/AlertDialog.Builder.html#setAdapter(android.widget.ListAdapter,%20android.content.DialogInterface.OnClickListener))。这让你能用 [ListAdapter](https://developer.android.com/reference/android/widget/ListAdapter.html) 将动态数据（如来自数据库的数据）返回到列表。

如果你选择用 [ListAdapter](https://developer.android.com/reference/android/widget/ListAdapter.html) 来构建你的列表，你应该始终使用 [Loader](https://developer.android.com/reference/android/support/v4/content/Loader.html) 让内容可以异步加载。详细描述见 [Building Layouts with an Adapter](https://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews) 和 [Loaders](https://developer.android.com/guide/components/loaders.html) 指南。

> **笔记：** 默认情况，触摸一个列表选项后 dialog 会消失，除非你使用下面介绍的某个持久选择列表。

> ![](https://developer.android.com/images/ui/dialog_checkboxes.png)
> 图4. 一列多选项

### 添加持久多选或单选列表

为添加一列多选项（checkboxes）或单选项（radio buttons），





