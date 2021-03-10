package th.kaowkaow.pokedexandroid.View.List

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_pokemon.*
import th.kaowkaow.pokedexandroid.Model.PokemonModel
import th.kaowkaow.pokedexandroid.R
import th.kaowkaow.pokedexandroid.View.List.Adapter.PokemonAdapter
import th.kaowkaow.pokedexandroid.ViewModel.ListPokemonViewModel
import th.kaowkaow.pokedexandroid.ViewModel.ListPokemonViewModelFactory
import th.kaowkaow.pokedexandroid.ViewModel.MainViewModel
import th.kaowkaow.pokedexandroid.network.ApiService
import th.kaowkaow.pokedexandroid.network.EndpointInterface

class ListPokemonActivity : AppCompatActivity(), PokemonAdapter.OnClickListener {

    private val apiService: EndpointInterface by lazy {
        ApiService().createService(EndpointInterface::class.java)
    }
    private val listPokeViewModelFactory: ListPokemonViewModelFactory by lazy {
        ListPokemonViewModelFactory(apiService)
    }
    lateinit var listPokeViewModel: ListPokemonViewModel

    private val resultIntent = Intent()
    lateinit var pokemonAdapter: PokemonAdapter
    lateinit var dataViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pokemon)

        listPokeViewModel = ViewModelProvider(
            this, listPokeViewModelFactory
        ).get(ListPokemonViewModel::class.java)

        dataViewModel = MainViewModel()

        intent.extras?.let {
            val poke = it.getParcelable<PokemonModel>("DATA")
            dataViewModel.setPokemonModel(poke ?: PokemonModel())
        }

        resultIntent.putExtra("DATA", dataViewModel.pokemon)

        setAdapter()
        opserveData()
        listPokeViewModel.callGetListPokemon()
    }

    private fun opserveData() {
        listPokeViewModel.callSuccessResponse.observe(this, Observer{
            pokemonAdapter.setListData(it)
        })
        listPokeViewModel.callFailResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun setAdapter() {
        pokemonAdapter = PokemonAdapter()
        pokemonAdapter?.setOnItemClickListerner(this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = pokemonAdapter
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pokemonAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onBackPressed() {
        setResult(111, resultIntent)
        resultIntent.putExtra("DATA", listPokeViewModel.getData())
        finish()
    }

    override fun onItemClick(name: String) {
        listPokeViewModel.setSelectPokemon(name)
    }
}