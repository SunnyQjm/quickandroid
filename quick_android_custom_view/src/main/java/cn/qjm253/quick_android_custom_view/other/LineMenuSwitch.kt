package cn.qjm253.quick_android_custom_view.other

import android.widget.CompoundButton
import android.content.Context
import android.widget.RelativeLayout
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import cn.qjm253.quick_android_custom_view.R


/**
 * @author SunnyQjm
 * @date 19-7-14 下午8:30
 */
class LineMenuSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var menuTitle: String? = null
    @ColorInt
    private var menuTitleColor: Int = 0
    private var menuTitleSize: Float = 0.toFloat()

    @DrawableRes
    private var switchThumb: Int = 0
    @DrawableRes
    private var switchTrack: Int = 0

    private var leftMargin: Int = 0
    private var rightMargin: Int = 0

    private var tv_title: TextView? = null
    private var aSwitch: Switch? = null
    private var checked = false
    private var onCheckedChangeListener: CompoundButton.OnCheckedChangeListener? = null

    init {
        getAttr(context, attrs, defStyleAttr)
        initView()
    }

    private fun initView() {
        tv_title = TextView(context)
        tv_title!!.text = menuTitle
        tv_title!!.setTextColor(menuTitleColor)
        tv_title!!.textSize = menuTitleSize
        tv_title!!.setBackgroundResource(R.color.transparent)
        val title_param = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        title_param.addRule(CENTER_VERTICAL)
        title_param.leftMargin = leftMargin
        tv_title!!.layoutParams = title_param
        addView(tv_title, title_param)

        aSwitch = Switch(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            aSwitch!!.setThumbResource(switchThumb)
            aSwitch!!.setTrackResource(switchTrack)
        }
        val switch_param = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            switch_param.addRule(ALIGN_PARENT_END)
        }
        switch_param.addRule(ALIGN_PARENT_RIGHT)
        switch_param.addRule(CENTER_VERTICAL)
        switch_param.rightMargin = rightMargin
        addView(aSwitch, switch_param)
        aSwitch!!.isChecked = checked
        aSwitch!!.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private fun getAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LineMenuSwitch)
        menuTitle = ta.getString(R.styleable.LineMenuSwitch_menu_title)
        menuTitleSize = ta.getFloat(R.styleable.LineMenuSwitch_menu_title_size, DEFAULT_TEXT_SIZE.toFloat())
        menuTitleColor = ta.getColor(
            R.styleable.LineMenuSwitch_menu_title_color,
            resources.getColor(DEFAULT_TITLE_COLOR)
        )
        leftMargin = ta.getDimensionPixelSize(
            R.styleable.LineMenuSwitch_left_margin,
            dip2px(context, DEFAULT_MARGIN)
        )
        rightMargin = ta.getDimensionPixelOffset(
            R.styleable.LineMenuSwitch_right_margin,
            dip2px(context, DEFAULT_MARGIN)
        )
        switchThumb = ta.getResourceId(R.styleable.LineMenuSwitch_switch_thumb, R.drawable.switch_thumb)
        switchTrack = ta.getResourceId(R.styleable.LineMenuSwitch_switch_track, R.drawable.switch_track)
        ta.recycle()
    }

    fun setChecked(checked: Boolean) {
        this.checked = checked
        if (aSwitch != null)
            aSwitch!!.isChecked = checked
    }

    fun setOnCheckedChangeListener(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener
        if (aSwitch != null)
            aSwitch!!.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    companion object {

        private val DEFAULT_TEXT_SIZE = 14
        private val DEFAULT_MARGIN = 13f

        private val DEFAULT_TITLE_COLOR = R.color.text_gray
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}