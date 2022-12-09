package com.akshatsahijpal.crud.ui.fragments.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.adapter.comment.CommentListAdapter
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.databinding.CommentLayoutBinding
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
    private lateinit var commentAdapter: CommentListAdapter

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
        setUpBaseUI()
    }

    private fun setUpBaseUI() {
        model.listenData().observe(viewLifecycleOwner) { data ->
            _binding.apply {
                mainScope.profileUserName.text = data!!.postUserName
                //mainScope.profileName.text = "@${data.postProfileName}"
                mainScope.uploadTime.text = data.postUploadTime
                mainScope.mainPostParagraph.text = data.postMainParagraph
                Picasso.get().load(data.postProfilePicture).into(mainScope.profilePicture)
                val phot = data.postAddPhoto
                if (phot != null) {
               //     mainScope.PostImage.isVisible = true
                 //   Picasso.get().load(phot.trim()).into(mainScope.PostImage)
                }
                if (data.postProfilePicture == null) {
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(mainScope.profilePicture)
                }


                commentAdapter = CommentListAdapter()
                CommentRecyclerView.adapter = commentAdapter
                CommentRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    isNestedScrollingEnabled = false
                }
                commentProcessing()
                Picasso.get().load(GoogleSignIn.getLastSignedInAccount(requireContext())?.photoUrl).into(commentScope.profilePictureForComment)
                commentThreadUploader(commentScope)
            }
        }
    }
    private fun commentThreadUploader (commentScope:CommentLayoutBinding) {
        commentScope.CommentButtonForPost.setOnClickListener {
            if (!commentScope.mainCommentContentForThePost.text.equals(" ")) {
                val account = GoogleSignIn.getLastSignedInAccount(requireContext())
                val data = CommentData(
                    account?.photoUrl.toString(),
                    Calendar.getInstance().time.toString(),
                    account?.displayName,
                    commentScope.mainCommentContentForThePost.text.toString()
                )
                model.commentUploader(data)
                model.listenCommentData().observe(viewLifecycleOwner) {
                    model.updatePostWithCommentRef(it, args.uid)
                    commentProcessing()
                }
                hideKeyboard()
                commentScope.mainCommentContentForThePost.setText("")
            }
        }
    }

    private fun commentProcessing () {
        model.getCommentIDs(args.uid)
        model.watchCommentIDs().observe(viewLifecycleOwner) { listOfData ->
            commentAdapter.submitList(listOfData)
        }
    }

    private fun hideKeyboard() {
        requireActivity().currentFocus?.let { view ->
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}