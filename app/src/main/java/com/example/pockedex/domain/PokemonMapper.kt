package com.example.pockedex.domain

import com.example.pockedex.data.model.PokemonApiResult
import com.example.pockedex.data.model.PokemonTypeSlot

class PokemonMapper {
    companion object {
        fun map(pockemon: PokemonApiResult): Pokemon =
            Pokemon(
                pockemon.id,
                pockemon.name,
                mapTypes(pockemon.types),
            )

        private fun mapTypes(types: List<PokemonTypeSlot>): List<PokemonType> =
            listOf(
                PokemonType(
                    types.map { it.type.name }.first()
                )
            )

    }


}