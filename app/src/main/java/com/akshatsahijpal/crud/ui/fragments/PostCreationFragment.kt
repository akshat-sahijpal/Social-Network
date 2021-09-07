package com.akshatsahijpal.crud.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.crud.databinding.PostCreationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostCreationFragment : Fragment() {
    private lateinit var _binding: PostCreationFragmentBinding
    private lateinit var navController: NavController
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
            closeWindowButton.setOnClickListener { closeWindow() }
            postButton.setOnClickListener { postButtonImpl() }
        }
    }
    private fun postButtonImpl() {
        TODO("Not yet implemented")
    }

    private fun closeWindow() {
        navController.popBackStack()
    }
}