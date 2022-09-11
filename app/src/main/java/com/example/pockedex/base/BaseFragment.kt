package com.example.pockedex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.pockedex.databinding.ActivityMainBinding

abstract class BaseFragment <T : ViewBinding> :Fragment(){
    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Get the concrete generated ViewBinding class for the [mContentLayoutId]
     * sent in the constructor, to be applied in the [binding]
     */
    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): T
}