package com.example.viewandlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewandlayout.databinding.FragmentMainBinding

class MainFragment() : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val currentFragment = parentFragmentManager.findFragmentById(R.id.listFragmentContainer)

        if (currentFragment == null) {
            val isTablet = context?.resources?.getBoolean(R.bool.isTablet)
            val fragment = ListFragment()
            val detailFragment = DetailFragmentFirstApp()
            when (isTablet) {
                true -> {
                    parentFragmentManager.beginTransaction()
                        .add(R.id.listFragmentContainer, fragment)
                        .commit()
                    parentFragmentManager.beginTransaction()
                        .add(R.id.detailFragmentContainer, detailFragment).commit()

                }
                else -> {
                    parentFragmentManager.beginTransaction()
                        .add(R.id.listFragmentContainer, fragment)
                        .commit()
                }
            }


        }



        return view
    }
}