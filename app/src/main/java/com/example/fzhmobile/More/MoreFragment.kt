package com.example.fzhmobile.More

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fzhmobile.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    // Data sederhana untuk simple_list_item_1
    private val dataList = listOf(
        "Kotlin",
        "Java",
        "Python",
        "C++",
        "JavaScript",
        "Dart",
        "Swift",
        "Go",
        "Ruby",
        "R",
        "PHP",
        "C#",
        "TypeScript",
        "Shell",
        "SQL",
        "Perl",
        "Rust",
        "Scala",
        "Haskell",
        "Lua",
        "Erlang",
        "Prolog",
        "Assembly",
        "Objective-C",
        "VBA"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Konfigurasi Toolbar agar muncul judulnya
        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.toolbarMore)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "More Options"
        }

        // 1. Definisikan ArrayAdapter menggunakan layout bawaan simple_list_item_1
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataList
        )

        // 2. Hubungkan ListView dengan adapter
        binding.listViewItems.adapter = adapter

        // 3. Tambahkan Event Klik pada Item List
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataList[position]
            Toast.makeText(requireContext(), "Kamu memilih: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}