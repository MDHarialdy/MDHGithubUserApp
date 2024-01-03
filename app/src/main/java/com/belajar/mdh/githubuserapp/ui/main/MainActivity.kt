package com.belajar.mdh.githubuserapp.ui.main

import androidx.core.view.MenuItemCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.data.model.Item
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdh.githubuserapp.ui.detail.DetailActivity
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteUserActivity
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkModeViewModel
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkViewModelFactory
import com.belajar.mdh.githubuserapp.ui.darkmode.SettingActivity
import com.belajar.mdh.githubuserapp.utils.ResultData
import com.belajar.mdh.githubuserapp.utils.SettingPreferences
import com.belajar.mdh.githubuserapp.utils.dataStore
import com.belajar.mdhgithubuserapp.R
import com.belajar.mdhgithubuserapp.databinding.ActivityMainBinding


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    lateinit var toolbar: Toolbar

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", it.login)
                putExtra("avatar_url", it.avatar_url)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //observe tema
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, DarkViewModelFactory(pref))[DarkModeViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->

            themeUpdate(isDarkModeActive)
        }



        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        //bindig untuk melakukan pencarian
        binding.searchview.setIconifiedByDefault(true) // Menjadikan SearchView berbentuk ikon pencarian
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.resultData.observe(this) {
            when (it) {
                is ResultData.Succes<*> -> {
                    adapter.setData(it.data as MutableList<Item>)
                }

                is ResultData.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is ResultData.loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //action ketika menu di klik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val favoriteMenu = Intent(this, FavoriteUserActivity::class.java)
        val settingMenu = Intent(this, SettingActivity::class.java)

        when(item.itemId){
            R.id.favorite_menu -> {startActivity(favoriteMenu)}
            R.id.settings_menu -> {startActivity(settingMenu)}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun themeUpdate(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}