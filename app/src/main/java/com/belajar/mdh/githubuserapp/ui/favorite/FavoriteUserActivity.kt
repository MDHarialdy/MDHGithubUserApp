package com.belajar.mdh.githubuserapp.ui.favorite

import com.belajar.mdh.githubuserapp.ui.adapter.FavoriteAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteViewModel
import com.belajar.mdh.githubuserapp.ui.favorite.ViewModelFactory
import com.belajar.mdhgithubuserapp.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(this.application)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel.getAllUser().observe(this){
            val adapter = FavoriteAdapter(it)
            binding.rvFavorite.layoutManager  = LinearLayoutManager(this)
            binding.rvFavorite.adapter = adapter

            if (it != null){
                binding.progressBarFavorite.visibility = View.GONE
            }
        }
    }
}
