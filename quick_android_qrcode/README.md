> ## quick_android_qrcode 模块说明文档
>
> 本模块基于[**BGAQRCode-Android**](https://github.com/bingoogolapple/BGAQRCode-Android)进行进一步的封装，支持使用RxJava链式调用的方式进行扫码并获得扫码的结果（不用复写`onActivityResult`来手动获取结果）



> ### 使用

```kotlin
QuickAndroidQrCode
	.create(context)		// context 可以是Activity或者Fragment
	.scanQrCode()
	.subscribe({qrCodeResult->			// 扫码成功
    
    }, { err->							// 扫码失败（权限被拒绝，摄像头打开失败等）
        
    })

// 当然，如果使用Kotlin开发也可以使用下面的统一扩展接口
QuickAndroid
	.scanQrCode(context)
	.subscribe({qrCodeResult->			// 扫码成功
    
    }, { err->							// 扫码失败（权限被拒绝，摄像头打开失败等）
        
    })
```



> ### 模块说明

- 本模块提供了一个简单的[QuickAndroidQrCodeActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_qrcode/src/main/java/cn/qjm253/quick_android_qrcode/activity/QuickAndroidQrCodeActivity.kt)实现扫码界面，可以直接使用
- 如果希望自定义扫码界面，也可以参考[QuickAndroidQrCodeActivity](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_qrcode/src/main/java/cn/qjm253/quick_android_qrcode/activity/QuickAndroidQrCodeActivity.kt)复用本模块提供的[QuickAndroidQrCodeFragment](https://github.com/SunnyQjm/quickandroid/blob/master/quick_android_qrcode/src/main/java/cn/qjm253/quick_android_qrcode/fragment/QuickAndroidQrCodeFragment.kt)来实现自定义的界面

