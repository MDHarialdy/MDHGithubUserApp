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
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdh.githubuserapp.ui.adapter.OnItemClickListener
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdh.githubuserapp.ui.detail.DetailActivity
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteUserActivity
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkModeViewModel
import com.belajar.mdh.githubuserapp.ui.darkmode.SettingActivity
import com.belajar.mdh.githubuserapp.utils.showToast
import com.belajar.mdhgithubuserapp.R
import com.belajar.mdhgithubuserapp.databinding.ActivityMainBinding


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(), OnItemClickListener {

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
        viewModel.getUser { error ->
            showToast(this, error)
        }
        //observe
        viewModel.responseUser.observe(this){ List ->
            if (List != null){

                //init adapter
                userAdapter = UserAdapter(List, listener = this)

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
        val settingViewModel by viewModels <DarkModeViewModel>{ViewModelFactory.getInstance(this)}
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

    override fun onItemClick(user: GetUserItemResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
        startActivity(intent)
    }
}