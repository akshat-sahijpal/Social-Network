package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akshatsahijpal.crud.databinding.QuestionsAnsweredTabFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsAnsweredTabFragment : Fragment() {
    private lateinit var _binding: QuestionsAnsweredTabFragmentBinding
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
    }
}