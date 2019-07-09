# quickandroid
> QuickAndroid一个Android快速开发的模板库，支持开箱即用的扫码、图片选择、MVP架构模式、全功能的下拉刷新和上拉加载更多（支持自定义刷新头）等等多项功能，可以根据需要引入不同的库实现不同的功能

[![](https://jitpack.io/v/SunnyQjm/quickandroid.svg)](https://jitpack.io/#SunnyQjm/quickandroid)

> ## quick_android_base 模块
>
> 本模块提供了一些常用的工具库，一些常用的Kotlin扩展，以及一些基本功能的封装

- ### 初始化

  ```kotlin
  // 在Application中初始化
  QuickAndroid.init()
  	// 执行下面一行可以开启应用Activity的侧滑返回支持
  	.enableParallaxBack(context)		
  ```

  

- ### 侧滑返回支持说明

  1. 所有需要支持侧滑返回的`Activity`都要继承 [BaseQuickAndroidActivity](<https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) ；

  2. 如果继承自[BaseQuickAndroidActivity](<https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) 的`Activity`需要按业务需求开启和关闭业务需求，可以使用下面两个函数（下面两个函数定义在[BaseQuickAndroidActivity](<https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) 当中，所有集成自该类的Activity均可调用）：

     ```kotlin
     // 禁止侧滑返回
     disableParallaxBack()
     
     // 开启侧滑返回
     enableParallaxBack()
     ```

  3. 本侧滑返回使用的是[ParallaxBackLayout](<https://github.com/anzewei/ParallaxBackLayout)，如果需要自定义侧滑模式，以及触发侧滑返回的位置等等，可以参考该库提供的配置。


> ## quick_android_mvp模块
> 本模块提供了一个快速实现MVP架构的框架，并且封装了网络请求（Retrofit + RxJava）


- ### 说明
    本模块提供提供了一系列MVP模式实现代码的封装，并且使用[retrofit](https://github.com/square/retrofit) + [RxJava2](https://github.com/ReactiveX/RxJava)
    实现了网络请求。默认使用[AutoDispose](https://github.com/uber/AutoDispose)来实现订阅对象随Activity的销毁而自动取消，防止内存泄漏
    
- ### 使用
    - 首先引入依赖
        ```groovy
        // 所有的模块都依赖于 quick_android_base 
        implementation 'com.github.SunnyQjm.quickandroid:quick_android_base:${last_version}'
      
        // 引入quick_android_mvp模块，来使用网络请求和MVP架构
        implementation 'com.github.SunnyQjm.quickandroid:quick_android_mvp:${last_version}'
        ```
    - 初始化
        ```kotlin
        
        /**
         * QuickAndroidMVP 模块初始化操作
         *
         * @param context                       Application 对象
         * @param baseUrl                       网络请求的前缀， eg.: https://example.cn/
         * @param enablePersistentCookieJar     是否开启Cookie的自动持久化和发送
         */
        QuickAndroidMVP
          .init(
              context, baseUrl, enablePersistentCookieJar
          )
          
      
        // 当然，如果使用Kotlin开发也可以使用下面的统一扩展接口，参数说明同上
        QuickAndroid.initMVP(
            context, baseUrl, enablePersistentCookieJar
        )
        ```
    具体MVP架构的使用方式参考本项目的[demo](https://github.com/SunnyQjm/quickandroid/tree/master/app/src/main/java/cn/qjm253/quick_android/mvp_demo)

- ### 如何使用网络请求？
    - 首先定义网络请求的Service接口（具体定义规则参看[Retrofit 官网](https://square.github.io/retrofit/)）
        ```kotlin
        interface TestServices {
        
            @GET("api")
            fun getWeather(
                @Query("city") city: String = "成都",
                @Query("version") version: String = "v1"
            ): Observable<Weather>
        }
        ```
    - 使用
        ```kotlin
        QuickAndroidMVPAPIManager.getService(TestServices::class.java)
              .getWeather("成都")
              .compose(RxScheduler.io_main())
              .subscribe({        // onNext
        
              }, {                // onError
        
              }, {                // onComplete
        
              })
        
        ```

     轻松两步便可实现网络请求

- ### 如何对每一个网络请求添加监听？
    下面的所有接口都有对应 `QuickAndroid` 的扩展，如果使用`kotlin`开发的话，也可以使用`QuickAndroid.xxx`的方式来调用。
    
    - 添加一个可以监听所有网络请求的onComplete事件的监听器
        ```kotlin
        /**
         * 添加一个网络请求 onComplete 的拦截监听
         * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onComplete执行的时候都会触发
         *
         * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
         */
        QuickAndroidMVP.addOnAPICompleteListener(onComplete)
        
        
        // 可以通过下面的方法清空所有对onComplete事件的监听器
        QuickAndroidMVP.clearOnAPICompleteListeners()
        ```
        
    - 添加一个可以监听所有网络请求的onNext事件的监听器
        ```kotlin
        /**
         * 添加一个网络请求 onNext 的拦截监听
         * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onNext执行的之前都会触发
         *
         * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
         */
        QuickAndroidMVP.addOnAPICompleteListener(onBeforeNextListener)
        
        
        QuickAndroidMVP.clearOnAPIBeforeNextListeners()
        ```
        
    - 添加一个可以监听所有网络请求的onError事件的监听器
        ```kotlin
        /**
         * 添加一个网络请求 onError 的拦截监听
         * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onError执行的时候都会触发
         *
         * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
         */
        QuickAndroidMVP.addOnAPIErrorListener(onErrorListener)
        
        
        // 可以通过下面的方法清空所有对onError事件的监听器
        QuickAndroidMVP.clearOnAPIErrorListeners()
        ```
    
- ### 如何对网络请求的结果进行统一的处理
