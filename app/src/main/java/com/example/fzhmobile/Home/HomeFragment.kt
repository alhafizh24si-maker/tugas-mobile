package com.example.fzhmobile.Home

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fzhmobile.AuthActivity
import com.example.fzhmobile.Home.pertemuan_10.TenActivity
import com.example.fzhmobile.Home.pertemuan_2.SecondActivity
import com.example.fzhmobile.Home.pertemuan_3.ThirdActivity
import com.example.fzhmobile.Home.pertemuan_4.FourthActivity
import com.example.fzhmobile.Home.pertemuan_5.FifthActivity
import com.example.fzhmobile.Home.pertemuan_7.SevenActivity
import com.example.fzhmobile.Home.pertemuan_9.NinthActivity
import com.example.fzhmobile.Home.photo.PhotoAdapter
import com.example.fzhmobile.data.api.CatFactApiClient
import com.example.fzhmobile.data.api.PhotoApiClient
import com.example.fzhmobile.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    // Inisialisasi View Binding untuk Fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
        }
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        // ==========================================
        // Logika Klik Tombol Pertemuan (Bawaan)
        // ==========================================
        binding.btnSecond.setOnClickListener {
            moveActivity(SecondActivity::class.java)
        }

        binding.btnThird.setOnClickListener {
            moveActivity(ThirdActivity::class.java)
        }

        binding.btnFourth.setOnClickListener {
            moveActivity(FourthActivity::class.java)
        }

        binding.btnLima.setOnClickListener {
            moveActivity(FifthActivity::class.java)
        }

        binding.btnSeven.setOnClickListener {
            moveActivity(SevenActivity::class.java)
        }

        binding.btnPertemuan9.setOnClickListener {
            val intent = Intent(requireContext(), NinthActivity::class.java)
            startActivity(intent)
        }

        binding.btnTen.setOnClickListener {
            val intent = Intent(requireContext(), TenActivity::class.java)
            startActivity(intent)
        }

        // Logika Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        // ==========================================
        // Aksi REST API & Refresh Button
        // ==========================================
        binding.btnRefresh.setOnClickListener {
            loadCatFact()
        }

        // Panggil otomatis saat fragment aktif di layar
        loadCatFact()
        loadPhoto()
    }

    // Fungsi pembantu untuk pindah Activity agar kode tidak berulang
    private fun moveActivity(cls: Class<*>) {
        val intent = Intent(requireContext(), cls)
        startActivity(intent)
    }

    // Mengambil Fakta Kucing via Retrofit
    private fun loadCatFact() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = CatFactApiClient.apiService.getCatFact()
                binding.tvCatFact.text = "\"${response.fact}\""
            } catch (e: Exception) {
                binding.tvCatFact.text = "Gagal mengambil fakta kucing."
            }
        }
    }

    // Mengambil Galeri Foto via Retrofit (Tambahan dari panduan Picsum)
    private fun loadPhoto() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val photos = PhotoApiClient.apiService.getPhotos()
                val adapter = PhotoAdapter(photos)
                binding.rvGallery.adapter = adapter

                // Set layout manager agar tampil secara vertikal standar
                binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                // Clear Session (SharedPreferences) jika ada
                val sharedPref = requireActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()

                // Pindah ke AuthActivity (Login)
                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Penting untuk mencegah memory leak
    }
}