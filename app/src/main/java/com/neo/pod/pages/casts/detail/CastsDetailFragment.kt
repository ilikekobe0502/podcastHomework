package com.neo.pod.pages.casts.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.neo.pod.AppInjector
import com.neo.pod.R
import com.neo.pod.constants.Page
import com.neo.pod.pages.base.InteractionView
import com.neo.pod.pages.base.OnPageInteractionListener
import com.neo.pod.repository.data.ContentFeed
import com.neo.pod.repository.data.PodCastCollection
import kotlinx.android.synthetic.main.fragment_casts_detail.*

const val TAG_PICTURE = "com.neo.pod.pages.casts.detail.picture"
const val TAG_PLAY_INFO = "com.neo.pod.pages.casts.detail.play_info"

class CastsDetailFragment : InteractionView<OnPageInteractionListener.Primary>(), View.OnClickListener {

    private lateinit var mViewModel: CastsDetailViewModel
    private var adapter: CastsDetailRecyclerViewAdapter = CastsDetailRecyclerViewAdapter()
    private var pictureUrl = ""

    companion object {
        fun newInstance(): CastsDetailFragment = CastsDetailFragment()
        private val TAG = CastsDetailFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = AppInjector.obtainViewModel(this)
        mViewModel.detailResult.observe(this, Observer { result ->
            result?.let { getDetailSuccess(it) }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_casts_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_content?.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }
        adapter.listener = this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(v: View?) {
        v?.apply {
            val b = Bundle()
            b.putString(TAG_PICTURE, pictureUrl)
            b.putSerializable(TAG_PLAY_INFO, tag as ContentFeed)
            getInteractionListener().addPage(R.id.fragment_container, Page.CASTS_PLAY, b, true, true)
        }
    }

    private fun getDetailSuccess(result: PodCastCollection) {
        result.apply {
            pictureUrl = artworkUrl600
            context?.let { Glide.with(it).load(artworkUrl100).into(imageView_picture) }
            textView_artist_name.text = artistName
            textView_name.text = collectionName
            adapter.setData(result.contentFeed)
        }
    }
}