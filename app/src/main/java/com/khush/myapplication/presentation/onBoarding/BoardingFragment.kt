package com.khush.myapplication.presentation.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.khush.myapplication.R
import com.khush.myapplication.databinding.FragmentBoardingBinding

class BoardingFragment : Fragment() {

    private lateinit var binding: FragmentBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_boarding, container, false)
        binding = FragmentBoardingBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_boardingFragment_to_loginFragment)
        }

        return view
    }

}