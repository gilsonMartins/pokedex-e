package com.example.pockedex.domain

import com.example.pockedex.BuildConfig
import java.util.*

data class Pokemon(
    val number: Int,
    val name: String,
    val types: List<PokemonType>
) {
    val formattedName =
        name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    val formattedNumber = number.toString().padStart(3, '0')

    val imageUrl = BuildConfig.URL_IMG + "$formattedNumber.png"
}
