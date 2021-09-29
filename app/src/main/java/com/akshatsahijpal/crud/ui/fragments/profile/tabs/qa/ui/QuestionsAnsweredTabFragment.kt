package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshatsahijpal.crud.databinding.QuestionsAnsweredTabFragmentBinding
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.adapter.QaAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsAnsweredTabFragment : Fragment() {
    private lateinit var _binding: QuestionsAnsweredTabFragmentBinding
    private val model by viewModels<QuestionsAnsweredTabViewModel>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = QuestionsAnsweredTabFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var account = GoogleSignIn.getLastSignedInAccount(requireContext())
        var query = account.displayName
        model.searchAllPostFor(query)
        var adapter = QaAdapter()
        _binding.apply {
            SubRecyclerMA.adapter = adapter
            SubRecyclerMA.layoutManager = LinearLayoutManager(requireContext())
        }
        model.data.observe(viewLifecycleOwner) {
            it.observe(viewLifecycleOwner) { dataSet ->
                adapter.submitData(viewLifecycleOwner.lifecycle, dataSet)
            }
        }
    }
}