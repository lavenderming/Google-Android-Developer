参见：[Start Another Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)

- [响应 send button](#%E5%93%8D%E5%BA%94-send-button)
- [创建一个 Intent](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA-intent)
- [创建第二个 activity](#%E5%88%9B%E5%BB%BA%E7%AC%AC%E4%BA%8C%E4%B8%AA-activity)
- [添加 text view](#%E6%B7%BB%E5%8A%A0-text-view)
- [显示信息](#%E6%98%BE%E7%A4%BA%E4%BF%A1%E6%81%AF)
- [添加 up 导航](#%E6%B7%BB%E5%8A%A0-up-%E5%AF%BC%E8%88%AA)
- [运行 app](#%E8%BF%90%E8%A1%8C-app)

在完成[上一节](Build-a-Simple-User-Interface.md)后，你有一个含一个 activity 的 app，该 app 上显示一个输入框和一个按钮。本节中，将添加一些代码到 `MainActivity` 中，当用户点击 **Send** 时能启动一个新的 activity 来显示消息。

> **笔记**：本节默认你使用 Android Studio 3.0

# 响应 send button

如下向 `MainActivity.java` 中添加被 button 调用方法：

1. 在 **app > java > com.example.myfirstapp > MainActivity.java** 文件中，添加 `sendMessage()` 方法存根（method stub）如下：
    ```java
    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        /** 当用户点击 Send button 时被调用 */
        public void sendMessage(View view) {
            // 响应 button 时要做的事  
        }
    }
    ```

    您可能会看到一个错误，因为 Android Studio 无法解析作为方法参数的 `View` 类。因此，将光标放在 `View` 的声明上，然后按 Alt + Enter（或 Mac 上的 Option + Enter）执行快速修复。（如果出现菜单，请选择 **Import class**。）

1. 现在回到 **activity_main.xml** 文件让 button 调用该方法：
    
    1. 选中布局编辑器中的 button
    1. 在 **Attributes** 窗口中，找到 **onClick** 属性并从下拉列表中选择 **sendMessage [MainActivity]** 

至此，当 button 被点击，系统会调用 `sendMessage()` 方法。

注意该方法必要的一些细节以便系统能将其识别为能匹配 [android:onClick](https://developer.android.com/reference/android/view/View.html#attr_android:onClick) 属性的方法。具体而言，该方法必须如下声明：

- 访问修饰符必须为 public
- 返回类型为 void
- 只有一个 [View](https://developer.android.com/reference/android/view/View.html) 类型参数（该参数是被点击的 View 对象）

接下来，你将完成该方法让该方法读取输入框的文本内容并将该文本发送到另一个 activity 中。

# 创建一个 Intent

一个 [Intent](https://developer.android.com/reference/android/content/Intent.html) 是一个提供运行时绑定不同组件的对象，例如两个 activity。[Intent](https://developer.android.com/reference/android/content/Intent.html) 表示一个 app “意图做某事”。你可以用 intent 完成各种各样的任务，但在这节课中，你的 intent 启动另一个 activity。

在 `MainActivity.java` 中添加 `EXTRA_MESSAGE` 常量和 `sendMessage()` 代码：
```java
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** 当用户点击 Send button 时被调用 */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
```

最终的 import 如下：
```java
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
``` 

仍有个关于 `DisplayMessageActivity` 的 error，但这没事，你将在下一部分中修复该问题。

这是在 `sendMessage()` 中发生的事：
- [Intent](https://developer.android.com/reference/android/content/Intent.html) 构造函数接收两个参数：
  - 第一个参数是 [Context](https://developer.android.com/reference/android/content/Context.html)（使用 `this` 是因为 [Activity](https://developer.android.com/reference/android/app/Activity.html) 类是 [Context](https://developer.android.com/reference/android/content/Context.html) 类的子类）
  - 第二个参数是 app 组件的 [Class](https://developer.android.com/reference/java/lang/Class.html)，系统将该 Intent 发送至该参数指定的组件（这里，`DisplayMessageActivity` 将被启动）。

- [putExtra()](https://developer.android.com/reference/android/content/Intent.html#putExtra(java.lang.String,java.lang.String)) 方法将 `EditText` 的值添加到 intent。一个 `Intent` 可以携带作为键值对的数据类型，这些键值对被称为 extra。key 是一个 public 的常量，因为下一个 activity 需要用这个 key 来获取文本值。一个良好实践是在定义 intent extra 的 key 时使用 app 的包名作为前缀。这确保你的 app 和其它 app 交互时 key 是唯一的。

- [startActivity()](https://developer.android.com/reference/android/app/Activity.html#startActivity(android.content.Intent)) 方法启动 [Intent](https://developer.android.com/reference/android/content/Intent.html) 指定的 `DisplayMessageActivity` 的一个实例。现在你需要创建这个类。

# 创建第二个 activity
1. 在 **Project** 窗口，右键 **app** 文件夹，选择 **New > Activity > Empty Activity**
1. 在 **Configure Activity** 窗口，在 **Activity Name** 中输入“DisplayMessageActivity”并点击 **Finish**

Android Studio 自动完成下列三件事：
- 创建 `DisplayMessageActivity.java` 文件。
- 创建对应的 `activity_display_message.xml` 布局文件。
- 在 `AndroidManifest.xml` 中添加需要的 [&lt;activity>](https://developer.android.com/guide/topics/manifest/activity-element.html)

若运行 app 并点击第一个 activity 上的按钮，第二个 activity 启动但没有内容。这是由于第二个 activity 使用了模板提供的空布局。

# 添加 text view
![图1.text view 位于 layout 顶部的中间](https://developer.android.com/training/basics/firstapp/images/constraint-textview_2x.png)

新的 activity 包含了一个空的布局文件，所以你现在需要添加一个显示消息的 text view。

1. 打开文件 **app > res > layout > activity_display_message.xml**
1. 点击工具栏上的 **Turn On Autoconnect** ![](https://developer.android.com/studio/images/buttons/layout-editor-autoconnect-on.png)（点击后将被启用，如上图所示）。
1. 在 **Palette** 窗口，点击 **Text** 然后将 **TextView** 拖拽入布局——放在接近顶部中间的地方，它会被出现的垂直线“咬”住。由于上一步中开启了 Autoconnect，故 Autoconnect 会在 view 的左右两侧添加约束使 view 水平居中。
1. 再创建一个从 text view 的顶部到布局的顶部的约束，最终情况如上图所示。

可选：修改文本样式。在 **Attributes** 窗口中展开 **textAppearance**，修改如 **textSize**、**textColor** 等属性。

# 显示信息

现在你将修改第二个 activity 来显示第一个 activity 传入的信息。

1. 在 `DisplayMessageActivity.java` 的 `onCreate()` 方法中添加下列代码：
    ```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        
        // 获取启动该 activity 的 Intent 并抽取出字符串
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // 获取布局中的 TextView 并将字符串作为 TextView 的 text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
    ```
1. import 如下：
   ```java
    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.TextView;
   ```

# 添加 up 导航
app 中的每一屏若非主入口（所有不是“home”屏的屏），则应该提供导航。当用户点击 [app bar](https://developer.android.com/training/appbar/index.html) 上的 Up 按钮时可以返回 app 层次中的逻辑父屏（logical parent）。

所有要做的只是在 [AndroidManifest.xml](https://developer.android.com/guide/topics/manifest/manifest-intro.html) 中声明哪个 activity 是 logical parent。所以打开文件 **app > manifests > AndroidManifest.xml**，找到 `DisplayMessageActivity` 的 `<activity>` 标签，将其作如下替换：
```xml
<activity android:name=".DisplayMessageActivity"
          android:parentActivityName=".MainActivity" >
    <!-- 如果你需要支持 API 15 或更低的 API 版本，则需要 meta-data 标签 -->
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity" />
</activity>
```

现在 Android 系统将自动在 app bar 上添加 Up 按钮。

# 运行 app
通过点击工具栏上的 **Apply Changes** ![](https://developer.android.com/studio/images/buttons/toolbar-apply-changes.png) 再次运行 app。当应用打开后，在输入框中输入信息，然后点击 **Send**，信息将在第二个 activity 中出现。

![图2.两个 Activity 的截图](https://developer.android.com/training/basics/firstapp/images/screenshot-activity2.png)

现在，你已完成你第一个 Android app 的制作。
