package com.example.pockedex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pockedex.R
import com.example.pockedex.base.BaseFragment
import com.example.pockedex.base.TypeUtils
import com.example.pockedex.data.exception.Resource
import com.example.pockedex.data.model.Pokemon
import com.example.pockedex.databinding.FragmentDetailsPokemonBinding
import com.example.pockedex.domain.PokemonType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class DetailsPokemonFragment : BaseFragment<FragmentDetailsPokemonBinding>() {
    private val viewModel: DetailsViewModel by sharedViewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsPokemonBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.containsKey("pokemon") == true)
            viewModel.getPokemonInfo(arguments?.getString("pokemon", "")!!)
        val toolbar: Toolbar = binding.toolbar

        toolbar.title = arguments?.getString("pokemon")
        toolbar.menu
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        observerPokemon()
        onBackPressed()
    }

    private fun observerPokemon() {
        viewModel.pokemonInfo.observe(viewLifecycleOwner) {
            with(binding) {
                setUpFields(it, it.data)
            }
        }
    }

    private fun FragmentDetailsPokemonBinding.setUpFields(
        it: Resource<Pokemon>,
        response: Pokemon?
    ) {
        binding.backgroundMain.background = ContextCompat.getDrawable(
            requireContext(),
            parseTypeToColor(it.data!!.types.first().type)
        )
        Glide.with(requireContext()).load(response?.imageUrl)
            .into(binding.imageFilterView2)
        this.NamePokemon.text = response?.name
        if (response?.types?.size!! > 1) {
            binding.textType2.visibility = VISIBLE
            this.textType2.text =
                response.types[1].type.name.capitalize(Locale.ROOT)
        }
        this.textType.text =
            response.types[0].type.name.capitalize(Locale.ROOT)
        this.textWeight.text = response.weight.toString()
        this.textHeight.text = response.height.toString()
    }


    private fun parseTypeToColor(type: PokemonType): Int {
        return TypeUtils.values().first {
            type.name == it.name.lowercase()
        }.TypeUtils

    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_navigation_home_details_to_navigation_home_watch_face)
                }
            })
    }
}