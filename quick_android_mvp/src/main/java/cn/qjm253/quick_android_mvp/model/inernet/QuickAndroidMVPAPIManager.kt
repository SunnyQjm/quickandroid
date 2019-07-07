package cn.qjm253.quick_android_mvp.model.inernet

import android.app.Application
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
object QuickAndroidMVPAPIManager {

    val UTF8 = Charset.forName("UTF-8")
    val DEFAULT_CACHE_SIZE = 20 * 1024 * 1024
    val DEFAULT_TIME_OUT = 10L
    val services = mutableListOf<Any>()
    lateinit var client: OkHttpClient
    lateinit var baseUrl: String


    /**
     * 网络请求工具类初始化
     */
    fun init(
        application: Application,
        enablePersistentCookieJar: Boolean = true,     // 是否开启自动cookie保存和提交
        baseUrl: String = ""
    ) {
        val okhttpBuilder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)

        if (enablePersistentCookieJar) {
            okhttpBuilder.cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(application)))
        }

        this.baseUrl = baseUrl
        client = okhttpBuilder
            .build()
    }

    /**
     * 注册一个服务，支持Json解析
     */
    fun <T> registerService(serviceClass: Class<T>): T {
        val newService = createService(serviceClass, GsonConverterFactory.create())
        services.add(newService!!)
        return newService
    }

    fun <T> getService(serviceClass: Class<T>): T {
        var result: T? = null
        services.forEach {
            if(it as? T != null ) {
                result = it
            }
        }
        return result ?: registerService(serviceClass)
    }

    fun <T> createService(serviceClass: Class<T>, vararg factory: Converter.Factory): T {
        val build = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
        for (aFactory in factory) {
            build.addConverterFactory(aFactory)
        }

        val retrofit = build
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(serviceClass)
    }
}