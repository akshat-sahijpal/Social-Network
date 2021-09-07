package com.akshatsahijpal.crud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.data.CommentArchitectureData
import com.akshatsahijpal.crud.databinding.CommentArchitectureBinding

class CommentRecyclerAdapter constructor(private val demo: List<CommentArchitectureData>) :
    RecyclerView.Adapter<CommentRecyclerAdapter.Holder>() {
    inner class Holder(var commentView: CommentArchitectureBinding) :
        RecyclerView.ViewHolder(commentView.root) {
        fun bind(comment: CommentArchitectureData) {
            commentView.apply {
                profileName.text = comment.profileName
                profileUserName.text = comment.userName
                mainCommentText.text = comment.commentText
                uploadTime.text = comment.commentTime
            }
        }
    }

    override fun getItemCount(): Int {
        return demo.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            CommentArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(demo[position])
    }

}