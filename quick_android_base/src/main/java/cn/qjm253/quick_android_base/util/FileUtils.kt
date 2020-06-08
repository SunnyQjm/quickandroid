package cn.qjm253.quick_android_base.util

import android.os.Environment
import cn.qjm253.quick_android_base.QuickAndroid
import java.io.File


/**
 * Created by tanshunwang on 2016/9/21 0021.
 */
object FileUtils {

    /**
     * 获得应用的根目录
     *
     * @return
     */
    //判断sd卡是否存在
    //获取根目录
    val appPath: String
        get() {
            val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            val sdDir = if (sdCardExist) {
                File(Environment.getExternalStorageDirectory().path + "/" + QuickAndroid.APP_NAME + "/")
            } else {
                File(Environment.getDataDirectory().toString() + "/" + QuickAndroid.APP_NAME + "/")
            }
            if (!sdDir.exists()) {
                sdDir.mkdirs()
            }
            return sdDir.toString()
        }

    val cachePath: String
        get() {
            val targetPath = "$appPath/Cache/"
            val file = File(targetPath)
            if (!file.exists())
                file.mkdirs()
            return targetPath
        }

    val recordPath: String
        get() {
            val targetPath = "$appPath/Record/"
            val file = File(targetPath)
            if (!file.exists())
                file.mkdirs()
            return targetPath
        }

    val picturePath: String
        get() {
            val targetPath = "$appPath/Picture/"
            val file = File(targetPath)
            if (!file.exists())
                file.mkdirs()
            return targetPath
        }

    val downloadPath: String
        get() {
            val targetPath = "$appPath/Download/"
            val file = File(targetPath)
            if (!file.exists())
                file.mkdirs()
            return targetPath
        }

    val wifiDirectPath: String
        get() {
            val path = "$appPath/WifiDirectFile/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            return path
        }


    /**
     * 获得SDCard的根目录
     *
     * @return
     */
    //判断sd卡是否存在
    val sdPath: String?
        get() {
            val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            return if (sdCardExist) {
                Environment.getExternalStorageDirectory().path
            } else {
                null
            }
        }


    val path: String
        get() {
            val path = "$appPath/header/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
                println("54555")
            }
            return path
        }

    fun generateRecordFile(fileName: String): File {
        return File(recordPath + File.separator + fileName)
    }

    fun generatePictureFile(fileName: String): File {
        return File(picturePath + File.separator + fileName)
    }


}
