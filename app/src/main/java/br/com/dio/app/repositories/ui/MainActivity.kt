package br.com.dio.app.repositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.core.hideSoftKeyboard
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val dialog by lazy { createProgressDialog() }
    private val mainViewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { RepoListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rvRepos.adapter = adapter

        mainViewModel.repos.observe(this, {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
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
        query?.let {
            mainViewModel.getRepositories(it)
        }

        binding.root.hideSoftKeyboard()

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "$newText")
        return false
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}