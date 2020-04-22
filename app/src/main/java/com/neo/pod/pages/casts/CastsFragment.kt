package com.neo.pod.pages.casts

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neo.pod.AppInjector
import com.neo.pod.R
import com.neo.pod.constants.Page
import com.neo.pod.pages.base.InteractionView
import com.neo.pod.pages.base.OnPageInteractionListener
import com.neo.pod.repository.data.PodCastList
import kotlinx.android.synthetic.main.fragment_casts.*

class CastsFragment : InteractionView<OnPageInteractionListener.Primary>(),View.OnClickListener {

    private lateinit var mViewModel: CastsViewModel
    private var adapter: CastsRecyclerViewAdapter = CastsRecyclerViewAdapter()

    companion object {
        fun newInstance(): CastsFragment = CastsFragment()
        private val TAG = CastsFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = AppInjector.obtainViewModel(this)
        mViewModel.podCastListResult.observe(this, Observer {result->
            result?.let { getPodCastSuccess(it) }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_casts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_content?.let {
            it.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }
        adapter.listener = this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(v: View?) {
        getInteractionListener().addPage(R.id.fragment_container, Page.CASTS_DETAIL,Bundle(),true,true)
    }

    private fun getPodCastSuccess(result: PodCastList){
        adapter.setData(result.podcast)
    }
}