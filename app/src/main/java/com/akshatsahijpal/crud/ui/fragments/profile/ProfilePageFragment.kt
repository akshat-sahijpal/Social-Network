package com.akshatsahijpal.crud.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.akshatsahijpal.crud.databinding.FragmentProfilePageBinding
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.TabAdapter
import com.akshatsahijpal.crud.util.Constants.DefaultProfilePhoto
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilePageFragment : Fragment() {
    private lateinit var _binding: FragmentProfilePageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var pager = _binding.viewPagerForProfile
        var account = GoogleSignIn.getLastSignedInAccount(requireContext())
        _binding.apply {
            Picasso.get().load(account.photoUrl).into(profilePicture)
            if(account.photoUrl==null){
                Picasso.get().load(DefaultProfilePhoto).into(profilePicture)
            }
            profileNameProfileScreen.text = account.displayName
            profileUserNameProfileScreen.text = "@${account.familyName}"
            locationOnProfile.text = account.email
            profileUserDescriptionProfileScreen.text =
                "Hey folks, Im ${account.displayName} my friends call me ${account.familyName}. You can contact me via mail on ${account.email}"
        }
        pager.adapter = TabAdapter(parentFragmentManager, lifecycle)
        TabLayoutMediator(_binding.tabLayoutForProfile, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Answers"
                    tab.id = 0
                }
                1 -> {
                    tab.text = "Media"
                    tab.id = 1
                }
                2 -> {
                    tab.text = "Chats"
                    tab.id = 2
                }
            }
        }.attach()
        pager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        if (_binding.tabLayoutForProfile.id == 0) {
                            _binding.tabLayoutForProfile[0].alpha = 1.0f
                        }
                    }
                    1 -> {
                        if (_binding.tabLayoutForProfile.id == 1) {
                            _binding.tabLayoutForProfile[1].alpha = 1.0f
                        }
                    }
                    2 -> {
                        if (_binding.tabLayoutForProfile.id == 2) {
                            _binding.tabLayoutForProfile[2].alpha = 1.0f
                        }
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }
}