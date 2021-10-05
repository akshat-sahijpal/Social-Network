package com.akshatsahijpal.crud.ui.fragments.post

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import androidx.core.net.toUri

@AndroidEntryPoint
class PostCreationFragment : Fragment() {
    private lateinit var _binding: PostCreationFragmentBinding
    private lateinit var navController: NavController
    private var imageURI: Uri? = null
    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private var CapturedImageFromCameraURI: Uri? = null
    private var photoPostURL: String? = null
    private lateinit var CameraPreview: PreviewView
    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private var CameraLens: Int = CameraSelector.LENS_FACING_BACK
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
        _binding.CameraPreview2323.isVisible = true
        CameraPreview = _binding.CameraPreview2323
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
        grantResults: IntArray,
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
            Toast.makeText(requireContext(), "Camera Won't work if not permitted", Toast.LENGTH_SHORT).show()
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

    private fun uploadImageOnDB(capturedImageFromCameraURI: Uri) {
        val fileReference = storageRef.child("${UUID.randomUUID()}.${getImageCapturedExtension(capturedImageFromCameraURI)}")
        fileReference.putFile(capturedImageFromCameraURI)
                .addOnSuccessListener { result ->
                    val res = result.storage.downloadUrl
                    res.addOnSuccessListener {
                        _binding.progressBarForPhoto.isVisible = false
                        photoPostURL = res.result.toString()
                        _binding.postButton.isEnabled = true
                    }
                }.addOnFailureListener { exec ->
                    Toast.makeText(requireContext(), "Failed To Upload $exec", Toast.LENGTH_LONG)
                        .show()
                }.addOnProgressListener { snap ->
                    val progress = (100 * snap.bytesTransferred / snap.totalByteCount)
                    _binding.progressBarForPhoto.isVisible = true
                    _binding.progressBarForPhoto.progress = progress.toInt()
                    _binding.postButton.isEnabled = false
                }

    }

    private fun getImageCapturedExtension(uri: Uri): String {
        val activity = requireActivity()
        val resolver = activity.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(uri?.let { resolver.getType(it) }).toString()
    }

    private fun getFileExtension(): String {
        val activity = requireActivity()
        val resolver = activity.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(this.imageURI?.let { resolver.getType(it) }).toString()
    }

    private fun closeWindow() {
        navController.popBackStack()
    }

    private fun generateImageFromCamera() {
        bindCamera()
        _binding.apply {
            imageCaptureBTN.isVisible = true
            textCapture.isVisible = true
            imageCaptureBTN.setOnClickListener {
                captureImage()
            }
        }
    }

    private fun captureImage() {
        var photoFile =
            File(requireActivity().externalMediaDirs.firstOrNull(),
                "CapturePro-${System.currentTimeMillis()}.jpg")
        var imageOptions: ImageCapture.OutputFileOptions = ImageCapture.OutputFileOptions.Builder(
            photoFile
        ).build()
        imageCapture?.takePicture(
            imageOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val imagePath = File(requireActivity().externalCacheDir!!.absolutePath)
                    Toast.makeText(requireContext(), "Image Saved At: ${imagePath.toUri()}", Toast.LENGTH_LONG).show()
                    CapturedImageFromCameraURI = imagePath.toUri()
                    CapturedImageFromCameraURI?.let { uploadImageOnDB(it) }
                    Log.d("TAG", "onImageSaved:  ${requireActivity().externalCacheDir!!.absolutePath}")
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        "Failed: ${exception.message} and ${exception.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun bindCamera() {
        cameraProvider = ProcessCameraProvider.getInstance(requireContext())
        cameraProvider.addListener({
            val prov: ProcessCameraProvider = cameraProvider.get()
            bindPreview(prov)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(prov: ProcessCameraProvider) {
        prov.unbindAll()
        val preview = Preview.Builder()
            .build()
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraLens)
                .build()
        val surface: Preview.SurfaceProvider =
            CameraPreview.createSurfaceProvider(camera?.cameraInfo)
        preview.setSurfaceProvider(surface)
        imageCapture = view?.display?.let {
            ImageCapture.Builder()
                .setFlashMode(ImageCapture.FLASH_MODE_ON)
                .setTargetAspectRatio(AspectRatio.RATIO_16_9) // width:height
                .setTargetRotation(it.rotation)
                .build()
        }
        camera = prov.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageCapture)
    }
}