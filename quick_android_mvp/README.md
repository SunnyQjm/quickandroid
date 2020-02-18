> ## quick_android_mvp模块
>
> 本模块提供了一个快速实现MVP架构的框架，并且封装了网络请求（Retrofit + RxJava）

- ### 说明

  本模块提供提供了一系列MVP模式实现代码的封装，并且使用[retrofit](https://github.com/square/retrofit) + [RxJava2](https://github.com/ReactiveX/RxJava)
  实现了网络请求。默认使用[AutoDispose](https://github.com/uber/AutoDispose)来实现订阅对象随Activity的销毁而自动取消，防止内存泄漏

- ### 使用

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

  下面的所有接口都有对应 `QuickAndroid` 的扩展，如果使用`kotlin`开发的话，也可以使用`QuickAndroid.xxx`的方式来调用。**所有的网络请求，只有使用`qaSubscribe`扩展进行订阅的才会调用下面的回调**

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

  - 使用要求

    - 所有的网络请求都必须使用`qaHandleResult()`扩展

      ```kotlin
      Observable.just(BaseQuickAndroidResponseBody(-2))
                  .qaHandleResult()
                  .qaSubscribe(mView, {
      
                  })
      ```

    - 网络请求的Model数据类需要继承自[BaseQuickAndroidResponseBody](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_mvp/src/main/java/cn/qjm253/quick_android_mvp/model/response/BaseQuickAndroidResponseBody.kt)，里面包含一个`code`属性标识网络请求的状态码

  - 使用

    网络请求成功的情况下，通常返回的结果中会包含状态码（状态码一般又分为请求正常、请求错误等等），如果需要对网络请求的结果进行统一处理，便可以使用如下的方式

    ```kotlin
    QuickAndroidMVP
    	.registerAPIResultDeal(object : QuickAndroidMVPResultDeal(
                    // 包含所有网络请求失败的code
            		arrayOf(
                        -1 to "网络请求失败"
                    ),							
                    
            		// 包含所有请求成功的code
            		arrayOf(
                        0 to "网络请求成功"
                    ),							
                    
            		// token 失效，需要重新获取token之后重新发起请求的code
            		arrayOf(
                        -2 to "Token失效"
                    )							
                ) {
                    override fun dealRetry(t: Throwable): Observable<Int> {
                        t.e("dealRetry")
                        return Observable.error(t)
                    }
    
                    override fun normalDeal(code: Int, description: String) {
                        "normalDeal => code: $code, description: $description".i()
                    }
    
                    override fun errorDeal(code: Int, description: String) {
                        "errorDeal => code: $code, description: $description".e()
                    }
    
                })
    ```

    

  