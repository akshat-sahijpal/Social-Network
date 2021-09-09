package com.akshatsahijpal.crud.ui.fragments.suggest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akshatsahijpal.crud.databinding.PersonalFeedFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalFeedFragment : Fragment() {
    private lateinit var _binding: PersonalFeedFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = PersonalFeedFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
}