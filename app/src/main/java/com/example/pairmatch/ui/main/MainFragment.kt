package com.example.pairmatch.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentMainBinding
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import com.example.pairmatch.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val vm : MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.getUserData()
            vm.getBetData()
            vm.getMatchesData()
        }


        initViews()
        initListeners()
    }

    private fun initViews() {

        binding?.run {
            rvTeam.adapter = mainAdapter
            rvTeam.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            vm.userData.observe(viewLifecycleOwner) { data ->
//                matchAdapter.userID = data?.user_uid
                if (data?.user_gender == "male")
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.male_user_avatar)
                else
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.male_user_avatar)

                userName.text = data?.user_name
                tvUserBalance.text = data?.user_balance?.toInt().toString()
            }



        }
    }


    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        binding?.run {
            btnPlusValue.setOnClickListener {
                it.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.other_btn))
                if (tvBetValue.text.toString().toDouble() >= 1000.0) {
                    val plusValue = tvBetValue.text.toString().toDouble() + 500.0
                    tvBetValue.text = plusValue.toInt().toString()
                }
            }
            btnMinusValue.setOnClickListener {
                it.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.other_btn))
                if (tvBetValue.text.toString().toDouble() != 1000.0) {
                    val minusValue = tvBetValue.text.toString().toDouble() - 500.0
                    tvBetValue.text = minusValue.toInt().toString()
                }
            }
        }
    }
}