package com.example.fzhmobile.Home.pertemuan_9

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.ActivityNinthBinding
import com.example.fzhmobile.utils.NotificationHelper

class NinthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNinthBinding

    // 1. Launcher izin notifikasi (Wajib untuk Android 13+)
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

        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Cek izin notifikasi saat halaman dibuka
        checkNotificationPermission()

        // 3. Logika tombol Login (Mengambil input dari Email & Phone)
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            if (email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Email dan Nomor Telepon wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Login berhasil untuk: $email", Toast.LENGTH_SHORT).show()
        }

        // 4. Logika Baru: Tombol Kirim Notifikasi (Sesuai dengan XML barumu)
        binding.btnNotif.setOnClickListener {
            triggerNotification()
        }
    }

    /**
     * Fungsi untuk memicu jalannya Notifikasi
     */
    private fun triggerNotification() {
        // Intent untuk membuka kembali NinthActivity saat notifikasi diklik
        val intent = Intent(this, NinthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Kirim notifikasi menggunakan helper
        NotificationHelper.showNotification(
            context = this,
            title = "Pertemuan 9",
            message = "Halo! Ini adalah notifikasi yang dipicu dari Halaman Login.",
            intent = intent
        )
    }

    /**
     * Fungsi helper mengecek SDK Android 13+ (Tiramisu)
     */
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}