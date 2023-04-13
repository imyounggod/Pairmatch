package com.example.pairmatch.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.BottomNavigationActivity
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentMainBinding
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import com.example.pairmatch.ui.auth.AuthViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val vm: MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()
    private var index = 0

    private var isLow = false
    private var isHigh = false

    private val playerAdapter = PlayerAdapter() { ind, player ->
        vm.selectPlayer(player)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.getUserData()
        }


        initViews()
        initListeners()
    }

    private fun initViews() {

        binding?.run {
            rvTeam.adapter = mainAdapter
            rvTeam.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvPlayers.adapter = playerAdapter
            rvPlayers.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

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
                    var minusValue = tvBetValue.text.toString().toDouble() - 500.0
                    if(minusValue < 0){
                        minusValue = 0.0
                    }
                    tvBetValue.text = minusValue.toInt().toString()
                }
            }
            vm.userData.observe(viewLifecycleOwner) { data ->
                if (data?.user_gender == "male")
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.male_user_avatar)
                else
                    userAvatar.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.female_user_avatar)

                userName.text = data?.user_name
                tvUserBalance.text = data?.user_balance?.toInt().toString()
            }

            vm.players.observe(viewLifecycleOwner) { data ->
                playerAdapter.items = data.toMutableList()
            }
            vm.bets.observe(viewLifecycleOwner){data->
                println(data)
                rvTeam.isVisible = true
                mainAdapter.items = data.toMutableList()
            }
            vm.event.observe(viewLifecycleOwner) { data ->
                when (data) {
                    is MainViewModel.ValidateEvent.Success -> {}
                    is MainViewModel.ValidateEvent.Error -> {
                        showMessage(data.text)
                    }
                }
            }
            vm.selectedTeam.observe(viewLifecycleOwner) { data ->
                println(data)
                namePlayer1.text = data.member1?.name ?: "Игрок 1"
                namePlayer2.text = data.member2?.name ?: "Игрок 2"
                namePlayer3.text = data.member3?.name ?: "Игрок 3"
                namePlayer4.text = data.member4?.name ?: "Игрок 4"
                namePlayer5.text = data.member5?.name ?: "Игрок 5"

                ivSmallPlayerPhoto1.setImageResource(data.member1?.photo ?: R.drawable.ic_plus_placeholder)
                ivSmallPlayerPhoto2.setImageResource(data.member2?.photo ?: R.drawable.ic_plus_placeholder)
                ivSmallPlayerPhoto3.setImageResource(data.member3?.photo ?: R.drawable.ic_plus_placeholder)
                ivSmallPlayerPhoto4.setImageResource(data.member4?.photo ?: R.drawable.ic_plus_placeholder)
                ivSmallPlayerPhoto5.setImageResource(data.member5?.photo ?: R.drawable.ic_plus_placeholder)

                if (data.member1 != null && data.member2 != null && data.member3 != null &&
                    data.member4 != null && data.member5 != null
                ) {
                    btnHighOefficient.text = data.coefHigh.toString()
                    btnLowOefficient.text = data.coefLow.toString()
                    betContainer.isVisible = true
                } else {
                    betContainer.isVisible = false
                }
                tvScoreTeam.text = data.teamPoints.toString()
            }
            member1.setOnClickListener {
                index = 1
                boxPlayers.isVisible = true
                boxMain.isVisible = false
                rvTeam.isVisible = false
                rvPlayers.isVisible = true
                (activity as BottomNavigationActivity).hideBnv()
            }
            member2.setOnClickListener {
                index = 2
                boxPlayers.isVisible = true
                boxMain.isVisible = false
                rvTeam.isVisible = false
                rvPlayers.isVisible = true
                (activity as BottomNavigationActivity).hideBnv()
            }
            member3.setOnClickListener {
                index = 3
                boxPlayers.isVisible = true
                boxMain.isVisible = false
                rvTeam.isVisible = false
                rvPlayers.isVisible = true
                (activity as BottomNavigationActivity).hideBnv()
            }
            member4.setOnClickListener {
                index = 4
                boxPlayers.isVisible = true
                boxMain.isVisible = false
                rvTeam.isVisible = false
                rvPlayers.isVisible = true
                (activity as BottomNavigationActivity).hideBnv()
            }
            member5.setOnClickListener {
                index = 5
                boxPlayers.isVisible = true
                boxMain.isVisible = false
                rvTeam.isVisible = false
                rvPlayers.isVisible = true
                (activity as BottomNavigationActivity).hideBnv()
            }
            tvSelectedDate.setOnClickListener {
                var mdp = MaterialDatePicker.Builder.dateRangePicker().setSelection(
                    Pair.create(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setStart(MaterialDatePicker.todayInUtcMilliseconds()).build()
                ).build()
                mdp.show(childFragmentManager, "pick")
                mdp.addOnPositiveButtonClickListener {
                    println(it)
                    val date1 = it.first
                    val date2 = it.second
                    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    tvSelectedDate.text = "${format.format(date1)} - ${format.format(date2)}"
                    vm.setDate(tvSelectedDate.text.toString())
                }
            }
            btnLowOefficient.setOnClickListener {
                isLow = true
                isHigh = false
            }
            btnHighOefficient.setOnClickListener {
                isHigh = true
                isLow = false
            }
            btnAddTeam.setOnClickListener {
                if (isLow) vm.setBet(tvBetValue.text.toString(), "low")
                if (isHigh) vm.setBet(tvBetValue.text.toString(), "high")
                if(!isLow && !isHigh) return@setOnClickListener
                tvSelectedDate.text = "дд.мм.гггг - дд.мм.гггг"
                isLow = false
                isHigh = false
                vm.isClearForm()
            }
            btnSelectPlayer.setOnClickListener {
                (activity as BottomNavigationActivity).showBnv()
                when (index) {
                    1 -> {
                        vm.selectedPlayer.value?.let { it1 -> vm.setMember1(it1) }
                    }
                    2 -> {
                        vm.selectedPlayer.value?.let { it1 -> vm.setMember2(it1) }
                    }
                    3 -> {
                        vm.selectedPlayer.value?.let { it1 -> vm.setMember3(it1) }
                    }
                    4 -> {
                        vm.selectedPlayer.value?.let { it1 -> vm.setMember4(it1) }
                    }
                    5 -> {
                        vm.selectedPlayer.value?.let { it1 -> vm.setMember5(it1) }
                    }
                }
                boxPlayers.isVisible = false
                boxMain.isVisible = true
                rvTeam.isVisible = true
                rvPlayers.isVisible = false
            }
        }
    }
}