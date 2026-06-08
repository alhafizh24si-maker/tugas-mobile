package com.example.fzhmobile.Home.pertemuan_13

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fzhmobile.databinding.FragmentTabCaptureBinding

class TabCaptureFragment : Fragment() {

    private var _binding: FragmentTabCaptureBinding? = null
    private val binding get() = _binding!!

    private var currentPhotoUri: Uri? = null

    // Menangani hasil pemanggilan kamera intent
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentPhotoUri?.let { uri ->
                binding.ivCapturedImage.setImageURI(uri)
                // Refresh galeri agar media baru langsung terindeks oleh sistem
                context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            }
        } else {
            Toast.makeText(context, "Pengambilan gambar dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    // Menangani dialog konfirmasi izin (permission runtime) kamera
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(context, "Izin kamera diperlukan untuk mengambil foto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabCaptureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCapture.setOnClickListener {
            if (hasCameraPermission()) {
                openCamera()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            // Generate alamat tempat penyimpanan dan nama foto secara dinamis di dalam MediaStore
            currentPhotoUri = createGalleryPhotoUri()

            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
            cameraLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal membuka kamera atau membuat file: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun createGalleryPhotoUri(): Uri {
        val folderName = "TestCaptures"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$folderName")
        }
        return requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw RuntimeException("Gagal membuat URI MediaStore")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Mencegah kebocoran memori (memory leak)
    }
}