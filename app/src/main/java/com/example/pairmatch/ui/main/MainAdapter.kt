package com.example.pairmatch.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemTeamBinding
import com.example.pairmatch.entites.Bet
import com.example.pairmatch.entites.Team

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.VH>() {

    var items: MutableList<Bet?> = mutableListOf()
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
        fun bind(data: Bet) {
            binding.run {

                tvDate.text = data.team?.scheduleMatch

                data.team?.member1?.photo?.let { ivSmallPlayerPhoto1.setImageResource(it) }
                data.team?.member2?.photo?.let { ivSmallPlayerPhoto2.setImageResource(it) }
                data.team?.member3?.photo?.let { ivSmallPlayerPhoto3.setImageResource(it) }
                data.team?.member4?.photo?.let { ivSmallPlayerPhoto4.setImageResource(it) }
                data.team?.member5?.photo?.let { ivSmallPlayerPhoto5.setImageResource(it) }

                tvPlayerName1.text = data.team?.member1?.name ?: ""
                tvPlayerName2.text = data.team?.member2?.name ?: ""
                tvPlayerName3.text = data.team?.member3?.name ?: ""
                tvPlayerName4.text = data.team?.member4?.name ?: ""
                tvPlayerName5.text = data.team?.member5?.name ?: ""

                tvPlayerPoint1.text ="%.1f".format(data.team?.member1?.score)
                tvPlayerPoint2.text = "%.1f".format(data.team?.member2?.score)
                tvPlayerPoint3.text = "%.1f".format(data.team?.member3?.score)
                tvPlayerPoint4.text = "%.1f".format(data.team?.member4?.score)
                tvPlayerPoint5.text = "%.1f".format(data.team?.member5?.score)

                tvScoreTeam.text = data.team?.teamPoints.toString()

                btnHighOefficient.text = data.team?.coefHigh.toString()
                btnLowOefficient.text = data.team?.coefLow.toString()
                btnHighOefficient.isEnabled = false
                btnLowOefficient.isEnabled = false
                if(data.team?.teamPoints!! > data.team?.endPoints!!){
                    if(data.coef_los == true){
                        btnHighOefficient.isEnabled = false
                        btnLowOefficient.isEnabled = true
                    }else if (data.coef_win == true){
                        btnHighOefficient.isEnabled = true
                        btnLowOefficient.isEnabled = false
                    }
                }else{
                    if(data.coef_los == true){
                        btnHighOefficient.isEnabled = false
                        btnLowOefficient.isEnabled = true
                    }else if (data.coef_win == true){
                        btnHighOefficient.isEnabled = true
                        btnLowOefficient.isEnabled = false
                    }
                }
            }
        }
    }
}