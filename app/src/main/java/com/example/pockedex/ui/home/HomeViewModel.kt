package com.example.pockedex.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockedex.data.exception.Resource
import com.example.pockedex.data.repository.PokemonRepository
import com.example.pockedex.domain.Pokemon
import com.example.pockedex.domain.PokemonMapper
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    var pokemonList = MutableLiveData<List<Pokemon>>()
    var loadError = MutableLiveData("")
    var isLoading = MutableLiveData(false)
    var isSearching = MutableLiveData(false)

    private var isSearchStarting = true
    private var cachedPokemonList = listOf<Pokemon>()

    fun loadPokemons() {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val pokemonsApiResult = pokemonRepository.listPokemons(150, 15)) {
                is Resource.Success -> {
                    isLoading.value = false

                    pokemonsApiResult.data!!.results.let {
                        pokemonList.value =
                            it.map { pokemonResult ->
                                val number = pokemonResult.url
                                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                                    .replace("/", "").toInt()

                                val pokemonItem = pokemonRepository.getPokemon(number)
                                PokemonMapper.map(pokemonItem.data!!)
                            }
                        cachedPokemonList = pokemonList.value!!
                    }

                    loadError.value = ""

                }
                is Resource.Error -> {
                    loadError.value = pokemonsApiResult.message!!
                    isLoading.value = false
                }

            }
        }
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch?.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value!!
                isSearchStarting = false
            }
            pokemonList.value = results!!
            isSearching.value = true
        }
    }
}
