package com.example.quick_android_file_picker

import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.extensions.getLazySingleton
import com.example.quick_android_file_picker.model.FilePickerResult
import io.reactivex.Observable

class QuickAndroidFilePicker(fragmentManager: FragmentManager) {
    companion object {
        const val TAG = "QuickAndroidFilePicker"

        fun create(activity: FragmentActivity): QuickAndroidFilePicker {
            return QuickAndroidFilePicker(activity.supportFragmentManager)
        }

        fun create(fragment: Fragment): QuickAndroidFilePicker {
            return QuickAndroidFilePicker(fragment.childFragmentManager)
        }
    }

    private var mRxAndroidFilePickerFragment: Lazy<QuickAndroidFilePickerFragment>

    init {
        mRxAndroidFilePickerFragment =
            fragmentManager.getLazySingleton(TAG, QuickAndroidFilePickerFragment::class.java)
    }

    @RequiresPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    fun pickerFile(fileType: String = "*/*"): Observable<FilePickerResult> {
        return mRxAndroidFilePickerFragment
            .value
            .pickerFile(fileType)
    }
}

//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////

fun QuickAndroid.createFilePicker(activity: FragmentActivity): QuickAndroidFilePicker {
    return QuickAndroidFilePicker(activity.supportFragmentManager)
}

fun QuickAndroid.createFilePicker(fragment: Fragment): QuickAndroidFilePicker {
    return QuickAndroidFilePicker(fragment.childFragmentManager)
}