package com.example.pockedex.data.repository

import android.util.Log
import com.example.pockedex.data.api.PokemonService
import com.example.pockedex.data.exception.Resource
import com.example.pockedex.data.model.Pokemon
import com.example.pockedex.data.model.PokemonApiResult
import com.example.pockedex.data.model.PokemonsApiResult

class PokemonRepositoryImpl(private val service: PokemonService) : PokemonRepository {

    override suspend fun listPokemons(limit: Int, offset: Int): Resource<PokemonsApiResult> {
        val response = try {
            service.listPokemons( limit, offset)
        } catch (e: Exception) {
            return Resource.Error(e.message!!)
        }
        return Resource.Success(response.body()!!)
    }

    override suspend fun getPokemon(number: Int): Resource<PokemonApiResult> {
        val response = try {
            service.getPokemon(number)

        } catch (e: Exception) {
            return Resource.Error(e.message!!)
        }
        return Resource.Success(response.body()!!)
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            service.getPokemonInfo( pokemonName)
        } catch (e: Exception) {
            Log.e("eror", e.message.toString())
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}