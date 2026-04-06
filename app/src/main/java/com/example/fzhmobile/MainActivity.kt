package com.example.fzhmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fzhmobile.databinding.ActivityMainBinding
import com.example.fzhmobile.pertemuan_4.FourthActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonSend.setOnClickListener {
            val btnButton = binding.buttonSend.text
            Toast.makeText(this, "Pesan Berhasil Dikirim ke $btnButton", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FourthActivity::class.java)
        }

        val intent = Intent(this, FourthActivity::class.java)

        /*tambahkan bagian berikut*/
        intent.putExtra("nama", "Politeknik Caltex Riau")
        intent.putExtra("asal", "Rumbai")
        intent.putExtra("umur", 25)

        startActivity(intent)
        finish()
        }
}