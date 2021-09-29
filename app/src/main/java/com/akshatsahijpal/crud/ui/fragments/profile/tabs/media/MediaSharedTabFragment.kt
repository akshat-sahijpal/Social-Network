package com.akshatsahijpal.crud.ui.fragments.profile.tabs.media

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akshatsahijpal.crud.R

class MediaSharedTabFragment : Fragment() {

    companion object {
        fun newInstance() = MediaSharedTabFragment()
    }

    private lateinit var viewModel: MediaSharedTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.media_shared_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MediaSharedTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}