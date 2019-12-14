package com.example.myapplication.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.appbar
import kotlinx.android.synthetic.main.fragment_home.toolbar
import kotlinx.android.synthetic.main.test1.*
import kotlin.math.abs
import kotlin.math.roundToInt

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.test1, container, false)
      /*  val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, verticalOffset ->
            var totalScrollRange = p0.totalScrollRange*2/3
            var absVerticalOffset = abs(verticalOffset * 1.0f)
            if(absVerticalOffset>totalScrollRange){
                absVerticalOffset = totalScrollRange.toFloat()
            }
            var percent = (absVerticalOffset / totalScrollRange)
            toolbar.background.alpha = (255 * percent).roundToInt()
            toolbartitle.alpha = percent
            lefttitle.alpha = 1-percent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Log.i("text","toolbar.background.alpha=${toolbar.background.alpha}")
            }
        })
    }
}