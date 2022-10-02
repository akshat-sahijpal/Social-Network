package com.akshatsahijpal.crud.ui.fragments.profile.tabs.answer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akshatsahijpal.crud.R

class QuestionsAskedFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionsAskedFragment()
    }

    private lateinit var viewModel: QuestionsAskedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.questions_asked_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[QuestionsAskedViewModel::class.java]
        // TODO: Use the ViewModel
    }

}