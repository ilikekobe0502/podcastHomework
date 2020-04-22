package com.neo.pod.pages.casts

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.neo.pod.R
import com.neo.pod.repository.data.PodCast
import com.neo.pod.utils.MiscUtils
import kotlinx.android.synthetic.main.item_casts.view.*

class CastsRecyclerViewAdapter : RecyclerView.Adapter<CastsRecyclerViewAdapter.ViewHolder>() {
    private var data: ArrayList<PodCast> = ArrayList()
    var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_casts, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = data[p1]
        val context = p0.itemView.context
        p0.itemView.apply {
            val param = imageView_picture.layoutParams
            param.width = MiscUtils.getScreenW(context as Activity) / 2
            imageView_picture.layoutParams = param
            Glide.with(context).load(item.artworkUrl100).into(imageView_picture)
            textView_artist_name.text = item.artistName
            textView_name.text = item.name
            setOnClickListener { v -> listener?.onClick(v) }
        }
    }

    fun setData(data: ArrayList<PodCast>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun modifyImageSize() {

        }
    }
}