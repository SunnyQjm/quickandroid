package cn.qjm253.quick_android_base.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @author SunnyQjm
 * @date 19-7-8 下午3:42
 */

fun <T: Fragment> FragmentManager.getLazySingleton(tag: String, clazz: Class<T>): Lazy<T> {
    val fragmentManager = this
    return object : Lazy<T> {
        private var t: T? = null

        private fun _getFragment(): T {
            if(t == null) {
                t = getFragment(tag, clazz, fragmentManager)
            }
            return t!!
        }

        override val value: T
            get() = _getFragment()

        override fun isInitialized(): Boolean {
            return t != null
        }
    }
}


private fun <T: Fragment> getFragment(tag: String, clazz: Class<T>, fragmentManager: FragmentManager): T {
    var fragment: T? = fragmentManager.findFragmentByTag(tag) as? T
    if (fragment == null) {
        fragment = clazz.newInstance()
        fragmentManager
            .beginTransaction()
            .add(fragment, tag)
            .commitNow()
    }
    return fragment!!
}

