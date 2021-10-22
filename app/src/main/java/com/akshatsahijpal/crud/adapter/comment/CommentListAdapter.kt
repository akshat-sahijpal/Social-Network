package com.akshatsahijpal.crud.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.databinding.CommentArchitectureBinding
import com.akshatsahijpal.crud.util.Constants
import com.squareup.picasso.Picasso

class CommentListAdapter :
    ListAdapter<CommentData, CommentListAdapter.CommentViewViewHolder>(Comparator) {
    inner class CommentViewViewHolder(var commentView: CommentArchitectureBinding) :
        RecyclerView.ViewHolder(commentView.root) {
        fun bind(comment: CommentData) {
            commentView.apply {
                Picasso.get().load(comment.profilePicURL ?: Constants.DefaultProfilePhoto)
                    .into(profilePicture)
                profileName.text = comment.profileUserName
                profileUserName.text = comment.profileUserName
                mainCommentText.text = comment.mainCommentContent
                uploadTime.text = comment.commentTime
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<CommentData>() {
        override fun areItemsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem.commentTime == newItem.commentTime
        }

        override fun areContentsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem.commentTime == newItem.commentTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewViewHolder {
        val view =
            CommentArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}