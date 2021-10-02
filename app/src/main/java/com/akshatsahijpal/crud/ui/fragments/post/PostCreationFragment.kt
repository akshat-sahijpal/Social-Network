package com.akshatsahijpal.crud.ui.fragments.post

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.databinding.PostCreationFragmentBinding
import com.akshatsahijpal.crud.util.Constants
import com.akshatsahijpal.crud.util.Constants.Permission_broker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PostCreationFragment : Fragment() {
    private lateinit var _binding: PostCreationFragmentBinding
    private lateinit var navController: NavController
    private var imageURI: Uri? = null
    private var photoPostURL: String? = null
    private var storageRef: StorageReference =
        FirebaseStorage.getInstance().getReference("PostImages")
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
            if (account.photoUrl == null) {
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

    private fun postButtonImpl(postText: String, time: Date, account: GoogleSignInAccount) {
        // Upload this data on net
        var uplData = PostFeedData(account.displayName,
            account.givenName,
            time.toString(),
            if (account.photoUrl != null) account.photoUrl.toString() else Constants.DefaultProfilePhoto,
            postText,
            photoPostURL,
            23,
            323,
            23)
        model.uploadData(uplData)
        model.liveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "vak => $it", Toast.LENGTH_LONG).show()
            if (it != null) {
                closeWindow()
            } else {
                Toast.makeText(requireContext(),
                    "Something went horribly wrong ",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCameraForImage() {
        // First ask for camera permissions
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
              //done
            generateImageFromCamera()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), Permission_broker)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Permission_broker
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
           // done
            generateImageFromCamera()
        } else if (requestCode == Permission_broker
            && grantResults[0] == PackageManager.PERMISSION_DENIED
        ) {

        }
    }

    private val PICK = 203
    private fun fetchImageFromGallery() {
        var intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK && resultCode == RESULT_OK) {
            _binding.postImgePreview.isVisible = true
            imageURI = data?.data
            _binding.postImgePreview.setImageURI(data?.data)
            if (data?.data != null) {
                uploadImage()
            }
        }
    }
    private fun uploadImage() {
        val fileReference = storageRef.child("${UUID.randomUUID()}.${getFileExtension()}")
        imageURI?.let {
            fileReference.putFile(it)
                .addOnSuccessListener { result ->
                    val res = result.storage.downloadUrl
                    res.addOnSuccessListener {
                        _binding.progressBarForPhoto.isVisible = false
                        photoPostURL = res.result.toString()
                        _binding.postButton.isEnabled = true
                    }
                }.addOnFailureListener { exec ->
                    Toast.makeText(requireContext(), "Failed To Upload $exec", Toast.LENGTH_SHORT)
                        .show()
                }.addOnProgressListener { snap ->
                    val progress = (100 * snap.bytesTransferred / snap.totalByteCount)
                    _binding.progressBarForPhoto.isVisible = true
                    _binding.progressBarForPhoto.progress = progress.toInt()
                    _binding.postButton.isEnabled = false
            }
        }
    }
    private fun getFileExtension(): String {
        var activity = requireActivity()
        var resolver = activity.contentResolver
        var mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(this.imageURI?.let { resolver.getType(it) }).toString()
    }
    private fun closeWindow() {
        navController.popBackStack()
    }
    private fun generateImageFromCamera() {
        Toast.makeText(requireContext(), "Click Click", Toast.LENGTH_SHORT).show()
    }
}