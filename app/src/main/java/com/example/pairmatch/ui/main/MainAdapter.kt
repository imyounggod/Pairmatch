package com.example.pairmatch.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemTeamBinding
import com.example.pairmatch.entites.Team

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.VH>() {

    var items: MutableList<Team?> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_team, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        items[position]?.let { holder.bind(it) }
    }

    inner class VH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemTeamBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: Team) {
            binding.run {

                tvDate.text = data.scheduleMatch

                data.member1?.photo?.let { ivSmallPlayerPhoto1.setImageResource(it) }
                data.member1?.photo?.let { ivSmallPlayerPhoto2.setImageResource(it) }
                data.member1?.photo?.let { ivSmallPlayerPhoto3.setImageResource(it) }
                data.member1?.photo?.let { ivSmallPlayerPhoto4.setImageResource(it) }
                data.member1?.photo?.let { ivSmallPlayerPhoto5.setImageResource(it) }

                tvPlayerName1.text = data.member1?.name ?: ""
                tvPlayerName2.text = data.member2?.name ?: ""
                tvPlayerName3.text = data.member3?.name ?: ""
                tvPlayerName4.text = data.member4?.name ?: ""
                tvPlayerName5.text = data.member5?.name ?: ""

                tvPlayerPoint1.text = data.member1?.score.toString()
                tvPlayerPoint2.text = data.member2?.score.toString()
                tvPlayerPoint3.text = data.member3?.score.toString()
                tvPlayerPoint4.text = data.member4?.score.toString()
                tvPlayerPoint5.text = data.member5?.score.toString()

                tvScoreTeam.text = data.teamPoints.toString()

                btnHighOefficient.text = data.coefHigh.toString()
                btnLowOefficient.text = data.coefLow.toString()

            }
        }
    }
}