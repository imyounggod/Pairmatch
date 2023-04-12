package com.example.pairmatch.ui.news

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.databinding.FragmentPageBinding

class PageFragment : BaseFragment<FragmentPageBinding>(FragmentPageBinding::inflate) {

    private val args: PageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() {
        binding?.run {
                tvTitle.text = args.newsData?.text
                tvDescription.text = args.newsData?.text
                Glide.with(requireContext()).load(args.newsData?.logo).into(ivLogo)
        }
    }

    private fun initListeners() {
        binding?.tvTitle?.setOnClickListener {
            onBackPressed()
        }
    }
}