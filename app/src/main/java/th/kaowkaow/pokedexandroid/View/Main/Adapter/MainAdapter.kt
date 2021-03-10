package th.kaowkaow.pokedexandroid.View.Main.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_list.view.*
import th.kaowkaow.pokedexandroid.Model.Card
import th.kaowkaow.pokedexandroid.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var pokemonListCard: MutableList<Card> = mutableListOf()
//    private var mListener: MainAdapter.OnClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun buildView(pokemon: Card) {
            itemView.tvName.text = pokemon.name

            itemView.progressBarHP.progress = pokemon.hp

//            itemView.addButton.setOnClickListener {
//                mListener?.onItemClick(pokemon.name)
//            }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.buildView(pokemonListCard[position])
    }

    override fun getItemCount(): Int = pokemonListCard.size

    fun setListData(pokemonList: MutableList<Card>) {
        pokemonListCard.clear()
        pokemonListCard.addAll(pokemonList)
        notifyDataSetChanged()
    }

}