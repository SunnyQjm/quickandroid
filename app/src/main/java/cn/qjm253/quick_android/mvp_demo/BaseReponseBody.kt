package cn.qjm253.quick_android.mvp_demo

class BaseReponseBody<T>(
    val code: Int,
    val data: T
)