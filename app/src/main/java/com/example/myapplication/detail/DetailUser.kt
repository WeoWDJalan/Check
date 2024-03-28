package com.example.myapplication.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.model.DetailUserResponse
import com.example.myapplication.databinding.ActivityDetailUserBinding
import com.example.myapplication.fragment.FollowsFragment
import com.example.myapplication.utils.ResultUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username")?: ""

        viewModel.resultDetailUser.observe(this){
            when(it){
                is ResultUtil.Success<*> ->{
                    val user = it.data as DetailUserResponse
                    binding.image.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }

                    binding.tvAccount.text = user.login
                    binding.tvUsername.text = user.name
                    binding.tvFollowers.text = "${it.data.followers} Followers"
                    binding.tvFollowing.text = "${it.data.following} Following"
                }
                is ResultUtil.Error ->{
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is ResultUtil.Loading ->{
                    binding.barProgres.isVisible = it.isLoading

                }
            }
        }

        viewModel.getDetailUser(username)

        val fragments = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
        )
        val  titleFragments = mutableListOf(
            getString(R.string.follower), getString(R.string.following),
        )
        val adapter = PagerAdapter(this, fragments)
        binding.pagerView.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.pagerView) { tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
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
}