参见：[Wi-Fi Peer-to-Peer](https://developer.android.com/guide/topics/connectivity/wifip2p.html)

- [概述](#%E6%A6%82%E8%BF%B0)
- [API 概览](#api-%E6%A6%82%E8%A7%88)
- [为 Wi-Fi P2P Intent 创建广播接收器](#%E4%B8%BA-wi-fi-p2p-intent-%E5%88%9B%E5%BB%BA%E5%B9%BF%E6%92%AD%E6%8E%A5%E6%94%B6%E5%99%A8)
- [创建一个 Wi-Fi P2P App](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA-wi-fi-p2p-app)
    - [初始设置](#%E5%88%9D%E5%A7%8B%E8%AE%BE%E7%BD%AE)
    - [发现 peer](#%E5%8F%91%E7%8E%B0-peer)

# 概述

Wi-Fi peer-to-peer（P2P）允许有适合硬件的 Android 4.0（API 14）或之后的 Android 设备直接通过 Wi-Fi 互相连接，无需中间接入点（Android 的 Wi-Fi P2P 框架兼容 Wi-Fi 联盟的 Wi-Fi Direct™ 验证程序）。使用这些 API，你可以在双发设备都支持 Wi-Fi P2P 时发现并连接对方，然后通过快速的连接通道进行通信，且连接的距离远远超过蓝牙连接。这对于需要在用户间分享数据的 app 是十分有用的，比如多人游戏或照片分享 app。

Wi-Fi P2P API 由如下主要部分组成：

- 发现、申请并连接到 peer 的方法定义在 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 类中。
- 监听 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 方法调用的监听器。在调用 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 方法时，每个方法都可以接收作为参数传入的指定监听器。
- 通知你 Wi-Fi P2P 框架检测到的特定事件的 intent，例如连接结束或发现新的 peer。

你通常组合使用这三个主要的 API 组件。例如，你可以在调用 [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) 时传入一个 [WifiP2pManager.ActionListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html)，这样你就可以在 [ActionListener.onSuccess()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html#onSuccess()) 和 [ActionListener.onFailure()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html#onFailure(int)) 方法中收到通知。如果 [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) 方法发现对等列表已更改，则还会广播[WIFI_P2P_PEERS_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_PEERS_CHANGED_ACTION) intent。

# API 概览

[WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 类提供了一些方法，这些方法让你直接同设备上的 Wi-Fi 硬件交互，以完成比如发现并连接到 peer 的操作。下面是可用的 actions：

> **表1.** Wi-Fi P2P 方法

| 方法 | 描述 |
|---|---|
| [initialize()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#initialize(android.content.Context,%20android.os.Looper,%20android.net.wifi.p2p.WifiP2pManager.ChannelListener))  | 将 app 注册到 Wi-Fi 框架。该方法必须在其它 Wi-Fi P2P 方法前调用   |
| [connect()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#connect(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pConfig,%20android.net.wifi.p2p.WifiP2pManager.ActionListener))  | 按照指定配置开始 P2P 连接  |
| [cancelConnect()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#cancelConnect(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) | 取消任何正在进行的 P2P 组协商   |
| [requestConnectInfo()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestConnectionInfo(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener)) | 请求设备的连接信息 |
| [createGroup()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#createGroup(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) | 以组所有者身份在当前设备上创建 P2P 组 |
| [removeGroup()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#removeGroup(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) | 移除当前的 P2P 组 |
| [requestGroupInfo()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestGroupInfo(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.GroupInfoListener)) | 请求 P2P 组信息 |
| [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) | 初始化 peer 发现 |
| [requestPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.PeerListListener)) | 请求当前发现的 peer 列表 |

这些 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 让你传入监听器，这样 Wi-Fi P2P 框架可以通过调用告知你活动的状态。可用的监听器接口及其对应的 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 如下表所示：

> **表2.** Wi-Fi P2P 监听器

| 监听器接口 | 相关 action |
|---|---|
| [WifiP2pManager.ActionListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html)  | [connect()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#connect(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pConfig,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)), [cancelConnect()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#cancelConnect(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)), [createGroup()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#createGroup(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)), [removeGroup()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#removeGroup(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)), [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener))  |
| [WifiP2pManager.ChannelListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ChannelListener.html) | [initialize()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#initialize(android.content.Context,%20android.os.Looper,%20android.net.wifi.p2p.WifiP2pManager.ChannelListener))  |
| [WifiP2pManager.ConnectionInfoListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ConnectionInfoListener.html)  | [requestConnectInfo()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestConnectionInfo(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener)) |
| [WifiP2pManager.GroupInfoListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.GroupInfoListener.html)  | [requestGroupInfo()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestGroupInfo(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.GroupInfoListener))  |
| [WifiP2pManager.PeerListListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.PeerListListener.html)  | [requestPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.PeerListListener))  |

Wi-Fi P2P API 定义了在特定 Wi-Fi P2P 事件发生时的广播 intent，比如在发现新的 peer 时，或在 Wi-Fi 状态改变时。你可以在 app 内通过 [创建广播接收器](https://developer.android.com/guide/topics/connectivity/wifip2p.html#creating-br) 注册、接收而后处理这些 intent。

> **表3.** Wi-Fi P2P Intent

| Intent | 描述  |
|---|---|
| [WIFI_P2P_CONNECTION_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_CONNECTION_CHANGED_ACTION)  | 在设备 Wi-Fi 连接的状态改变时广播  |
| [WIFI_P2P_PEERS_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_PEERS_CHANGED_ACTION)  | 在你调用 [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) 时广播。如果你在 app 中处理这个 intent，你通常时想调用 [requestPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.PeerListListener)) 来获取更新的 peer 列表 |
| [WIFI_P2P_STATE_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_STATE_CHANGED_ACTION) | 在设备的 Wi-Fi P2P 启用或禁用时广播 |
| [WIFI_P2P_THIS_DEVICE_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)  | 在设备的详细信息发生变更时广播，比如设备名 |

# 为 Wi-Fi P2P Intent 创建广播接收器

广播接收器允许你接收来自 Android 系统的 intent 广播，由此，你的 app 可以响应你感兴趣的事件。创建处理 Wi-Fi P2P intent 广播接收器的基本步骤如下：

1. 创建一个继承 [BroadcastReceiver ](https://developer.android.com/reference/android/content/BroadcastReceiver.html) 的类。对于该类的构造函数，你最可能想用的参数是 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html)、[WifiP2pManager.Channel](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.Channel.html) 以及该广播接收器要注册的 activity。这使得广播接收器既能向 activity 发送更新，又能在需要时访问 Wi-Fi 硬件和通讯 channel。
2. 在广播接收器内，在 [onReceive()](https://developer.android.com/reference/android/content/BroadcastReceiver.html#onReceive(android.content.Context,%20android.content.Intent)) 中检查你感兴趣的 intent。进行任何依赖于接收到的 intent 的必要 action。例如，如果广播接收器接收一个 [WIFI_P2P_PEERS_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_PEERS_CHANGED_ACTION) intent，你可以调用 [requestPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.PeerListListener)) 方法获取最新发现的 peer 列表。

下面的代码向你展示如何创建一个典型的广播接收器。该广播接收器接收 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 对象和 activity 对象为参数，并使用这俩对象在接收到 intent 时恰当处理需要的 action：

> **阿懂：** 这里是描述有误？

```java
/**
 * 通知主要 Wi-Fi p2p 事件的 BroadcastReceiver.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private MyWiFiActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
            MyWifiActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // 检查 Wi-Fi 是否启用并通知相应 activity
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // 调用 WifiP2pManager.requestPeers() 获取当前的 peer 列表
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // 响应新连接或断开连接
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // 响应该设备的 wifi 状态改变
        }
    }
}
```

# 创建一个 Wi-Fi P2P App

创建一个 Wi-Fi P2P App 包括为 app 创建和注册广播接收器、发现 peer、连接 peer，并向 peer 传输数据。下面的部分描述如何完成这些。

## 初始设置

在使用 Wi-Fi P2P API 前，你必须确保你的 app 可以访问硬件且设备支持 Wi-Fi P2P 协议。如果支持 Wi-Fi P2P，你可以获取 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 实例，创建并注册你的广播接收器，并开始使用 Wi-Fi P2P API。

1. 在 Android manifest 中申请使用设备 Wi-Fi 硬件的权限、声明正确的最低 SDK 版本：
    ```xml
    <uses-sdk android:minSdkVersion="14" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    ```

1. 检查 Wi-Fi P2P 打开、支持状态。检查该项的好地方是在广播接收器接收到 [WIFI_P2P_STATE_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_STATE_CHANGED_ACTION) intent 时。通知你的 activity Wi-Fi P2P 状态并正确响应：

    ```java
    @Override
    public void onReceive(Context context, Intent intent) {
        ...
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P 已启用
            } else {
                // Wi-Fi P2P 未启用
            }
        }
        ...
    }
    ```

1. 在 activity 的 [onCreate](https://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)) 方法中，获取 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 的实例并通过调用该实例的 [initialize()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#initialize(android.content.Context,%20android.os.Looper,%20android.net.wifi.p2p.WifiP2pManager.ChannelListener)) 将 app 注册到 Wi-Fi P2P 框架。该方法会返回一个 [WifiP2pManager.Channel](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.Channel.html)，用于连接 app 和 Wi-Fi P2P 框架。你还应该以 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html)、[WifiP2pManager.Channel](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.Channel.html) 以及 activity 的引用创建一个你的广播接收器的实例。这样你的广播接收器即可通知 activity 相关事件，并使 activity 随之更新。它还能让你在必要时操作设备的 Wi-Fi 状态：

    ```java
    WifiP2pManager mManager;
    Channel mChannel;
    BroadcastReceiver mReceiver;
    ...
    @Override
    protected void onCreate(Bundle savedInstanceState){
        ...
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        ...
    }
    ```

1. 创建 intent 过滤器并添加广播接收器要检测的 intent：

    ```java
    IntentFilter mIntentFilter;
    ...
    @Override
    protected void onCreate(Bundle savedInstanceState){
        ...
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        ...
    }
    ```

1. 在 activity 的 [onResume()](https://developer.android.com/reference/android/app/Activity.html#onResume()) 方法中注册广播接收器，并在 activity 的 [onPause()](https://developer.android.com/reference/android/app/Activity.html#onPause()) 中撤销注册：

    ```java
    /* 使用广播接收器和它匹配的 intent 值注册它 */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* 撤销广播接收器的注册 */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
    ```

    当你获取 [WifiP2pManager.Channel](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.Channel.html) 并初始化了广播接收器，你的 app 就可以调用 Wi-Fi P2P 方法并接收 Wi-Fi P2P intent。

    你现在可以实现 app 并通过调用 [WifiP2pManager](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html) 中的方法使用 Wi-Fi P2P 的功能。下一节描述如何执行一些常见的动作比如发现与连接 peer。 

## 发现 peer

为发现可连接的 peer，需要调用 [discoverPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#discoverPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.ActionListener)) 检测范围内可用的 peer。该方法异步调用，如果你传入了 [WifiP2pManager.ActionListener](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html)，则结果会通过监听器的 [onSuccess()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html#onSuccess()) 和 [onFailure()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html#onFailure(int)) 方法告知 app。[onSuccess()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.ActionListener.html#onSuccess()) 方法只会通知你发现已执行成功，并不会提供任何关于实际发现的 peer 信息：

```java
mManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
    @Override
    public void onSuccess() {
        ...
    }

    @Override
    public void onFailure(int reasonCode) {
        ...
    }
});
```

如果发现执行成功并检测到 peer，系统广播 [WIFI_P2P_PEERS_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_PEERS_CHANGED_ACTION) intent，你可以在广播接收器中接收该广播来获取 peer 列表。当你的 app 接收到 [WIFI_P2P_PEERS_CHANGED_ACTION](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#WIFI_P2P_PEERS_CHANGED_ACTION) intent，你可以通过 [requestPeers()](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pManager.html#requestPeers(android.net.wifi.p2p.WifiP2pManager.Channel,%20android.net.wifi.p2p.WifiP2pManager.PeerListListener)) 请求已发现的 peer 列表。下面代码展示这些如何设置：

```java
PeerListListener myPeerListListener;
...
if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

    // 从 wifi p2p manager 处请求可用 peer 列表. 这是个异步调用且
    // 调用的 activity 在 PeerListListener.onPeersAvailable() 
    // 回调中收到结果
    if (mManager != null) {
        mManager.requestPeers(mChannel, myPeerListListener);
    }
}
```
