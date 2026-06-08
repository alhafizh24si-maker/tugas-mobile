package com.example.fzhmobile.Home.pertemuan_13

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.FragmentTabScanBinding

class TabScanFragment : Fragment() {
    private var _binding: FragmentTabScanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTabScanBinding.inflate(inflater, container, false)
        return binding.root
    }
}