package com.example.pockedex.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockedex.data.exception.Resource
import com.example.pockedex.data.model.Pokemon
import com.example.pockedex.data.model.PokemonApiResult
import com.example.pockedex.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    var pokemonInfo = MutableLiveData<Resource<Pokemon>>()
    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            when (val pokemonsApiResult = pokemonRepository.getPokemonInfo(pokemonName)) {
                is Resource.Success -> {
                    pokemonInfo.value = pokemonsApiResult
                }
                is Resource.Error -> {
                }
                else -> {}
            }
        }
    }
}