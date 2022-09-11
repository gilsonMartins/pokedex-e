package com.example.pockedex.data.model

import com.example.pockedex.domain.PokemonType

data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>,
    val height: Int,
    val weight: Int
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonsApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)
data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)
