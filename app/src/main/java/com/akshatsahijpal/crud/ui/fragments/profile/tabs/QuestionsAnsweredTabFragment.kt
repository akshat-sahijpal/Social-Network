package com.akshatsahijpal.crud.ui.fragments.profile.tabs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akshatsahijpal.crud.R

class QuestionsAnsweredTabFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionsAnsweredTabFragment()
    }

    private lateinit var viewModel: QuestionsAnsweredTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.questions_answered_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionsAnsweredTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}