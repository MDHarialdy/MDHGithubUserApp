package com.belajar.mdh.githubuserapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.ui.adapter.OnItemClickListener
import com.belajar.mdh.githubuserapp.ui.adapter.UserAdapter
import com.belajar.mdh.githubuserapp.utils.ResultData
import com.belajar.mdhgithubuserapp.databinding.FragmentFollowBinding


@Suppress("UNCHECKED_CAST")
class FollowFragment : Fragment(), OnItemClickListener {

    private var binding:  FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter(listener = this)
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when(type){
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollow)
            }
            FOLLOWERS -> {
                viewModel.resultFollower.observe(viewLifecycleOwner, this::manageResultFollow)
            }
        }
    }

    private fun manageResultFollow(state: ResultData){
        when(state){
            is ResultData.Succes<*> ->{
                adapter.setData(newData = state.data as MutableList<GetUserItemResponse>)
            }
            is ResultData.Error -> {
                Toast.makeText(requireActivity(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is ResultData.loading ->{
                binding?.progressBarFragment?.isVisible = state.isLoading
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

    override fun onItemClick(user: GetUserItemResponse) {

    }
}