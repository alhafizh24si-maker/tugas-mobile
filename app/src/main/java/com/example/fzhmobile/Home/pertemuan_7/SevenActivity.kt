package com.example.fzhmobile.Home.pertemuan_7

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.ActivitySevenBinding

class SevenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySevenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySevenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Activity Seven"
            subtitle = "Ini adalah subtitle"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // PERBAIKAN 1: Tampilkan fragment pertama TANPA addToBackStack
        // Gunakan check savedInstanceState agar tidak re-create fragment saat rotasi layar
        if (savedInstanceState == null) {
            replaceFragment(SatuFragment(), false)
        }

        // Setup event click dengan parameter true agar bisa di-back
        binding.btnFragment1.setOnClickListener {
            replaceFragment(SatuFragment(), true)
        }

        binding.btnFragment2.setOnClickListener {
            replaceFragment(DuaFragment(), true)
        }

        binding.btnFragment3.setOnClickListener {
            replaceFragment(TigaFragment(), true)
        }
    }

    // PERBAIKAN 2: Tambahkan parameter isBackStack
    private fun replaceFragment(fragment: Fragment, isBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // Opsional: Cek agar tidak replace ke fragment yang sama (mencegah tumpukan double)
        val currentFragment = fragmentManager.findFragmentById(binding.fragmentContainer.id)
        if (currentFragment?.javaClass == fragment.javaClass) return

        transaction.replace(binding.fragmentContainer.id, fragment)

        // Hanya masukkan ke stack jika diperintahkan (biasanya untuk klik tombol)
        if (isBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    // Overload function agar tidak error saat dipanggil di onCreate awal
    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(fragment, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        // PERBAIKAN 3: Cek jumlah stack sebelum back
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            // Jika stack kosong, berarti kita di fragment utama, maka tutup activity
            finish()
        }
        return true
    }
}