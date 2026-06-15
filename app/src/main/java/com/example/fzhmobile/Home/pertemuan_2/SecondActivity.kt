package com.example.fzhmobile.Home.pertemuan_2

import android.Manifest
import android.content.Intent
import android.os.Build
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
import com.example.fzhmobile.utils.NotificationHelper

class SecondActivity : AppCompatActivity() {

    // 1. Tambahkan launcher izin notifikasi (Wajib untuk Android 13+)
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Cek izin notifikasi saat halaman dibuka
        checkNotificationPermission()

        // Inisialisasi komponen XML menggunakan findViewById
        val inputNama: EditText = findViewById(R.id.inputNama)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // 3. Inisialisasi tombol notifikasi baru
        val btnNotif: Button = findViewById(R.id.btnNotif)

        // Logika tombol Submit lama (menampilkan Toast)
        btnSubmit.setOnClickListener {
            val nama = inputNama.text.toString().trim() // Tambahkan .toString() agar tidak error
            Toast.makeText(this, "Halo $nama", Toast.LENGTH_SHORT).show()
        }

        // 4. Logika tombol Notifikasi baru
        btnNotif.setOnClickListener {
            val nama = inputNama.text.toString().trim()

            if (nama.isEmpty()) {
                inputNama.error = "Nama tidak boleh kosong!"
                return@setOnClickListener
            }

            // Siapkan intent untuk membuka kembali SecondActivity saat notifikasi diklik
            val intent = Intent(this, SecondActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            // Panggil NotificationHelper untuk memunculkan notifikasi
            NotificationHelper.showNotification(
                context = this,
                title = "Notifikasi Pertemuan 2",
                message = "Halo $nama, ini adalah notifikasi dari Pertemuan 2!",
                intent = intent
            )
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}