package com.belajar.mdh.githubuserapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdh.githubuserapp.ui.adapter.OnItemClickListener
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdh.githubuserapp.ui.detail.DetailActivity
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteUserActivity
import com.belajar.mdh.githubuserapp.ui.darkmode.SettingViewModel
import com.belajar.mdh.githubuserapp.ui.darkmode.SettingActivity
import com.belajar.mdh.githubuserapp.utils.ResultData
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

        setUpViewModel()
        observeLiveData()
        observerTheme()
    }

    //getCodeFromSetting
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SETTINGS) {
            if (resultCode == RESULT_OK){
                setUpViewModel()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingMenu = Intent(this, SettingActivity::class.java)

        when(item.itemId){
            R.id.settings_menu -> { startActivityForResult(settingMenu, REQUEST_CODE_SETTINGS) }
        }
        return super.onOptionsItemSelected(item)
    }

    //setupViewModel
    private fun setUpViewModel(){

        viewModel.getUser { error ->
            showToast(this, error)
        }

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.searchUser(query.toString())
//                if (query == ""){
//                    viewModel.getUser { error ->
//                        showToast(this@MainActivity, error) }
//                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchUser(newText.toString())
                if (newText == ""){
                    viewModel.getUser { error ->
                        showToast(this@MainActivity, error)
                    }
                }
                return true
            }
        })
    }

    private fun observeLiveData(){

        userAdapter = UserAdapter(listener = this)

        viewModel.response.observe(this){resultData ->
            binding.recyclerView.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }

            when (resultData){
                is ResultData.Succes<*> -> {
                    userAdapter.setData(resultData.data as MutableList<GetUserItemResponse>)
                }

                is ResultData.Error -> {
                    Toast.makeText(this, resultData.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is  ResultData.loading -> {
                    binding.progressBar.isVisible = resultData.isLoading
                }
            }
        }
    }

    //theme
    private fun observerTheme(){
        val settingViewModel by viewModels <SettingViewModel>{ViewModelFactory.getInstance(this)}
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

    companion object {
        private const val REQUEST_CODE_SETTINGS = 123
    }
}