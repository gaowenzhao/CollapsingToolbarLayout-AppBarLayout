package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import com.example.myapplication.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.reflect.Field
import kotlin.math.abs
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK)
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, verticalOffset ->
            var percent = (abs(verticalOffset * 1.0f) / p0.totalScrollRange)
            toolbar.background.alpha = (255 * percent).roundToInt()
            Log.i("test","percent=$percent")
        })
    }

    override fun onStart() {
        super.onStart()
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        showStatusBar()
        //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        SYSTEM_UI_FLAG_LAYOUT_STABLE
        // activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onPause() {
        super.onPause()

    }

    //全屏并且状态栏透明显示
    private fun showStatusBar() {
        val attrs = activity!!.window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        activity!!.window.attributes = attrs
    }

    //全屏并且隐藏状态栏
    private fun hideStatusBar() {
        val attrs = activity!!.window.getAttributes()
        attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        activity!!.window.setAttributes(attrs)
    }

    //获取手机状态栏高度
    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.getResources().getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return statusBarHeight
    }
    /* private void setAvatorChange() {
         appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
             @Override
             public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                 //verticalOffset始终为0以下的负数
                 float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
 //toolbar渐变效果：
                 toolbar.getBackground().setAlpha(Math.round(255percent));
 //toolbar渐变效果：如果使用这种方式，还没开始滑动时出现看不见toolbar内容
 //toolbar.setAlpha(percent);
             }
         });
     }*/

}