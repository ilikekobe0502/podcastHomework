package com.neo.pod.constants

import android.os.Bundle
import android.support.v4.app.Fragment
import com.neo.pod.pages.casts.CastsFragment
import com.neo.pod.pages.casts.detail.CastsDetailFragment
import com.neo.pod.pages.casts.play.CastsPlayFragment
import java.lang.IllegalArgumentException

object Page {

    const val PAGE = "page"
    const val ARG_PAGE = "com.neo.pod.constants.Page.ARG_PAGE"
    const val ARG_DATA = "com.neo.pod.constants.Page.ARG_DATA"

    const val INVALID_PAGE = -1

//    const val TEMPLATE = 1000
    const val CASTS = 1000
    const val CASTS_DETAIL = 1001
    const val CASTS_PLAY = 1002

    /*--------------------------------------------------------------------------------------------*/
    /* Helpers */
    fun tag(page: Int): String = "page_$page"

    fun view(page: Int, args: Bundle): Fragment {
        var result: Fragment

        when (page) {
            CASTS -> result = CastsFragment.newInstance()
            CASTS_DETAIL -> result = CastsDetailFragment.newInstance()
            CASTS_PLAY -> result = CastsPlayFragment.newInstance()
            else -> throw IllegalArgumentException("No match view! page = $page")
        }

        args.putInt(ARG_PAGE, page)
        putData(result, args)

        return result
    }

    private fun putData(fragment: Fragment, data: Bundle) {
        var args = fragment.arguments;
        if (args == null) {
            fragment.arguments = data
        } else {
            args.putAll(data)
        }
    }
}