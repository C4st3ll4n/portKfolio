package br.com.dio.app.repositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val mainViewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { RepoListAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mainViewModel.getRepositories("C4st3ll4n")

        mainViewModel.repos.observe(this,{
            when (it){
                is MainViewModel.State.Error -> { }
                MainViewModel.State.Loading -> {}
                is MainViewModel.State.Success -> adapter.submitList(it.list)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = (menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "$query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "$newText")
        return true
    }

    companion object{
        private const val TAG = "MainActivity"
    }

}