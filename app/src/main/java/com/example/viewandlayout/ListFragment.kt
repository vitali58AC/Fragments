package com.example.viewandlayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.viewandlayout.databinding.FragmentListBinding

class ListFragment(): Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.buildFirstApp.setOnClickListener {
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                replace(R.id.listFragmentContainer, DetailFragmentFirstApp())
                addToBackStack(null)
            }
        }
/*        binding.constraintContainer.let {it as ViewGroup}
            .children
            .mapNotNull { it as? TextView }
            .forEach {
                it.setOnClickListener {textView ->
                    showFragmentBuildFirstApp(textView.toString())
                    childFragmentManager.beginTransaction()
                        .replace(R.id.listFragmentContainer, DetailFragmentFirstApp())
                        .commit()
                }
            }*/

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

private fun showFragmentBuildFirstApp(clickText: String) {
    Log.d("ListFragment", "click on $clickText")

}