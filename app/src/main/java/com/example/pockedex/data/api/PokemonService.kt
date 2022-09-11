package com.example.pockedex.data.api
import com.example.pockedex.data.model.Pokemon
import com.example.pockedex.data.model.PokemonApiResult
import com.example.pockedex.data.model.PokemonsApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonsApiResult>

    @GET("pokemon/{number}")
    suspend fun getPokemon(
        @Path("number") number: Int
    ): Response<PokemonApiResult>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}
