package com.akshatsahijpal.crud.ui.fragments.home

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.adapter.home.CheckClickerForPost
import com.akshatsahijpal.crud.adapter.home.PagingAdapter
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import com.akshatsahijpal.crud.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.http.POST

@AndroidEntryPoint
class HomeFeedFragment : Fragment() {
    private lateinit var _binding: FragmentHomeFeedBinding
    private val db = Firebase.firestore
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
        GlobalScope.launch {
            Log.d("Result->", "Result: ${makeReq()}")
        }
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        navController = Navigation.findNavController(view)
        _binding.apply {
            if (account != null) {
                Picasso.get().load(account.photoUrl).into(profilePicture)
            }
            if (account != null) {
                if (account.photoUrl == null) {
                    Picasso.get().load(Constants.DefaultProfilePhoto).into(profilePicture)
                }
            }
            profilePicture.setOnClickListener {
                navController.navigate(R.id.action_homeFeedFragment_to_profilePageFragment)
            }
            model.grabData()
            val adapter = PagingAdapter(CheckClickerForPost {
                val clickedID: String? =  it.documentID
                if (clickedID != null) {
                   var navi = HomeFeedFragmentDirections.actionHomeFeedFragmentToExpandedPostFragment(clickedID)
                    findNavController().navigate(navi)
                }
                Toast.makeText(requireContext(), "Clicked:$clickedID", Toast.LENGTH_LONG).show()
            })
            mainFeedRecycler.setHasFixedSize(true)
            mainFeedRecycler.adapter = adapter





            _binding.shimmerFrameLayout.isVisible = false
            postRedirButton.setOnClickListener {
                navController.navigate(R.id.action_homeFeedFragment_to_postCreationFragment)
            }
            adapter.addLoadStateListener {
                if (it.source.refresh is LoadState.Loading) {
                    _binding.shimmerFrameLayout.isVisible = true
                    _binding.shimmerFrameLayout.startShimmer()
                } else {
                    _binding.shimmerFrameLayout.stopShimmer()
                    _binding.shimmerFrameLayout.isVisible = false
                }
                _binding.mainFeedRecycler.isVisible = it.source.refresh is LoadState.NotLoading

            }
            model.binder.observe(viewLifecycleOwner) {
                it.observe(viewLifecycleOwner) { dt ->
                    adapter.submitData(viewLifecycleOwner.lifecycle, dt)
                }
            }
        }
    }

    suspend fun makeReq() = db.collection(Constants.POST).get().await().documents
}