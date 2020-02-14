package com.example.quick_android_file_picker.model

import android.net.Uri

data class FilePickerResult (
    val path: String,
    val uri: Uri
)