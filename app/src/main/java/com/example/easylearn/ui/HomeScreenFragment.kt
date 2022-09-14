package com.example.easylearn.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.easylearn.R
import com.example.easylearn.databinding.FragmentHomeScreenBinding
import com.google.android.material.navigation.NavigationBarView

class HomeScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeScreenBinding.bind(view)
        val bottomNavigation: NavigationBarView = binding.bottomNavigation


        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_screen_home) as NavHostFragment
        val navController = navHost.findNavController()
        bottomNavigation.setupWithNavController(navController)

    }
}