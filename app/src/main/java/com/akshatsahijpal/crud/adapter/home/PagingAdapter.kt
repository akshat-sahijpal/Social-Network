package com.akshatsahijpal.crud.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostArchitectureBinding
import com.akshatsahijpal.crud.util.Constants
import com.squareup.picasso.Picasso

class PagingAdapter constructor(itemClk: ItemClickListener) :
    PagingDataAdapter<PostFeedData, PagingAdapter.Holder>(compareList) {
    private lateinit var navController: NavController
    private var itemClick: ItemClickListener = itemClk

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
        private var _bind: PostArchitectureBinding,
        var itemClick: ItemClickListener,
    ) :
        RecyclerView.ViewHolder(_bind.root), View.OnClickListener {
        init {
            _bind.root.setOnClickListener(this)
        }

        fun connect(post: PostFeedData) {
            _bind.let {
                it.profileUserName.text = post.postUserName
                it.profileName.text = "@${post.postProfileName}"
                it.uploadTime.text = post.postUploadTime
                it.mainPostParagraph.text = post.postMainParagraph
                Picasso.get().load(post.postProfilePicture).into(it.profilePicture)
                if(post.postAddPhoto!=null){
                    Picasso.get().load(post.postAddPhoto).into(it.PostImage)
                }
                if(post.postProfilePicture==null){
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(it.profilePicture)
                }
                it.profilePicture.setOnClickListener {
                    toProfile(post, _bind.root)
                }
            }
        }

        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            itemClick.onItemClicked(position)
        }

        private fun toProfile(post: PostFeedData, root: CardView) {
            navController = Navigation.findNavController(root)
            navController.navigate(R.id.action_homeFeedFragment_to_profilePageFragment)
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
            PostArchitectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view, itemClick)
    }
    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}