package th.kaowkaow.pokedexandroid.ViewModel

import android.widget.Filter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import th.kaowkaow.pokedexandroid.Model.Card
import th.kaowkaow.pokedexandroid.Model.PokemonModel

class MainViewModel : ViewModel() {
    var pokemon = PokemonModel()

    var filterSucess: MutableLiveData<MutableList<Card>> = MutableLiveData()

    fun setPokemonModel(data: PokemonModel) {
        pokemon = data
    }

    fun filter(poke: PokemonModel): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                pokemon =  poke.cards.filter {
                    it.isSelected == true
                } as PokemonModel

                filterSucess.postValue(pokemon.cards)

                return FilterResults().apply {
                    count = pokemon.cards.size
                    values = pokemon.cards
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterSucess = results?.values as MutableLiveData<MutableList<Card>>
            }
        }

    }

}