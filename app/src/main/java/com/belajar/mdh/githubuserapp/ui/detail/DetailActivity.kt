package com.belajar.mdh.githubuserapp.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdhgithubuserapp.R.string
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteViewModel
import com.belajar.mdhgithubuserapp.R
import com.belajar.mdhgithubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    //inisialisasi ViewBinding dan ViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var username: String
    private lateinit var avatarUrl: String

    private val favoriteViewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(this)}
    private val detailViewModel: DetailViewModel by viewModels{ ViewModelFactory.getInstance(this)}

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""

        setupViewModel()
        setupTabLayout()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModel(){
        binding.progressBarDetail.visibility = View.VISIBLE
        detailViewModel.getDetailUser(username)

        //getDetail
        detailViewModel.responseDetail.observe(this){user ->
            avatarUrl = user.avatar_url
            binding.tvDetailName.text = "Name: " + user.name
            binding.tvFollower.text = "Follower: " + user.followers.toString()
            binding.tvFollowing.text = "Following: " + user.following.toString()
            binding.tvLocation.text = "Location: " + user.location
            binding.imageView.load(user.avatar_url)
            binding.progressBarDetail.visibility = View.INVISIBLE
        }


        //favorite
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
    }

    //tab
    private fun setupTabLayout(){
        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragment = mutableListOf(
            getString(string.follower), getString(string.following)
        )

        val tabAdapter = TabAdapter(this, fragments)
        binding.viewpagerTab.adapter = tabAdapter

        TabLayoutMediator(binding.TabLayout, binding.viewpagerTab){tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.TabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    detailViewModel.getFollower(username)
                }else{
                    detailViewModel.getFollowing(username)
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
    }
}