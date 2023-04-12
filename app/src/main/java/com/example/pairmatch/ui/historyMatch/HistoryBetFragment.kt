package com.example.pairmatch.ui.historyMatch

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentHistoryMatchBinding
import com.example.pairmatch.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryBetFragment : BaseFragment<FragmentHistoryMatchBinding>(FragmentHistoryMatchBinding::inflate) {

    private val vm: MainViewModel by viewModels()
    private val historyAdapter = HistoryBetAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.getUserData()
        }

        initViews()
    }

    private fun initViews() {
        binding?.run {
            vm.userData.observe(viewLifecycleOwner) { data ->
                if (data?.user_gender == "male")
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.male_user_avatar)
                else
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.male_user_avatar)

                userName.text = data?.user_name
                tvUserBalance.text = data?.user_balance?.toInt().toString()
            }

            rvBets.adapter = historyAdapter
            rvBets.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}