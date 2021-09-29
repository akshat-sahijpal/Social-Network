package com.akshatsahijpal.crud.ui.fragments.profile.tabs.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.media.MediaSharedTabFragment
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.answer.QuestionsAskedFragment
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.ui.QuestionsAnsweredTabFragment

class TabAdapter(var manager:FragmentManager,
var lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> QuestionsAnsweredTabFragment()
            1 -> QuestionsAskedFragment()
            else -> MediaSharedTabFragment()
        }
    }
}