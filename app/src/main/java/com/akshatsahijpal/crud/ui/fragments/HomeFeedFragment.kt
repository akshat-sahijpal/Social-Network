package com.akshatsahijpal.crud.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.adapter.HomeFeedRecyclerViewAdapter
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment() {
    private lateinit var _binding: FragmentHomeFeedBinding
    private var x = listOf(
        PostFeedData("akshat2001",
            "Akshat", "2h", null,
        "This is my first post", null, 10, 12, 30),
        PostFeedData("aks2001",
            "Akat", "26h", null,
            "This is my Second post", null, 10, 12, 30),
        PostFeedData("at2001",
            "hat", "2h", null,
            "This is my third post", null, 10, 12, 30),
        PostFeedData("akshat2001",
            "Akshat", "2h", null,
            "This is my first post", null, 10, 12, 30),
        PostFeedData("aks2001",
            "Akat", "26h", null,
            "This is my Second post", null, 10, 12, 30),
        PostFeedData("at2001",
            "hat", "2h", null,
            "This is my third post", null, 10, 12, 30),

        )
    private var adapter = HomeFeedRecyclerViewAdapter(x)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeFeedBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*_binding.apply {
            mainFeedRecycler.adapter = adapter
            mainFeedRecycler.layoutManager = LinearLayoutManager(requireContext())
        }*/
    }
}