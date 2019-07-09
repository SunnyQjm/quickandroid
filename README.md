# quickandroid
> QuickAndroid一个Android快速开发的模板库，支持开箱即用的扫码、图片选择、MVP架构模式、全功能的下拉刷新和上拉加载更多（支持自定义刷新头）等等多项功能，可以根据需要引入不同的库实现不同的功能

[![](https://jitpack.io/v/SunnyQjm/quickandroid.svg)](https://jitpack.io/#SunnyQjm/quickandroid)

> ## 依赖使用

```groovy
// 所有的模块都依赖于 quick_android_base （必选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_base:${last_version}'
  
// 引入quick_android_mvp模块，来使用网络请求和MVP架构（可选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_mvp:${last_version}'

// 引入quick_android_image_picker模块，来接入图片选择，图片裁剪等功能（可选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_image_picker:${last_version}'

// 引入quick_android_qrcode模块，来接入扫码功能（可选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_qrcode:${last_version}'

// 引入quick_android_rx_permission模块，来接入链式调用动态请求权限的功能（可选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_rx_permission:${last_version}'

// 引入quick_android_webview模块，来接入H5容器能力（可选）
implementation 'com.github.SunnyQjm.quickandroid:quick_android_webview:${last_version}'
```



>  ## 各模块说明文档


- [quick_android_base模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_base)
- [quick_android_image_picker模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_image_picker)
- [quick_android_mvp模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_mvp)
- [quick_android_qrcode模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_qrcode)
- [quick_android_rx_permission模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_rx_permission)
- [quick_android_webview模块文档](https://github.com/SunnyQjm/quickandroid/tree/master/quick_android_webview)

> ## 一些说明

- 本库仅支持`androidx`，不能与使用`support`库的项目一起使用，如果需要与support库一起使用，请自行fork进行迁移