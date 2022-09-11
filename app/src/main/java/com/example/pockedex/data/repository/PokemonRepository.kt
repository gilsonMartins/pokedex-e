package com.example.pockedex.data.repository

import com.example.pockedex.data.exception.Resource
import com.example.pockedex.data.model.Pokemon
import com.example.pockedex.data.model.PokemonApiResult
import com.example.pockedex.data.model.PokemonsApiResult

interface PokemonRepository {
    suspend fun listPokemons(limit: Int, offset: Int): Resource<PokemonsApiResult>?
    suspend fun getPokemon(number: Int):  Resource<PokemonApiResult>
    suspend fun getPokemonInfo(pokemonName:String):  Resource<Pokemon>
}
