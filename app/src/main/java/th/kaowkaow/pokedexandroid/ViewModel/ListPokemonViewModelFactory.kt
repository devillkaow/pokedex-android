package th.kaowkaow.pokedexandroid.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import th.kaowkaow.pokedexandroid.network.EndpointInterface

class ListPokemonViewModelFactory(private val apiService: EndpointInterface): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EndpointInterface::class.java).newInstance(apiService)
    }
}