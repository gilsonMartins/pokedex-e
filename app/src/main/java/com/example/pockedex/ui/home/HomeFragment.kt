package com.example.pockedex.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pockedex.R
import com.example.pockedex.base.BaseFragment
import com.example.pockedex.databinding.FragmentHomeBinding
import com.example.pockedex.domain.Pokemon
import com.example.pockedex.ui.adapter.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(), TextView.OnEditorActionListener {
    private val viewModel: HomeViewModel by sharedViewModel()
    private var adaptador: PokemonAdapter? = null
    private val recyclerView by lazy {
        binding.recyclerView
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPokemons()
        binding.editQuery.setOnEditorActionListener(this)
        observerPokemonList()
        onFinish()

    }

    private fun observerPokemonList() {
        viewModel.pokemonList.observe(viewLifecycleOwner) {
            binding.viewFlipper.displayedChild = 1
            createAdapter(it)
        }

    }

    private fun createAdapter(it: List<Pokemon>) {
        binding.aleatory.setOnClickListener { view ->
            findNavController().navigate(
                R.id.action_home_open_details,
                bundleOf("pokemon" to it.random().name)
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptador = PokemonAdapter(
            it, AdapterListener()
        )
        recyclerView.adapter = adaptador
    }

    private fun onFinish() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    createModal()
                }
            })
    }

    private fun createModal() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.exit_app)
        builder.setPositiveButton("Yes") { dialog, _ ->
            requireActivity().finish()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private inner class AdapterListener : PokemonAdapter.Listener {
        override fun onItemClicked(prevendaSelecionada: Pokemon) {
            findNavController().navigate(
                R.id.action_home_open_details,
                bundleOf("pokemon" to prevendaSelecionada.name)
            )
        }

    }

    override fun onEditorAction(textView: TextView, actions: Int, event: KeyEvent?): Boolean {
        if (actions == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.searchPokemonList(binding.editQuery.text.toString())

            return true
        }
        return false
    }
}