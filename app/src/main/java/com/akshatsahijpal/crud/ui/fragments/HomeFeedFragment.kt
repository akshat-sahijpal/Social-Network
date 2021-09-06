package com.akshatsahijpal.crud.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akshatsahijpal.crud.R
import com.akshatsahijpal.crud.databinding.FragmentHomeFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment() {
        private lateinit var _binding: FragmentHomeFeedBinding
        override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
            _binding = FragmentHomeFeedBinding.inflate(inflater, container, false)
            return _binding.root
        }
}