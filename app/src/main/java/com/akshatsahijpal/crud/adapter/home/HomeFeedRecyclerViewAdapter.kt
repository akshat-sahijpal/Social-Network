package com.akshatsahijpal.crud.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostArchitectureBinding
import com.squareup.picasso.Picasso

class HomeFeedRecyclerViewAdapter constructor(
    private val demo: List<PostFeedData>,
    itemClk: ItemClickListener,
) :  RecyclerView.Adapter<HomeFeedRecyclerViewAdapter.Holder>() {
    private var itemClick: ItemClickListener = itemClk
    private lateinit var navController: NavController
    inner class Holder(private var _bind: PostArchitectureBinding,
    var itemClick: ItemClickListener
    ) :
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
                Picasso.get().load(post.postProfilePicture).into(it.profilePicture)
                it.profilePicture.setOnClickListener {
                    toProfile(post, _bind.root)
                }
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
    private fun toProfile(post: PostFeedData, root: CardView) {
        navController = Navigation.findNavController(root)
        navController.navigate(R.id.action_homeFeedFragment_to_profilePageFragment)
    }
    override fun getItemCount(): Int {
        return demo.size
    }
}