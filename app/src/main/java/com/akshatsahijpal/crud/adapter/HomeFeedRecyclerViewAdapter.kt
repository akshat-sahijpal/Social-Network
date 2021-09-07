package com.akshatsahijpal.crud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostArchitectureBinding

class HomeFeedRecyclerViewAdapter constructor(
    private val demo: List<PostFeedData>,
    itemClk: ItemClickListener,
) :  RecyclerView.Adapter<HomeFeedRecyclerViewAdapter.Holder>() {
    private var itemClick: ItemClickListener = itemClk
    class Holder(private var _bind: PostArchitectureBinding,
    var itemClick: ItemClickListener) :
        RecyclerView.ViewHolder(_bind.root), View.OnClickListener {
        init {
            _bind.root.setOnClickListener(this)
        }
        fun connect(post: PostFeedData) {
            _bind.let {
                it.profileUserName.text = post.postUserName
                it.profileName.text = post.postProfileName
                it.uploadTime.text = post.postUploadTime
                it.mainPostParagraph.text = post.postMainParagraph
            }
        }
        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            itemClick.onItemClicked(position)
        }
    }
    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            PostArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val post: PostFeedData = demo[position]
        holder.connect(post)
    }


    override fun getItemCount(): Int {
        return demo.size
    }
}