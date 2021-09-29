package com.akshatsahijpal.crud.ui.fragments.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostCreationFragmentBinding
import com.akshatsahijpal.crud.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PostCreationFragment : Fragment() {
    private lateinit var _binding: PostCreationFragmentBinding
    private lateinit var navController: NavController
    private val model by viewModels<PostCreationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = PostCreationFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var account = GoogleSignIn.getLastSignedInAccount(requireContext())
        navController = Navigation.findNavController(view)
        _binding.apply {
            userNameOn.text = account.displayName
            imageForPostFromGallery.setOnClickListener { fetchImageFromGallery() }
            imageForPostFromCamera.setOnClickListener { startCameraForImage() }
            Picasso.get().load(account.photoUrl).into(profilePictureOfUser)
            if(account.photoUrl==null){
                Picasso.get().load(Constants.DefaultProfilePhoto).into(profilePictureOfUser)
            }
            closeWindowButton.setOnClickListener { closeWindow() }
            postButton.setOnClickListener {
                var postText = mainPara.text.toString()
                when (postText) {
                null -> postButton.isEnabled = false
                else -> postButton.isEnabled = true
            }
                postButtonImpl(postText, Calendar.getInstance().time, account)
            }
        }
    }

    private fun startCameraForImage() {
        TODO("Not yet implemented")
    }

    private fun fetchImageFromGallery() {
        TODO("Not yet implemented")
    }

    private fun postButtonImpl(postText: String, time: Date, account: GoogleSignInAccount) {
        // Upload this data on net
        var uplData = PostFeedData(account.displayName,
            account.givenName,
            time.toString(),
            if (account.photoUrl != null) account.photoUrl.toString() else Constants.DefaultProfilePhoto,
            postText,
            null,
            23,
            323,
            23)
        model.uploadData(uplData)
        model.liveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "vak => $it", Toast.LENGTH_LONG).show()
            if(it != null){
                closeWindow()
            }else {
                Toast.makeText(requireContext(), "Something went horribly wrong ", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun closeWindow() {
        navController.popBackStack()
    }
}