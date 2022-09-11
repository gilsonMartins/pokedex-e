package com.example.pockedex.data.model

import com.example.pockedex.BuildConfig


data class Pokemon(
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>
){
    private val formattedNumber = id.toString().padStart(3, '0')
    val imageUrl = BuildConfig.URL_IMG + "$formattedNumber.png"
}