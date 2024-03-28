package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.adapter.UserAdapter
import com.example.myapplication.data.model.SearchResponse
import com.example.myapplication.databinding.FragmentFollowsBinding
import com.example.myapplication.detail.DetailViewModel
import com.example.myapplication.utils.ResultUtil

class FollowsFragment : Fragment() {

    private var binding:FragmentFollowsBinding? = null
    private val adapter by lazy {
        UserAdapter{

        }
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type: Int= 0 //var type: 0 tadi error

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollow?. apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }

        when(type){
            FOLLOWERS ->{
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING ->{
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state:ResultUtil){
        when(state){
            is ResultUtil.Success<*> ->{
                adapter.setData(state.data as MutableList<SearchResponse.Item>)

            }
            is ResultUtil.Error ->{
                Toast.makeText(requireActivity(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()

            }
            is ResultUtil.Loading ->{
                binding?.progressBar3?.isVisible = state.isLoading

            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101
        fun newInstance(type:Int) = FollowsFragment()
            .apply {
                this.type = type
            }
    }
}