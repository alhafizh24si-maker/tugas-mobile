package com.example.fzhmobile.Home.pertemuan_13

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fzhmobile.databinding.FragmentTabScanBinding
// Import PermissionHelper Anda (Sesuaikan package-nya jika berbeda)
import com.example.fzhmobile.utils.PermissionHelper
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TabScanFragment : Fragment() {

    private var _binding: FragmentTabScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService

    // Inisialisasi scanner khusus format QR Code
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    // Launcher izin kamera secara modern
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(context, "Izin kamera diperlukan untuk fitur scan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Membuat single thread executor untuk penanganan kalkulasi di background
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Logika klik tombol Capture sesuai permintaan Anda
        binding.btnCapture.setOnClickListener {
            if (!PermissionHelper.hasPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {
                PermissionHelper.requestPermission(
                    permissionLauncher,
                    Manifest.permission.CAMERA
                )
            } else {
                openCamera()
            }
        }
    }

    /**
     * Membuka dan menginisialisasi kamera (Sebelumnya bernama startCamera)
     */
    private fun openCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Mengonfigurasi Preview Jendela Bidik Kamera (ViewFinder)
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            // Mengonfigurasi Analisis Frame Gambar untuk ML Kit Scanner
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(cameraExecutor) { imageProxy ->
                        processImageProxy(imageProxy)
                    }
                }

            try {
                cameraProvider.unbindAll()

                // Ikat komponen kamera ke siklus hidup Fragment ini
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e("TabScanFragment", "Gagal memulai kamera internal", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    val rawValue = barcodes[0].rawValue
                    activity?.runOnUiThread {
                        binding.tvScanResult.text = "Hasil: $rawValue"
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("TabScanFragment", "Proses scan gagal", e)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scanner.close()
        cameraExecutor.shutdown()
    }
}