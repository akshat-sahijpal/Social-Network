package com.akshatsahijpal.crud.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostCreationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PostCreationFragment : Fragment() {
    private lateinit var _binding: PostCreationFragmentBinding
    private lateinit var navController: NavController
    private var awaitedResult: String = "Awaited"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = PostCreationFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        _binding.apply {
            var postText = mainPara.text.toString()
            when (postText) {
                null -> postButton.isEnabled = false
                else -> postButton.isEnabled = true
            }
            closeWindowButton.setOnClickListener { closeWindow() }
            postButton.setOnClickListener {
                postButtonImpl(postText, Calendar.getInstance().time)
            }
        }
    }

    private fun postButtonImpl(postText: String, time: Date) {
        // Upload this data on net
        var uplData = PostFeedData(awaitedResult,
            awaitedResult,
            awaitedResult,
            time.toString(),
            postText,
            null,
            23,
            323,
            23)
        closeWindow()
    }


    private fun closeWindow() {
        navController.popBackStack()
    }
}