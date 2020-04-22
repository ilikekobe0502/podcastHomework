package com.neo.pod.pages.casts.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neo.pod.R
import com.neo.pod.repository.data.ContentFeed
import kotlinx.android.synthetic.main.item_casts_detail.view.*

class CastsDetailRecyclerViewAdapter : RecyclerView.Adapter<CastsDetailRecyclerViewAdapter.ViewHolder>() {
    private var data: ArrayList<ContentFeed> = ArrayList()
    var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_casts_detail, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = data[p1]
        p0.itemView.apply {
            textView_title.text = item.title
            setOnClickListener { v ->
                v.tag = item
                listener?.onClick(v)
            }
        }
    }

    fun setData(data: ArrayList<ContentFeed>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}