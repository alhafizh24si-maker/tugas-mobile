package com.example.fzhmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.fzhmobile.Home.pertemuan_2.SecondActivity
import com.example.fzhmobile.Home.pertemuan_3.ThirdActivity
import com.example.fzhmobile.Home.pertemuan_4.FourthActivity
import com.example.fzhmobile.Home.pertemuan_5.FifthActivity
import com.example.fzhmobile.Home.pertemuan_7.SevenActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pastikan layout yang dipanggil adalah activity_main yang baru saja diperbaiki
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)


        findViewById<Button>(R.id.btnSecond).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        findViewById<Button>(R.id.btnThird).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

        findViewById<Button>(R.id.btnFourth).setOnClickListener {
            startActivity(Intent(this, FourthActivity::class.java))
        }

        findViewById<Button>(R.id.btnLima).setOnClickListener {
            startActivity(Intent(this, FifthActivity::class.java))
        }

        // Pertemuan 7
        findViewById<Button>(R.id.btnSeven).setOnClickListener {
            startActivity(Intent(this, SevenActivity::class.java))
        }

        // --- Logika Logout ---
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    // Hapus Session
                    sharedPref.edit { clear() }

                    // Pindah ke AuthActivity (Login)
                    val intent = Intent(this, AuthActivity::class.java)
                    // Flag ini memastikan user tidak bisa tekan 'back' kembali ke MainActivity
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}