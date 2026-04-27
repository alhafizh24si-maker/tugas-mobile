package com.example.fzhmobile.Home.pertemuan_5

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    // Gunakan private lateinit agar binding bisa diakses di semua fungsi (termasuk onBackPressed)
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- IMPROVISASI 1: Konfigurasi Toolbar ---
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Web Portal"
            setDisplayHomeAsUpEnabled(true)
        }

        // --- IMPROVISASI 2: Progress Bar Real-time ---
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.progress = newProgress
                if (newProgress == 100) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        // Konfigurasi WebView
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://portal-guest.alwaysdata.net/login")
    }

    // --- IMPROVISASI 3: Penanganan Tombol Back yang Benar ---
    override fun onBackPressed() {
        // JANGAN inflate lagi di sini. Gunakan binding yang sudah ada.
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // Menangani tombol back di Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}