package cn.qjm253.quick_android_base.params

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by sunny on 18-1-28.
 */
class IntentParam(val keys: MutableList<String> = mutableListOf(),
                  val values: MutableList<Any> = mutableListOf()) {

    fun add(key: String, value: Any?): IntentParam {
        if(value == null)
            return this
        keys.add(key)
        values.add(value)
        return this
    }

    fun applyParam(intent: Intent){
        for(i in 0 until keys.size){
            when(values[i]){
                is Boolean -> intent.putExtra(keys[i], values[i] as Boolean)
                is Byte -> intent.putExtra(keys[i], values[i] as Byte)
                is Char -> intent.putExtra(keys[i], values[i] as Char)
                is Short -> intent.putExtra(keys[i], values[i] as Short)
                is Int -> intent.putExtra(keys[i], values[i] as Int)
                is Long -> intent.putExtra(keys[i], values[i] as Long)
                is Float -> intent.putExtra(keys[i], values[i] as Float)
                is Double -> intent.putExtra(keys[i], values[i] as Double)
                is String -> intent.putExtra(keys[i], values[i] as String)
                is CharSequence -> intent.putExtra(keys[i], values[i] as CharSequence)
                is Parcelable -> intent.putExtra(keys[i], values[i] as Parcelable)
                is BooleanArray -> intent.putExtra(keys[i], values[i] as BooleanArray)
                is ByteArray -> intent.putExtra(keys[i], values[i] as ByteArray)
                is CharArray -> intent.putExtra(keys[i], values[i] as CharArray)
                is ShortArray -> intent.putExtra(keys[i], values[i] as ShortArray)
                is IntArray -> intent.putExtra(keys[i], values[i] as IntArray)
                is LongArray -> intent.putExtra(keys[i], values[i] as LongArray)
                is FloatArray -> intent.putExtra(keys[i], values[i] as FloatArray)
                is DoubleArray -> intent.putExtra(keys[i], values[i] as DoubleArray)
                is Serializable -> intent.putExtra(keys[i], values[i] as Serializable)
                is Array<*> -> intent.putExtra(keys[i], values[i] as Array<out String>)
            }
        }
    }

    fun applyBundle(bundle: Bundle) {
        for(i in 0 until keys.size){
            when(values[i]){
                is Boolean -> bundle.putBoolean(keys[i], values[i] as Boolean)
                is Byte -> bundle.putByte(keys[i], values[i] as Byte)
                is Char -> bundle.putChar(keys[i], values[i] as Char)
                is Short -> bundle.putShort(keys[i], values[i] as Short)
                is Int -> bundle.putInt(keys[i], values[i] as Int)
                is Long -> bundle.putLong(keys[i], values[i] as Long)
                is Float -> bundle.putFloat(keys[i], values[i] as Float)
                is Double -> bundle.putDouble(keys[i], values[i] as Double)
                is String -> bundle.putString(keys[i], values[i] as String)
                is CharSequence -> bundle.putCharSequence(keys[i], values[i] as CharSequence)
                is Parcelable -> bundle.putParcelable(keys[i], values[i] as Parcelable)
                is BooleanArray -> bundle.putBooleanArray(keys[i], values[i] as BooleanArray)
                is ByteArray -> bundle.putByteArray(keys[i], values[i] as ByteArray)
                is CharArray -> bundle.putCharArray(keys[i], values[i] as CharArray)
                is ShortArray -> bundle.putShortArray(keys[i], values[i] as ShortArray)
                is IntArray -> bundle.putIntArray(keys[i], values[i] as IntArray)
                is LongArray -> bundle.putLongArray(keys[i], values[i] as LongArray)
                is FloatArray -> bundle.putFloatArray(keys[i], values[i] as FloatArray)
                is DoubleArray -> bundle.putDoubleArray(keys[i], values[i] as DoubleArray)
                is Serializable -> bundle.putSerializable(keys[i], values[i] as Serializable)
            }
        }
    }
}