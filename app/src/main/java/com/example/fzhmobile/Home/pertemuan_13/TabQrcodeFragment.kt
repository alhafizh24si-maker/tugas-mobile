package com.example.fzhmobile.Home.pertemuan_13

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fzhmobile.databinding.FragmentTabQrcodeBinding

// IMPORT ZXING YANG BENAR (Sesuai panduan gambar)
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class TabQrcodeFragment : Fragment() {
    private var _binding: FragmentTabQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTabQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            val text = binding.edtQrInput.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(context, "Input tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Generate QR dan set hasilnya ke ImageView
                val qrBitmap = createQR(text)
                binding.ivQrCode.setImageBitmap(qrBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal generate QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createQR(text: String): Bitmap {
        val writer = QRCodeWriter()
        // Mengonversi teks menjadi matrix bit QR Code menggunakan ZXing core
        val matrix = writer.encode(
            text,
            BarcodeFormat.QR_CODE,
            500,
            500,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        )

        // Membuat object Bitmap kosong berukuran 500x500 pixel
        return Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565).apply {
            // Looping koordinat matrix untuk mewarnai pixel Bitmap (Hitam/Putih)
            for (x in 0 until 500) {
                for (y in 0 until 500) {
                    setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Mencegah memory leak saat fragmen dihancurkan
    }
}