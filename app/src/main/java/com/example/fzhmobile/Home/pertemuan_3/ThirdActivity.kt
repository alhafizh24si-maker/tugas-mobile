package com.example.fzhmobile.Home.pertemuan_3

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.R
import com.example.fzhmobile.R.id.btnKirim
import com.example.fzhmobile.databinding.ActivityThirdBinding
import com.example.fzhmobile.utils.NotificationHelper
import com.example.fzhmobile.utils.PermissionHelper

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(this, permission)) {
                PermissionHelper.requestPermission(
                    notificationPermissionLauncher,
                    permission
                )
            }
        }

        binding.btnKirim.setOnClickListener {
            val nomor = binding.inputNoTujuan.text
            Toast.makeText(this, "Pesan Berhasil dikirim ke $nomor", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ThirdResultActivity::class.java)
//            startActivity(intent)
            NotificationHelper.showNotification(
                this, //Jika panggil di fragment maka requireContext()
                "Pesanan Anda",
                "Halo $nomor, Pesanan Anda Sedang Diproses",
                intent
            )
        }
    }
}