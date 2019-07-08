package cn.qjm253.quick_android.mvp_demo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TestServices {

    @GET("api")
    fun getWeather(
        @Query("city") city: String = "成都",
        @Query("version") version: String = "v1"
    ): Observable<Weather>
}