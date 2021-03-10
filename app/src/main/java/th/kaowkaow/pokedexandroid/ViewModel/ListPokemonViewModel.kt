package th.kaowkaow.pokedexandroid.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import th.kaowkaow.pokedexandroid.Model.Card
import th.kaowkaow.pokedexandroid.Model.PokemonModel
import th.kaowkaow.pokedexandroid.network.EndpointInterface

class ListPokemonViewModel(private val apiService: EndpointInterface) : ViewModel() {
    val callSuccessResponse: MutableLiveData<MutableList<Card>> = MutableLiveData()
    val callFailResponse: MutableLiveData<String> = MutableLiveData()

    private var pokemon = PokemonModel()

    fun callGetListPokemon() {

        apiService.getListPokemon().enqueue(object : Callback<PokemonModel> {
            override fun onResponse(
                call: Call<PokemonModel>,
                response: Response<PokemonModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pokemon = it
                        callSuccessResponse.postValue(pokemon.cards)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                callFailResponse.postValue(t.message)
            }
        })
    }

    fun setSelectPokemon(name: String) {
        pokemon.cards.find {
            it.name == name
        }?.isSelected = true
    }

    fun getData(): PokemonModel {
        return pokemon
    }
}