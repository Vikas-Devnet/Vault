package com.devnet.vault.ui.home

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devnet.vault.R
import com.devnet.vault.databinding.FragmentHomeBinding
import com.devnet.vault.model.home.PasswordItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var passwordAdapter: PasswordAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.passwordContainer
        recyclerView.layoutManager = LinearLayoutManager(context)

        val passwordItems = listOf(
            PasswordItem("https://example.com", "user1", "password1", "https://example.com/image1.jpg"),
            PasswordItem("https://example2.com", "user2", "password2", "https://example.com/image2.jpg"),
            // Add more items here
        )
        passwordAdapter = PasswordAdapter(passwordItems) { item ->
            openPasswordDetailScreen(item)
        }
        recyclerView.adapter = passwordAdapter
        return root
    }

    private fun openPasswordDetailScreen(item: PasswordItem) {
        Toast.makeText(requireContext(), item.username, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}