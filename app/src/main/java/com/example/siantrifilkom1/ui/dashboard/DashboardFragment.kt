package com.example.siantrifilkom1.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.siantrifilkom1.MainActivity
import com.example.siantrifilkom1.R
import com.example.siantrifilkom1.databinding.FragmentDashboardBinding
import com.example.siantrifilkom1.util.SharedPref


class DashboardFragment : Fragment() {
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val myPreference = SharedPref(requireContext())
        //mengecek data login apakah sudah sesuai dg di sharedpref
        if (myPreference.getData().NIM == "" && myPreference.getData().PASSWORD == "") {
            val navOption = NavOptions.Builder().setPopUpTo(R.id.loginFragment,true).build()
            findNavController().navigate(R.id.action_dashboardFragment_to_welcomePageFragment,null,navOption)
        }
    }

}