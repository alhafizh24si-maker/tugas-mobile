package com.example.fzhmobile.Home.pertemuan_10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.ActivityTenBinding
import com.google.android.material.tabs.TabLayoutMediator

class TenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar dan tombol Back
        setSupportActionBar(binding.toolbarTen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Logika ketika tombol back di toolbar diklik
        binding.toolbarTen.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Mengikuti standar callback back modern
        }

        // 1. Inisialisasi Adapter
        val tabsAdapter = TenthTabsAdapter(this)

        // 2. Set adapter ke ViewPager2
        binding.viewPager.adapter = tabsAdapter

        // 3. Hubungkan TabLayout & ViewPager2 menggunakan Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Atur judul untuk setiap tab
            when (position) {
                0 -> tab.text = "Tab A"
                1 -> tab.text = "Tab B"
            }
        }.attach()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}