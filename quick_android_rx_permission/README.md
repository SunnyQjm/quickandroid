> ## quick_android_rx_permission 模块说明文档
>
> 本模块基于[easypermissions](https://github.com/googlesamples/easypermissions)进行封装，实现了RxJava链式调用进行动态权限请求，不需要显示的复写权限请求回调，请求逻辑和请求结果可以在同一代码段，不用分开编写。（封装实现时参考了[RxPermissions](https://github.com/tbruyelle/RxPermissions)项目）



> ### 使用示例

```kotlin
QuickAndroidRxPermission
	.create(context)			// 这个context可以是Activity，也可以是Fragment
	.request(
        arrayof(Manifest.android.CAMERA)
    )
	.subscribe {
        if(it.granted) {		// 权限请求通过
            
        } else {				// 权限请求拒绝
            
        }
	}

// 当然，如果使用Kotlin开发也可以使用下面的统一扩展接口
QuickAndroid
	.createRxPermission(context)
	.request(
        arrayof(Manifest.android.CAMERA)
    )
	.subscribe {
        if(it.granted) {		// 权限请求通过
            
        } else {				// 权限请求拒绝
            
        }
	}
```

