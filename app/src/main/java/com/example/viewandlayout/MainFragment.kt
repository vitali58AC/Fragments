package com.example.viewandlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.viewandlayout.databinding.FragmentMainBinding

class MainFragment() : Fragment(), IOnDetailChangeListener  {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val currentFragment = childFragmentManager.findFragmentById(R.id.listFragmentContainer)

        if (currentFragment == null) {
            val isTablet = context?.resources?.getBoolean(R.bool.isTablet)
            val fragment = ListFragment()
            val detailFragment = DetailFragmentFirstApp()
            when (isTablet) {
                true -> {
                    childFragmentManager.beginTransaction()
                        .add(R.id.listFragmentContainer, fragment)
                        .commit()
                    childFragmentManager.beginTransaction()
                        .add(R.id.detailFragmentContainer, detailFragment).commit()

                }
                else -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.listFragmentContainer, fragment, "1")
                        .addToBackStack(null)
                        .commit()
                }
            }


        }



        return view
    }


    override fun onChange(data: Any) {
        Toast.makeText(activity?.baseContext, data as String, Toast.LENGTH_SHORT).show()
    }
}