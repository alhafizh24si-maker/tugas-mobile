package com.example.fzhmobile.Home.pertemuan_13

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.ActivityThirteenthBinding
import com.google.android.material.tabs.TabLayoutMediator

class ThirteenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirteenthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThirteenthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar sebagai ActionBar agar konsisten dengan Pertemuan 10
        setSupportActionBar(binding.toolbarThirteenth)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Mengatur fungsi klik tombol back di toolbar
        binding.toolbarThirteenth.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 1. Inisialisasi Adapter
        val tabsAdapter = ThirteenthTabsAdapter(this)

        // 2. Set adapter pada ViewPager2
        binding.viewPagerThirteenth.adapter = tabsAdapter

        // 3. Sinkronisasi TabLayout dan ViewPager2 dengan Judul, Icon, dan Badge
        TabLayoutMediator(binding.tabLayoutThirteenth, binding.viewPagerThirteenth) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Capture"
                    // Tambah Icon (Menggunakan ic_home atau ganti dengan drawable lain jika ada)
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_capture)

                    // Tambah Badge Tanpa nomor (hanya titik indikator)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = "Scan"
                    // Tambah Icon
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_qr_scan)

                    // Tambah Badge dengan nomor notifikasi (misal: 5)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 5
                }
                2 -> {
                    tab.text = "QR Code"
                    // Tambah Icon jika diperlukan (opsional, di sini disamakan dengan Pertemuan 10 yang hanya teks/polos pada tab terakhir)
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_qr)
                }
            }
        }.attach()

        // Penerapan Window Insets untuk Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}