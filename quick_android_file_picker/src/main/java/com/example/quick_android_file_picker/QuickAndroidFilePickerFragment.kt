package com.example.quick_android_file_picker

import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.util.ContentUriUtil
import com.example.quick_android_file_picker.model.FilePickerResult
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * 这个fragment用于接收文件选择的结果
 */
class QuickAndroidFilePickerFragment : Fragment() {
    private var emitter: ObservableEmitter<FilePickerResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @RequiresPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    fun pickerFile(fileType: String = "*/*"): Observable<FilePickerResult> {
        return Observable.create<FilePickerResult> { _emitter ->
            this.emitter = _emitter

            // 调用系统的文件选择器
            Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = fileType
                this@QuickAndroidFilePickerFragment.startActivityForResult(this, 0)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let {
            it.path?.i()
            val path = ContentUriUtil.getPath(context, it)
            println(it.toString())
            emitter?.onNext(FilePickerResult(path, it))
            emitter?.onComplete()
        }
    }
}