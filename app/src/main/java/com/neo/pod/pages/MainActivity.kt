package com.neo.pod.pages

import android.os.Bundle
import com.neo.pod.R
import com.neo.pod.constants.Page
import com.neo.pod.pages.base.OnPageInteractionListener
import com.neo.pod.pages.base.PaneViewActivity

class MainActivity : PaneViewActivity(), OnPageInteractionListener.Primary {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        var intent = intent

        var args = intent.extras
        var page = args?.getInt("page")
        switchPage(R.id.fragment_container, Page.CASTS, Bundle(), true, false)
    }

    private fun init() {

    }
}

