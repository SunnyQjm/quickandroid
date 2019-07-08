# quickandroid
> QuickAndroid一个Android快速开发的模板库，支持开箱即用的扫码、图片选择、MVP架构模式、全功能的下拉刷新和上拉加载更多（支持自定义刷新头）等等多项功能，可以根据需要引入不同的库实现不同的功能

[![](https://jitpack.io/v/SunnyQjm/quickandroid.svg)](https://jitpack.io/#SunnyQjm/quickandroid)


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
    - 