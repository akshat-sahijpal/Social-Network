package com.akshatsahijpal.crud.ui.fragments.post

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.adapter.comment.CommentListAdapter
import com.akshatsahijpal.crud.adapter.comment.CommentRecyclerAdapter
import com.akshatsahijpal.crud.data.CommentArchitectureData
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.ExpandedPostFragmentBinding
import com.akshatsahijpal.crud.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ExpandedPostFragment : Fragment() {
    private val args: ExpandedPostFragmentArgs by navArgs()

    private val model: ExpandedPostViewModel by viewModels()
    private lateinit var _binding: ExpandedPostFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ExpandedPostFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.checkForDataWithUid(args.uid)
        model.listenData().observe(viewLifecycleOwner) { data ->
            _binding.apply {
                mainScope.profileUserName.text = data!!.postUserName
                mainScope.profileName.text = "@${data.postProfileName}"
                mainScope.uploadTime.text = data.postUploadTime
                mainScope.mainPostParagraph.text = data.postMainParagraph
                Picasso.get().load(data.postProfilePicture).into(mainScope.profilePicture)
                val phot = data.postAddPhoto
                Log.d("Displaying this image->", "${phot}")
                if (phot != null) {
                    mainScope.PostImage.isVisible = true
                    Picasso.get().load(phot.trim()).into(mainScope.PostImage)
                    Log.d("Displaying this image->", "${data.postAddPhoto}")
                }
                if (data.postProfilePicture == null) {
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(mainScope.profilePicture)
                }
                commentScope.CommentButtonForPost.setOnClickListener {
                    if (!commentScope.mainCommentContentForThePost.text.equals(" ")) {
                        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
                        val data = CommentData(
                            account.photoUrl.toString(),
                            Calendar.getInstance().time.toString(),
                            account.displayName,
                            commentScope.mainCommentContentForThePost.text.toString()
                        )
                        model.commentUploader(data)
                        model.listenCommentData().observe(viewLifecycleOwner) {
                            model.updatePostWithCommentRef(it, args.uid)
                        }
                        commentScope.mainCommentContentForThePost.setText("")
                    }
                }
                val adapter = CommentListAdapter()
                model.getCommentIDs(args.uid)
                model.watchCommentIDs().observe(viewLifecycleOwner) { listOfData ->
                    adapter.submitList(listOfData)
                }
                CommentRecyclerView.adapter = adapter
                CommentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                CommentRecyclerView.isNestedScrollingEnabled = false
            }
        }
    }
}