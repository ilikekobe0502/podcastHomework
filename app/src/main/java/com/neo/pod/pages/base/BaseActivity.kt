package com.neo.pod.pages.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), OnPageInteractionListener.Base {
    private val TAG = BaseActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun pressBack() {
        if (isActivityDestroying())
            return
        onBackPressed()
    }

    override fun showFullScreenLoading() {
    }

    override fun hideFullScreenOverlay() {
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    fun isActivityDestroying(): Boolean {
        if (isFinishing)
            return true
        return false
    }
}