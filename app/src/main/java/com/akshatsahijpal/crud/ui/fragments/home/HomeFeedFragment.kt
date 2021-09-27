package com.akshatsahijpal.crud.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.adapter.home.PagingAdapter
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment(), PagingAdapter.ItemClickListener {
    private lateinit var _binding: FragmentHomeFeedBinding
    private lateinit var navController: NavController
    private val model by viewModels<HomeFeedViewModel>()
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
            model.grabData()
            var adapter = PagingAdapter(HomeFeedFragment())
            mainFeedRecycler.setHasFixedSize(true)
            mainFeedRecycler.adapter = adapter
            model.binder.observe(viewLifecycleOwner) {
                /*Toast.makeText(requireContext(), "Result -> $it", Toast.LENGTH_SHORT).show()
                if(it!=null) {
                    mainFeedRecycler.adapter = HomeFeedRecyclerViewAdapter(it, HomeFeedFragment())
                    Toast.makeText(requireContext(), "Result -> $it", Toast.LENGTH_SHORT).show()
                    mainFeedRecycler.layoutManager = LinearLayoutManager(requireContext())
                    postRedirButton.setOnClickListener {
                        navController.navigate(R.id.action_homeFeedFragment_to_postCreationFragment)
                    }
                }*/
                it.observe(viewLifecycleOwner) {   dt ->
                    adapter.submitData(viewLifecycleOwner.lifecycle, dt)
                }
            }
        }
    }

    override fun onItemClicked(position: Int) {
        Toast.makeText(requireContext(), "for $position", Toast.LENGTH_LONG).show();
        navController.navigate(R.id.action_homeFeedFragment_to_expandedPostFragment)
    }
}