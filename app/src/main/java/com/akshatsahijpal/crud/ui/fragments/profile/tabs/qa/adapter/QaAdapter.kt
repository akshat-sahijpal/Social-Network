package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.ProfilePostArchitectureBinding
import com.akshatsahijpal.crud.util.Constants
import com.squareup.picasso.Picasso

class QaAdapter :
    PagingDataAdapter<PostFeedData, QaAdapter.Holder>(compareList) {
    private lateinit var navController: NavController

    companion object {
        val compareList = object : DiffUtil.ItemCallback<PostFeedData>() {
            override fun areItemsTheSame(oldItem: PostFeedData, newItem: PostFeedData): Boolean {
                return oldItem.postUserName == newItem.postUserName
            }

            override fun areContentsTheSame(oldItem: PostFeedData, newItem: PostFeedData): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class Holder(
        private var _bind: ProfilePostArchitectureBinding,
    ) :
        RecyclerView.ViewHolder(_bind.root) {
        fun connect(post: PostFeedData) {
            _bind.let {
                it.profileName.text = post.postProfileName
                it.uploadTime.text = post.postUploadTime
                it.mainPostParagraph.text = post.postMainParagraph
                Picasso.get().load(post.postProfilePicture).into(it.profilePicture)
                if (post.postProfilePicture == null) {
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(it.profilePicture)
                }
                it.profilePicture.setOnClickListener {
                    // toProfile(post, _bind.root)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentPost = getItem(position)
        if (currentPost != null) {
            holder.connect(currentPost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            ProfilePostArchitectureBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return Holder(view)
    }
}