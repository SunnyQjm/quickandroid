> # quickandroid
> QuickAndroid一个Android快速开发的模板库，支持开箱即用的扫码、图片选择、MVP架构模式、全功能的下拉刷新和上拉加载更多（支持自定义刷新头）等等多项功能，可以根据需要引入不同的库实现不同的功能

[![](https://jitpack.io/v/SunnyQjm/quickandroid.svg)](https://jitpack.io/#SunnyQjm/quickandroid)


> ## quick_android_mvp模块
> 本模块提供了一个快速实现MVP架构的框架，并且封装了网络请求（Retrofit + RxJava）


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
        QuickAndroidMVP.init(
          context, baseUrl, enablePersistentCookieJar, enableParallaxBack
        )
  
        ```

