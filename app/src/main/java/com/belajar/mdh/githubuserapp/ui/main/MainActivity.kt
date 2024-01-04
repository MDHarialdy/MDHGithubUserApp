package com.belajar.mdh.githubuserapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdh.githubuserapp.ui.detail.DetailActivity
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteUserActivity
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkModeViewModel
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkViewModelFactory
import com.belajar.mdh.githubuserapp.ui.darkmode.SettingActivity
import com.belajar.mdh.githubuserapp.utils.SettingPreferences
import com.belajar.mdh.githubuserapp.utils.dataStore
import com.belajar.mdhgithubuserapp.R
import com.belajar.mdhgithubuserapp.databinding.ActivityMainBinding


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    private val viewModel by viewModels<MainViewModel>{
        ViewModelFactory.getInstance(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabFavorite.setOnClickListener{
            val favorite = Intent(this, FavoriteUserActivity::class.java)
            startActivity(favorite)
        }
        observerTheme()
        setUpViewModel()
    }


    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingMenu = Intent(this, SettingActivity::class.java)

        when(item.itemId){
            R.id.settings_menu -> {startActivity(settingMenu)}
        }
        return super.onOptionsItemSelected(item)
    }

    //setupViewModel
    private fun setUpViewModel(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getUser()
        viewModel.responseUser.observe(this){ List ->
            userAdapter = UserAdapter(List)
            if (List != null){
                binding.recyclerView.apply {
                    adapter = userAdapter
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    //theme
    private fun observerTheme(){
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, DarkViewModelFactory(pref))[DarkModeViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            themeUpdate(isDarkModeActive)
        }
    }

    private fun themeUpdate(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}