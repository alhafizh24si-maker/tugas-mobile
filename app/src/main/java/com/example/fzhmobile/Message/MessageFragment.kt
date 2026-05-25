package com.example.fzhmobile.Message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fzhmobile.Message.tutorial.TutorialMessageActivity
import com.example.fzhmobile.R
import com.example.fzhmobile.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    // Inisialisasi View Binding
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    // 1. Definisi list data message menggunakan MessageModel
    private val messageList = listOf(
        MessageModel("Alya", "Halo! Apa kabar?", "https://avatar.iran.liara.run/public/1"),
        MessageModel("Budi", "Sudah makan?", "https://avatar.iran.liara.run/public/2"),
        MessageModel(
            "Citra",
            "Jangan lupa tugasnya ya!",
            "https://avatar.iran.liara.run/public/3"
        ),
        MessageModel(
            "Dika",
            "Besok kita rapat jam 9",
            "https://avatar.iran.liara.run/public/4"
        ),
        MessageModel("Eka", "Nice job kemarin!", "https://avatar.iran.liara.run/public/5"),
        MessageModel("Fajar", "Lagi ngapain?", "https://avatar.iran.liara.run/public/6"),
        MessageModel("Gita", "Boleh minta tolong?", "https://avatar.iran.liara.run/public/7"),
        MessageModel("Hana", "Lihat email ya", "https://avatar.iran.liara.run/public/8"),
        MessageModel("Irfan", "Oke noted", "https://avatar.iran.liara.run/public/9"),
        MessageModel("Joko", "Sampai jumpa besok", "https://avatar.iran.liara.run/public/10")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout menggunakan binding
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengeset toolbar fragment sebagai ActionBar Activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarMessage)

        // Mengatur judul pada ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Message"
        }

        // Mengaktifkan Option Menu di Fragment
        setHasOptionsMenu(true)

        // 3. Inisialisasi MessageAdapter (Custom Adapter)
        val adapter = MessageAdapter(requireContext(), messageList)

        // 4. Hubungkan ListView dengan Adapter
        binding.listMessageItems.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.message_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_tutorial -> {
                val intent = Intent(requireContext(), TutorialMessageActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Bersihkan binding untuk menghindari memory leak
        _binding = null
    }
}
