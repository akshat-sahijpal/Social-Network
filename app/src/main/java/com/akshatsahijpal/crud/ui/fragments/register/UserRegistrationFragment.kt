package com.akshatsahijpal.crud.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akshatsahijpal.crud.data.UserProfileData
import com.akshatsahijpal.crud.databinding.FragmentUserRegistrationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationFragment : Fragment() {
    private lateinit var _binding: FragmentUserRegistrationBinding

    private val model by viewModels<UserRegistrationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.apply {
            submitData.setOnClickListener {
                var fullName = fullNameField.editText?.text ?: "-1"
                var location = locationField.editText?.text ?: "-1"
                var description = descriptionAboutField.editText?.text ?: "-1"
                if (fullName == "-1" || location == "-1" || description == "-1") {
                    Toast.makeText(requireContext(), "Empty fields Not allowed", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                var acc = GoogleSignIn.getLastSignedInAccount(requireContext())?.account
                model.uploadData(UserProfileData(fullName = fullName.toString(),
                    location = location.toString(),
                    description.toString(),
                    "acc"))
            }
        }
    }
}