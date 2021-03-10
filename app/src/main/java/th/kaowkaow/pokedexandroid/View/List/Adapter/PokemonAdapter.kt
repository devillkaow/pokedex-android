package th.kaowkaow.pokedexandroid.View.List.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_list.view.*
import th.kaowkaow.pokedexandroid.Model.Card
import th.kaowkaow.pokedexandroid.R

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ViewHolder>(), Filterable {
    private var pokemonListCard: MutableList<Card> = mutableListOf()
    private var filterPokemonList: MutableList<Card> = mutableListOf()
    private var charString = ""
    private var mListener: OnClickListener? = null

    interface OnClickListener {
        fun onItemClick(name: String)
    }

    fun setOnItemClickListerner(listener: OnClickListener?) {
        mListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun buildView(pokemon: Card) {
            itemView.tvName.text = pokemon.name

            itemView.progressBarHP.progress = pokemon.hp

            itemView.btnAdd.setOnClickListener {
                mListener?.onItemClick(pokemon.name)
            }

            var rarity = String()
            for (i in 1..pokemon.rarity) {
                rarity += "⭐️"
            }
            var dm = 0
            pokemon.attacks.forEach {
                dm += it.damage.toIntOrNull() ?: 0
            }

            itemView.progressBarSTR.progress = dm - pokemon.weaknesses.size
            itemView.progressBarWEAK.progress = pokemon.weaknesses.size * 10
            itemView.star.text = rarity
            Glide.with(itemView.context).load(pokemon.imageUrl).into(itemView.imgCardAdd)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.buildView(filterPokemonList[position])
    }

    override fun getItemCount(): Int = filterPokemonList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charString = constraint.toString().toLowerCase().trim()
                    .trim()
                filterPokemonList =
                    if (charString == "") {
                        pokemonListCard
                    } else {
                        pokemonListCard.filter {
                            it.name.toLowerCase().trim().contains(charString)
                        }.toMutableList()
                    }

                return FilterResults().apply {
                    count = filterPokemonList.size
                    values = filterPokemonList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPokemonList = results?.values as MutableList<Card>
                notifyDataSetChanged()
            }

        }
    }

    fun setListData(pokemonList: MutableList<Card>) {
        pokemonListCard.clear()
        pokemonListCard.addAll(pokemonList)
        filterPokemonList.clear()
        filterPokemonList.addAll(pokemonList)
        notifyDataSetChanged()
    }
}