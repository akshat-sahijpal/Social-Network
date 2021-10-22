package com.akshatsahijpal.crud.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.data.CommentArchitectureData
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.databinding.CommentArchitectureBinding

class CommentRecyclerAdapter constructor(private val demo: List<CommentData>) :
    RecyclerView.Adapter<CommentRecyclerAdapter.Holder>() {

    inner class Holder(var commentView: CommentArchitectureBinding) :
        RecyclerView.ViewHolder(commentView.root) {
        fun bind(comment: CommentData) {
            commentView.apply {
                profileName.text = comment.profileUserName
                profileUserName.text = comment.profileUserName
                mainCommentText.text = comment.mainCommentContent
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