package com.example.pairmatch.ui.news

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.pairmatch.BaseFragment

//class PageFragment : BaseFragment<FragmentPageBinding>(FragmentPageBinding::inflate) {
//
//    private val args: PageFragmentArgs by navArgs()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initViews()
//        initListeners()
//    }
//
//    private fun initViews() {
//        binding?.run {
//            args.let {
//                tvTitle.text = it.newsData?.title
//                tvDescription.text = it.newsData?.text
//                Glide.with(requireContext()).load(it.newsData?.logo).into(ivLogo)
//            }
//        }
//    }
//
//    private fun initListeners() {
//        binding?.btnBack?.setOnClickListener {
//            onBackPressed()
//        }
//    }
//}