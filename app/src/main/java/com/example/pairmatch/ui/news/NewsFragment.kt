package com.example.pairmatch.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.BottomNavigationActivity
import com.example.pairmatch.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val vm by viewModels<NewsViewModule>()
    private val mainAdapter = NewsAdapter {
        //navController.navigate(NewsFragmentDirections.actionNewsFragmentToPageFragment(it))
        (activity as BottomNavigationActivity).hideBnv()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as BottomNavigationActivity).showBnv()

        initData()
        initViews()
    }

    private fun initData() {
        vm.news.observe(viewLifecycleOwner){
            println(it.news)
            mainAdapter.items = it.news
        }
    }


    private fun initViews() {
        binding?.run {
            rvNews.adapter = mainAdapter
            rvNews.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }


}