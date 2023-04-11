package com.example.pairmatch.ui.leaderBoard

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentLeaderBoardBinding

class LeaderBoardFragment :
    BaseFragment<FragmentLeaderBoardBinding>(FragmentLeaderBoardBinding::inflate) {
    private val vm by viewModels<LeaderBoardViewModel>()
    private val leaders = LeaderBoardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getUserData()
        vm.getUsers()

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
                        ContextCompat.getDrawable(requireContext(), R.drawable.female_user_avatar)

                userName.text = data?.user_name?.replace(" ", "\n")
                tvUserBalance.text = data?.user_balance?.toInt().toString()
                println(data)
            }

            vm.users.observe(viewLifecycleOwner) { list ->
                if (list != null) {
                    leaders.items = list
                    println(list)
                }
            }

            rvLeaders.adapter = leaders
            rvLeaders.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }


}