package com.belajar.mdh.githubuserapp.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.belajar.mdh.githubuserapp.data.model.ResponseDetailUser
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdhgithubuserapp.R.string
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteViewModel
import com.belajar.mdh.githubuserapp.ui.favorite.ViewModelFactory
import com.belajar.mdh.githubuserapp.utils.ResultData
import com.belajar.mdhgithubuserapp.R
import com.belajar.mdhgithubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    //inisialisasi ViewBinding dan ViewModel
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(this.application)}


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL) ?: ""


        //mendapatkan detail user
        viewModel.resultDetailUser.observe(this){
            when(it){
                is ResultData.Succes<*> ->{
                    val user = it.data as ResponseDetailUser
                    binding.imageDetail.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }
                    binding.tvNamaDetail.text = user.name
                    binding.tvUsernameRaw.text = username
                }
                is ResultData.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultData.loading ->{
                    binding.progressBarDetail.isVisible = it.isLoading
                }
            }

            //mendapatkan data apakah ada di database atau tidak
            favoriteViewModel.isFavorite(username).observe(this){
                if (it == null) {
                    binding.btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity,
                            R.drawable.favorite_border
                        )
                    )
                } else {
                    binding.btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity,
                            R.drawable.favorite_full
                        )
                    )
                }
            }
        }

        //memasukkan dan menghapus data
        favoriteViewModel.isFavorite(username).observe(this){ user ->
            binding.btnFav.setOnClickListener {
                if (user == null) {
                    binding.btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity,
                            R.drawable.favorite_full
                        )
                    )
                    val readUser = FavoriteEntity(username, avatarUrl)
                    favoriteViewModel.addToFavorites(readUser)
                } else {
                    binding.btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity,
                            R.drawable.favorite_border
                        )
                    )
                    favoriteViewModel.removeFromFavorites(user)
                }
            }
        }



        //mengambil total follower dan following
        viewModel.followerCount.observe(this){totalFollower ->
            binding.tvFollowerCount.text = totalFollower.toString() + " Follower"
        }

        viewModel.followingCount.observe(this){totalFollowing ->
            binding.tvFollowingCount.text = totalFollowing.toString() + " Following"
        }

        viewModel.getDetailUser(username)
        viewModel.getFollowing(username)
        viewModel.getFollower(username)

        //membuat tab layout
        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(string.follower), getString(string.following)
        )

        val adapter1 = DetailAdapter(this, fragments)
        binding.viewpagerTab.adapter = adapter1

        TabLayoutMediator(binding.TabLayout, binding.viewpagerTab){tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.TabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    viewModel.getFollower(username)
                }else{
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    companion object {
        const val EXTRA_USERNAME = "username"
        const val EXTRA_AVATAR_URL = "avatar_url"
    }
}