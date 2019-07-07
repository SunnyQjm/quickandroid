package cn.qjm253.quick_android_base.base.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

/**
 * 支持在第一次创建视图时初始化，且不会重复添加导致重叠
 */
abstract class BaseQuickAndroidFragment : Fragment() {
    companion object {
        private const val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }

    protected var mView: View? = null
    protected var mListener: OnFragmentInteractionListener? = null

    // 当前视图是否构建完成
    private var prepare: Boolean = false
    // 是否是第一次加载数据
    private var first: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //当页面重启时，Fragment会被保存回复，而此时再加载Fragment便会导致重叠
        if (savedInstanceState != null) {
            val isHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager?.beginTransaction()
            if (isHidden)
                ft?.hide(this)
            else
                ft?.show(this)
            ft?.commit()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null) {
            mView = inflater.inflate(getRes(), container, false)
        }

        // 如果之前被添加过，则先移除
        if(mView?.parent != null) {
            (mView?.parent as ViewGroup).removeView(mView)
        }
        return mView
    }



    /**
     * 当视图构建完成时会调用这个回调
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!prepare) {
            initView()
            prepare = true
        }
    }


    /**
     * 下面的函数由系统调用
     * 在Fragment可见时加载数据
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser) {
            onVisible()
        } else {
            onInvisible()
        }
    }



    protected open fun onInvisible() {}

    protected open fun onVisible() {
        //加载数据
        if (prepare && first) {
            first = false
            initialLoadData()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    /**
     * Fragment页面的布局文件
     */
    abstract fun getRes(): Int

    /**
     * 视图初始化操作
     */
    abstract fun initView()

    /**
     * 页面第一次加载时加载数据
     */
    abstract fun initialLoadData()

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri?)
    }

}