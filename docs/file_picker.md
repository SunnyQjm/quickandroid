> ## quick_android_file_picker 模块说明文档
>
> 本模块简单封装了打开系统的文件管理器选择文件的功能，并返回文件的原始Uri以及经过处理后文件的绝对路径。
>
> 通过RxJava用简单的链式调用即可获得选择文件的结果

- 返回数据结构

  ```kotlin
  data class FilePickerResult (
      val path: String,			// 文件的绝对路径		
      val uri: Uri				// 文件的Uri
  )
  ```

- ### 使用示例

  - 本接口需要读取文件的权限 => `READ_EXTERNAL_STORAGE`，权限的获取可以参考本库的另一个模块 [quick_android_rx_permission模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_rx_permission)

  - 包含权限请求的使用示例：

    ps: `checkMyPermission` 为 [quick_android_base模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_base) 模块提供的用于检查权限是否存在的 `Context` 类的扩展方法

    ```kotlin
    QuickAndroid
        .createRxPermission(this)
        .request(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
        .subscribe {
            if (it.granted) {
                checkMyPermission(Manifest.permission.READ_EXTERNAL_STORAGE, {
                    QuickAndroidFilePicker
                        .create(this)
                        .pickerFile()
                        .subscribe { fpr ->
                            toast(fpr.path)
                        }
                })
            }
        }
    
    //////////////////////////////////////////////////////////////
    //// 使用 QuickAndroid 扩展的用法
    //////////////////////////////////////////////////////////////
    QuickAndroid
        .createRxPermission(this)
        .request(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
        .subscribe {
            if (it.granted) {
                checkMyPermission(Manifest.permission.READ_EXTERNAL_STORAGE, {
                    QuickAndroid
                        .createFilePicker(this)
                        .pickerFile()
                        .subscribe { fpr ->
                            toast(fpr.path)
                        }
                })
            }
        }
    ```
