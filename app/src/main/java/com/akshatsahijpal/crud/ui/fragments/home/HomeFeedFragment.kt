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
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.adapter.home.PagingAdapter
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment(), PagingAdapter.ItemClickListener {
    private lateinit var _binding: FragmentHomeFeedBinding
    val db = Firebase.firestore
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
        var account = GoogleSignIn.getLastSignedInAccount(requireContext())
        navController = Navigation.findNavController(view)
        _binding.apply {
            Picasso.get().load(account.photoUrl).into(profilePicture)
            model.grabData()
            var adapter = PagingAdapter(HomeFeedFragment())
            mainFeedRecycler.setHasFixedSize(true)
            mainFeedRecycler.adapter = adapter
            mainFeedRecycler.layoutManager = LinearLayoutManager(requireContext())
            postRedirButton.setOnClickListener {
                navController.navigate(R.id.action_homeFeedFragment_to_postCreationFragment)
            }
            model.binder.observe(viewLifecycleOwner) {
                it.observe(viewLifecycleOwner) { dt ->
                    adapter.submitData(viewLifecycleOwner.lifecycle, dt)
                }
            }
        }
    }

    //suspend fun makeReq() = db.collection(Constants.POST).get().await().documents
    override fun onItemClicked(position: Int) {
        Toast.makeText(requireContext(), "for $position", Toast.LENGTH_LONG).show()
        navController.navigate(R.id.action_homeFeedFragment_to_expandedPostFragment)
    }
}