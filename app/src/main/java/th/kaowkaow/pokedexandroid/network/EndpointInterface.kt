package th.kaowkaow.pokedexandroid.network

import retrofit2.Call
import retrofit2.http.GET
import th.kaowkaow.pokedexandroid.Model.PokemonModel

interface EndpointInterface {
    @GET("v3/f9916417-f92e-478e-bfbc-c39e43f7c75b")
    fun getListPokemon(): Call<PokemonModel>
}