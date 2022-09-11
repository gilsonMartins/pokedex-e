package com.example.pockedex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pockedex.BuildConfig
import com.example.pockedex.databinding.ItemPokemonsBinding
import com.example.pockedex.domain.Pokemon
import java.util.*

class PokemonAdapter(
    private val pokemonList:List<Pokemon>,
    private val listener: Listener

) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {


    interface Listener {
        fun onItemClicked(prevendaSelecionada: Pokemon)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemPokemonsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemonList[position]

        holder.bindView(item)
    }

    inner class ViewHolder(private val binding: ItemPokemonsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Pokemon){
            binding.root.setOnClickListener {listener.onItemClicked(item)}
            val ivPokemon = binding.imageFilterView
            val tvNumber = binding.tvNumber
            val tvName = binding.tvName
            val tvType1 = binding.tvType1
            val tvType2 = binding.tvType2

            item.let {
                Glide.with(itemView.context).load(it.imageUrl).into(ivPokemon)

                tvNumber.text = "Nº ${it.formattedNumber}"
                tvName.text = it.formattedName
                tvType1.text =
                    it.types[0].name.replaceFirstChar { string-> if (string.isLowerCase()) string.titlecase(Locale.getDefault()) else string.toString() }

                if (it.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.text = it.types[1].name.replaceFirstChar { string->
                        if (string.isLowerCase()) string.titlecase(Locale.getDefault()) else string.toString()
                    }
                } else {
                    tvType2.visibility = View.GONE
                }
            }
        }
    }
}
