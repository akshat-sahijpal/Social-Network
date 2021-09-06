package com.akshatsahijpal.crud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostArchitectureBinding

class HomeFeedRecyclerViewAdapter constructor(val demo: List<PostFeedData>): RecyclerView.Adapter<HomeFeedRecyclerViewAdapter.Holder>() {
    class Holder(private var _bind: PostArchitectureBinding) : RecyclerView.ViewHolder(_bind.root){
        fun connect(post: PostFeedData) {
            _bind.let {
                   it.profileUserName.text  = post.postUserName
                   it.profileName.text = post.postProfileName
                   it.uploadTime.text = post.postUploadTime
                   it.mainPostParagraph.text = post.postMainParagraph
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = PostArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var post: PostFeedData = demo[position]
        holder.connect(post)
    }

    override fun getItemCount(): Int {
        return demo.size
    }
}