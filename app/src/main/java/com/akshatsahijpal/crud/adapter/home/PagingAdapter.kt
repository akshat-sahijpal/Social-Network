package com.akshatsahijpal.crud.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostArchitectureBinding
import com.akshatsahijpal.crud.util.Constants
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class PagingAdapter constructor(private var itemClk: CheckClickerForPost) :
    PagingDataAdapter<PostFeedData, PagingAdapter.Holder>(compareList) {
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
        private var _bind: PostArchitectureBinding
    ) :
        RecyclerView.ViewHolder(_bind.root) {
        fun connect(post: PostFeedData, clickListener: (data: PostFeedData) -> Unit) {
            _bind.root.setOnClickListener { clickListener(post) }
            _bind.let {
                it.profileUserName.text = post.postUserName
                //it.profileName.text = "@${post.postProfileName}"
                it.uploadTime.text = post.postUploadTime?.subSequence(startIndex = 0, endIndex = 16)
                it.mainPostParagraph.text = post.postMainParagraph
                Picasso.get().load(post.postProfilePicture).into(it.profilePicture)
                val postPicture = post.postAddPhoto
                Log.d("Displaying this image->", "${postPicture}")
                if (postPicture != null) {
                    it.iCv.visibility = View.VISIBLE
                    Picasso.get().load(postPicture.trim()).into(it.PostImage)
                    Log.d("Displaying this image->", "${post.postAddPhoto}")
                    Glide.with(_bind.root).load(post.postAddPhoto).centerCrop().into(it.PostImage)
                }
                if (post.postProfilePicture == null) {
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(it.profilePicture)
                }
                it.profilePicture.setOnClickListener {
                    // toProfile(post, _bind.root)
                }
            }
        }

        private fun toProfile(post: PostFeedData, root: CardView) {
            navController = Navigation.findNavController(root)
            navController.navigate(R.id.action_homeFeedFragment_to_profilePageFragment)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentPost = getItem(position)
        if (currentPost != null) {
            holder.connect(currentPost, itemClk.clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            PostArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}