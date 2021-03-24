package com.example.viewandlayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
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
        binding.constraintContainer.let {it as ViewGroup}
            .children
            .mapNotNull { it as? TextView }
            .forEach {
                it.setOnClickListener {textView ->
                    showFragmentBuildFirstApp(textView.toString())
                }
            }

        return view
    }


}

private fun showFragmentBuildFirstApp(clickText: String) {
    Log.d("ListFragment", "click on $clickText")
}