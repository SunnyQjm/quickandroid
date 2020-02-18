> ## quick_android_image_picker 模块使用说明
>
> 本模块基于[RxImagePicker](https://github.com/qingmei2/RxImagePicker)进行封装，并额外提供图片裁剪功能，提供简单的调用接口。关于`RxImagePicker`的更多使用说明，请参考[RxImagePicker](https://github.com/qingmei2/RxImagePicker)

- ### 使用

  - 简单使用

      ```kotlin
      
      /**
       * 打开知乎样式的图片选择器
       */
      QuickAndroidImagePicker
        .openZhihuStyleGallery(
              context,		
              ZhihuConfigurationBuilder(
                  MimeType.ofImage(),
                  false
              )
              .maxSelectable(1)
              .countable(true)
              .spanCount(4)
              .build()				// ICustomPickerConfiguration 类型
          )
        .subscribe({ result ->		
               // 将结果传给图片裁剪界面进行图片裁剪
               QuickAndroidImagePicker.startClipImage(context, result.uri)
        })
      
      ```
  - 其他功能
      ```kotlin
      /**
      * 打开知乎样式的图片选择器
      */
      QuickAndroidImagePicker
      .openWechatStyleGallery(
      	context,		
      	WechatConfigrationBuilder(
      		MimeType.ofImage(),
      		false
      		)
      	.maxSelectable(1)
      	.countable(true)
      	.spanCount(4)
      	.build()				// ICustomPickerConfiguration 类型
      )
      .subscribe({ result ->
      
      })
      
      /**
      * 打开摄像头拍摄
      */
      QuickAndroidImagePicker
      	.openCamera(context)
      	.subscribe({ result ->
      	})
      	
      /**
       * 跳转到裁剪页面裁剪图片
       * @param context
       * @param uri           Uri类型
       * @param multiple      裁剪后文件的压缩强度
       *        0.2 -> 50 ~ 100 KB
       *        0.3 -> 100 ~ 200 KB
       */
      QuickAndroidImagePicker
        	.startClipImage(context, uri, 0.3)
      
      /**
       * 跳转到裁剪页面裁剪图片
       * @param context
       * @param path          文件的绝对路径
       * @param multiple      裁剪后文件的压缩强度
       *        0.2 -> 50 ~ 100 KB
       *        0.3 -> 100 ~ 200 KB
       */
      QuickANdroidImagePicker
      	.startClipImage(context, path, 0.3)
      
      ```

      当然，上面的所有接口都有`QuickAndroid`对应的扩展，都可以用`QuickAndroid.xxx`来调用
