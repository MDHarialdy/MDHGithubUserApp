package com.belajar.mdh.githubuserapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdhgithubuserapp.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {

    private var binding:  FragmentFollowBinding? = null
    private lateinit var adapter: UserAdapter

    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    var type = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        adapter = UserAdapter(mutableListOf())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding recycle view
        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when(type){
            FOLLOWING -> {
                viewModel.responseFollowing.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            FOLLOWERS -> {
                viewModel.responseFollower.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }
    }
}