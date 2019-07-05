package cn.qjm253.quick_android_base.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * 网络工具类
 * Created by sunny on 18-2-23.
 */

class NetworkUtil(context: Context){

    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * 检测网络是否连接
     * @return
     */
    private fun checkNetworkState(): Boolean {
        var flag = false
        //得到网络连接信息
        //去进行判断网络是否连接
        if (manager.activeNetworkInfo != null) {
            flag = manager.activeNetworkInfo.isAvailable
        }
        return flag
    }

    /**
     * 判断是否连了wifi
     */
    fun isWifiConnect(): Boolean{
        val wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state
        return wifiState == NetworkInfo.State.CONNECTED
    }

    /**
     * 获取当前网络的类型
     */
    fun getConnectedType(context: Context?): Int {
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                return mNetworkInfo.type
            }
        }
        return -1
    }


    /**
     * 是否是移动数据连接
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }


}