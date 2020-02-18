> ## quick_android_base 模块
>
> 本模块提供了一些常用的工具库，一些常用的Kotlin扩展，以及一些基本功能的封装

- ### 初始化

  ```kotlin
  // 在Application中初始化
  QuickAndroid.init()
  	// 下面这一行输入你应用的英文名，便于为你的应用创建对应名字的应用文件夹等等
  	.appName("your_app_name")		
  	// 执行下面一行可以开启应用Activity的侧滑返回支持
  	.enableParallaxBack(context)		
  ```

  

- ### 侧滑返回支持说明

  1. 所有需要支持侧滑返回的`Activity`都要继承 [BaseQuickAndroidActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) ；

  2. 如果继承自[BaseQuickAndroidActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) 的`Activity`需要按业务需求开启和关闭业务需求，可以使用下面两个函数（下面两个函数定义在[BaseQuickAndroidActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) 当中，所有集成自该类的Activity均可调用）：

     ```kotlin
     // 禁止侧滑返回
     disableParallaxBack()
     
     // 开启侧滑返回
     enableParallaxBack()
     ```

  3. 本侧滑返回使用的是[ParallaxBackLayout](https://github.com/anzewei/ParallaxBackLayout)，如果需要自定义侧滑模式，以及触发侧滑返回的位置等等，可以参考该库提供的配置。

- ### Glide支持

  1. `quick_android_base`库中已经包含了Glide依赖，引入本依赖库之后不需要再另外引入

  2. 当前`quick_android_base`中Glide的版本为`4.9.0`

  3. 已经定义了[MyAppGlideModule](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/MyAppGlideModule.kt)，不需要再定义，可以通过下面的方式使用

     ```kotlin
     GlideApp.with(context)
     	.load(url)
     	.into(imageView)
     ```

- ### 动态权限请求支持

  本功能基于google开源的[easypermissions](https://github.com/googlesamples/easypermissions)进行，在所有继承自[BaseQuickAndroidActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/activity/BaseQuickAndroidActivity.kt>) 的Activity或者继承自[BaseQuickAndroidFragment](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/base/fragment/BaseQuickAndroidFragment.kt)的Fragment均可使用

  - 动态请求权限

    ```kotlin
    easyRequestPermissions(
    	arrayof(Manifest.permissions.CAMERA),
        R.string.require_camera_permission_tip,
        0
    )
    ```

  - 复写如下函数

    ```kotlin
        /**
         * 授权成功
         */
        override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
            when(requestCode) {
                0 -> {		// 请求摄像头权限
                    
                }
            }
        }
    
        /**
         * 授权失败
         */
        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    
        }
    ```

- ### `quick_android_base`支持的其它特性

  - 一些有用的工具类 [utils](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/util)

  - 一些有用的扩展 [extensions](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_base/src/main/java/cn/qjm253/quick_android_base/extensions)

  - 便捷的页面跳转扩展（下面所有的扩展函数均可在所有Activity和Fragment中使用，只可在Kotlin项目中使用）

    ```kotlin
    // 简单使用，跳转到一个Activity
    jumpTo(MainActivity::class.java)
    
    // 跳转到一个Activity，并处理返回结果(requestCode = 0)
    jumpForResult(MainACtivity::class.java, 0)
    
    // 跳转到一个Activity并传递参数
    jumpTo(MainActivity::class.java, 
           IntentParam()
           	.add("param1", 1)
           	.add("param2", "value2")
           	.add("param3", arrayof("1", "2", "3"))
          )
    
    // 跳转到一个Activity，传递参数并处理返回结果
    jumpTo(MainActivity::class.java, 
           0,
           IntentParam()
           	.add("param1", 1)
           	.add("param2", "value2")
           	.add("param3", arrayof("1", "2", "3"))
          )
    ```

    

- ### `quick_android_base`引入的所有支持库

  ```groovy
      // gson
      api "com.google.code.gson:gson:${gson_version}"
  
      // Glide
      api "com.github.bumptech.glide:glide:${glide_version}"
      kapt "com.github.bumptech.glide:compiler:${glide_version}"
  
      // Easypermission
      api "pub.devrel:easypermissions:${easypermission_version}"
  
      // RxJava
      // RxAndroid
      api "io.reactivex.rxjava2:rxandroid:${rx_android_version}"
      api "io.reactivex.rxjava2:rxjava:${rx_java}"
      // RxKotlin
      api "io.reactivex.rxjava2:rxkotlin:${rx_kotlin}"
  
      // BRVAHquick_android_base
      api "com.github.CymChad:BaseRecyclerViewAdapterHelper:${brvah_version}"
  
      // Retrofit
      api "com.squareup.retrofit2:retrofit:${retrofit_version}"
      api "com.squareup.retrofit2:retrofit:${retrofit_version}"
      api "com.squareup.retrofit2:adapter-rxjava2:${retrofit_version}"
      api "com.squareup.retrofit2:converter-gson:${retrofit_version}"
  
      // Okhttp
      api "com.squareup.okhttp3:okhttp:${okhttp_version}"
  
      // Logger
      api "com.orhanobut:logger:${logger_vesion}"
  
      // ParallaxBackLayout
      api "com.github.SunnyQjm:ParallaxBackLayout:${parallax_layout_version}"
  
      // ImmersionBar
      api "com.gyf.immersionbar:immersionbar:${immersion_bar_version}"
  ```

  