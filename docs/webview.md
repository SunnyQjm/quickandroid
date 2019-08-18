> ## quick_android_webview 模块说明文档
>
> 本模块提供一个简单的**H5容器**实现方案。
>
> - 接入腾讯的[X5浏览器](https://x5.tencent.com/)内核，比原生的更安全且对js的新特性有更好的支持，兼容性问题较少，同时支持和QQ、微信等共享内核，可以大大减少APP包的体积。
> - 本模块实现一个简单的 `Android Webview Bridge` **QuickAndroidWebBridge**，可以实现js调用原生的扫码、图片选择、获取设备信息等功能



- ### 使用说明

  - 首先在你的作为H5容器的Acivity中使用 `QuickAndroidWebview` 替代原生的Webview进行布局

    ```xml
    <cn.qjm253.quick_android_webview.views.QuickAndroidWebview
                android:id="@+id/quickAndroidWebview"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
    
    </cn.qjm253.quick_android_webview.views.QuickAndroidWebview>
    ```

  - 接着在Activity中使用

    ```kotlin
    // 初始化操作，绑定通信桥
    QuickAndroidWV
    	.init(this, quickAndroidWebview)
    
    // 加载URL
    quickAndroidWebview.loadUrl("file:///android_asset/www/test.html")
    ```

  - 完成上面两步，基本上H5容器就可以使用了，H5端搭配使用[quick_android_web_bridge](https://github.com/SunnyQjm/quick_android_web_bridge) 这一JS库便可进行原生功能的调用

- ### 提供的原生调用能力如下：

  > H5端具体如何接入，参考[quick_android_web_bridge](https://github.com/SunnyQjm/quick_android_web_bridge)

  - #### toast（吐司弹窗）

    H5端可以使用[quick_android_web_bridge](https://github.com/SunnyQjm/quick_android_web_bridge)调用原生的toast

    ```javascript
    /**
     * 调用Android原生的toast接口
     *
     * @param msg	要显示的信息
     */
    function toast(msg: string) 
    
    // 使用示例
    QuickAndroidWebBridge.scanCode()
    ```

  - #### scanCode（扫码）

    H5端可以使用[quick_android_web_bridge](https://github.com/SunnyQjm/quick_android_web_bridge)调用原生的扫码功能，并得到扫码的结果

    ```javascript
    /**
     * 调用原生 的扫码功能，并得到扫码的结果
     *
     * @return 返回一个Promise对象，可以异步的得到扫码结果
     */ 
    function scanCode(): Promise<string>
        
        
    
    // 使用示例
    QuickAndroidWebBridge.scanCode()
        .then(result => {			// 扫码成功，在此处理扫码的结果
        
    	})
        .catch(err => {				// 发生错误，在此处理错误
        
    	})
    ```

  - #### getSystemInfo（获取Android设备的信息）

    H5端可以使用[quick_android_web_bridge](https://github.com/SunnyQjm/quick_android_web_bridge)获取到当前Android手机的设备信息

    ```javascript
    /**
     * 获取当前手机系统的信息
     *
     * @return 返回一个Promise对象，可以异步得到扫码结果
     */
    function getSystemInfo(): Promise<SystemInfo>
        
    // 使用示例
    QuickAndroidWebBridge.getSystemInfo()
        .then(result => {			// 获取系统信息成功，在此处理结果
        
    	})
        .catch(err => {				// 发生错误，在此处理错误
        
    	})
    ```

    - 获取到的信息如下所示：

      |     属性名      | 属性类型 |          属性说明          |
      | :-------------: | :------: | :------------------------: |
      |      brand      |  string  |          手机品牌          |
      |      model      |  string  |          手机型号          |
      |    hardware     |  string  |        手机硬件信息        |
      |     sdkInt      |  number  |      Android SDK 版本      |
      |     release     |  string  |       手机系统版本号       |
      | statusBarHeight |  number  | 手机系统状态栏的高度（px） |
      | systemLanguage  |  string  |    当前手机上使用的语言    |



