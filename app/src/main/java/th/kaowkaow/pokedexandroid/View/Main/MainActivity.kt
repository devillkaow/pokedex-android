package th.kaowkaow.pokedexandroid.View.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import th.kaowkaow.pokedexandroid.Model.PokemonModel
import th.kaowkaow.pokedexandroid.R
import th.kaowkaow.pokedexandroid.View.List.ListPokemonActivity
import th.kaowkaow.pokedexandroid.View.Main.Adapter.MainAdapter
import th.kaowkaow.pokedexandroid.ViewModel.MainViewModel
import th.kaowkaow.pokedexandroid.ViewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val mainViewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory()
    }
    lateinit var mainViewModel: MainViewModel
    lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(
                this,
                mainViewModelFactory
        ).get(MainViewModel::class.java)

        btnClick.setOnClickListener {
            val intent = Intent(this, ListPokemonActivity::class.java).apply {
                putExtra("DATA", mainViewModel.pokemon)
            }
            startActivityForResult(intent, 201)
        }
        setAdapter()
        observeData()
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter()
//        mainAdapter?.setOnItemClckListerner(this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mainAdapter
    }

    private fun observeData() {
        mainViewModel.filterSucess.observe(this, Observer {
            mainAdapter.setListData(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            201 -> {
                if (resultCode == 111) {
                    data?.extras?.let {
                        val poke = it.getParcelable<PokemonModel>("DATA")
                        mainViewModel.setPokemonModel(poke ?: PokemonModel())
                        mainViewModel.filter(poke ?: PokemonModel())
                    }
                }
            }
        }
    }
}

