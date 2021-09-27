package com.akshatsahijpal.crud.ui.fragments.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.adapter.comment.CommentRecyclerAdapter
import com.akshatsahijpal.crud.data.CommentArchitectureData
import com.akshatsahijpal.crud.databinding.ExpandedPostFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpandedPostFragment : Fragment() {
    private lateinit var _binding: ExpandedPostFragmentBinding
    private var adapter = CommentRecyclerAdapter(listOf(
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
        CommentArchitectureData("akshat", "Akshat Sahijpal", "2h", "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It h"),
    ))
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
        _binding.apply {
            CommentRecyclerView.adapter = adapter
            CommentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            CommentRecyclerView.isNestedScrollingEnabled = false
        }
    }
}