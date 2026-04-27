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
import com.example.fzhmobile.AuthActivity
import com.example.fzhmobile.Home.pertemuan_2.SecondActivity
import com.example.fzhmobile.Home.pertemuan_3.ThirdActivity
import com.example.fzhmobile.Home.pertemuan_4.FourthActivity
import com.example.fzhmobile.Home.pertemuan_5.FifthActivity
import com.example.fzhmobile.Home.pertemuan_7.SevenActivity
import com.example.fzhmobile.databinding.FragmentHomeBinding

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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
        }
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        // Menggunakan binding untuk menggantikan findViewById
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

        // Logika Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    // Fungsi pembantu untuk pindah Activity agar kode tidak berulang
    private fun moveActivity(cls: Class<*>) {
        val intent = Intent(requireContext(), cls)
        startActivity(intent)
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