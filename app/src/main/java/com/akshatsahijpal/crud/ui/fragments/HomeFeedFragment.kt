package com.akshatsahijpal.crud.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.adapter.HomeFeedRecyclerViewAdapter
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment(), HomeFeedRecyclerViewAdapter.ItemClickListener {
    private lateinit var _binding: FragmentHomeFeedBinding
    private lateinit var navController: NavController
    private var x = listOf(
        PostFeedData("akshat2001",
            "Akshat Sahijpal", "2h", null,
        "This is my first post", null, 10, 12, 30),
        PostFeedData("@aks2001",
            "Rainbow MainCar ", "26h", null,
            "This is my Second post", null, 10, 12, 30),
        PostFeedData("@at2001",
            "Lorem Ipsumhas", "2h", null,
            "This is my third post", null, 10, 12, 30),
        PostFeedData("@akshat2001",
            "Akshat", "2h", null,
            "Lorem Ipsumhas been the ind since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries", null, 10, 12, 30),
        PostFeedData("@aks2001",
            "Lorem Ipsumhas", "26h", null,
            "This is my Second post", null, 10, 12, 30),
        PostFeedData("@at2001",
            "Lorem Ipsumhas", "2h", null,
            "This is my third post", null, 10, 12, 30),
        )
    private var adapter = HomeFeedRecyclerViewAdapter(x, this)
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
        navController = Navigation.findNavController(view)
        _binding.apply {
            mainFeedRecycler.adapter = adapter
            mainFeedRecycler.layoutManager = LinearLayoutManager(requireContext())
            postRedirButton.setOnClickListener {
               navController.navigate(R.id.action_homeFeedFragment_to_postCreationFragment)
            }
        }
    }

    override fun onItemClicked(position: Int) {
        Toast.makeText(requireContext(), "for $position", Toast.LENGTH_LONG).show();
        navController.navigate(R.id.action_homeFeedFragment_to_expandedPostFragment)
    }
}