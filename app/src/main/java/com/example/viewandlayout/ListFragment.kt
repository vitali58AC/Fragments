package com.example.viewandlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.viewandlayout.databinding.FragmentListBinding

class ListFragment() : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val parentListener = parentFragment as? IOnDetailChangeListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.buildFirstApp.setOnClickListener {
            parentListener?.onChange("Example string in Interface listener")
            val isTablet = context?.resources?.getBoolean(R.bool.isTablet)
            if (isTablet == false) {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    replace(R.id.listFragmentContainer, DetailFragmentFirstApp())
                    addToBackStack("1")

                }
            } else {
                Toast.makeText(activity?.baseContext, "View now is open", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}