package cn.qjm253.quick_android_base.extensions

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by sunny on 17-12-28.
 */

object MyDelegates{
    fun <T> notNullAndOnlyInitFirstTime(): ReadWriteProperty<Any?, T> = NotNullAndOnlyInitFirstTime()
}

/**
 * 本代理类代理的属性，不可为null，可延迟初始化，但是只会被初始化一次，第一次初始化之后的set操作都将被忽略
 */
private class NotNullAndOnlyInitFirstTime<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    /**
     * get代理
     * @param thisRef   被代理的对象
     * @param property  保存了被代理对象的名字等
     */
    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            value ?: throw IllegalStateException("${property.name} is not initialized!")

    /**
     * set代理
     * @param thisRef   被代理的对象
     * @param property  保存了被代理对象的名字等
     * @param value     元数据
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(this.value == null)
            this.value = value
    }

}