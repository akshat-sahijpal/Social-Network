package com.akshatsahijpal.crud.adapter.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comment_architecture.view.*

class CommentListAdapter :
    RecyclerView.Adapter<CommentListAdapter.CommentViewViewHolder>() {

    inner class CommentViewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    val comparator = object: DiffUtil.ItemCallback<CommentData>() {
        override fun areItemsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem.commentTime == newItem.commentTime
        }

        //hashCode to compare the hash value of the items
        override fun areContentsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ= AsyncListDiffer(this, comparator)

    fun submitList(list:List<CommentData>)= differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_architecture,parent, false)
        return CommentViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewViewHolder, position: Int) {
        val comment= differ.currentList[position]
        holder.itemView.apply {
            Picasso.get().load(comment.profilePicURL ?: Constants.DefaultProfilePhoto)
                .into(profilePicture)
            profileName.text = comment.profileUserName
            profileUserName.text = comment.profileUserName
            mainCommentText.text = comment.mainCommentContent
            uploadTime.text = comment.commentTime?.subSequence(0, 16)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

