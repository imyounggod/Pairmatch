package com.example.pairmatch.ui.historyMatch

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemHistoryBetBinding
import com.example.pairmatch.databinding.ItemTeamBinding
import com.example.pairmatch.entites.HistoryBet
import com.example.pairmatch.entites.Team

class HistoryBetAdapter : RecyclerView.Adapter<HistoryBetAdapter.VH>() {

    var items: MutableList<HistoryBet?> = mutableListOf()
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
                .inflate(R.layout.item_history_bet, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        items[position]?.let { holder.bind(it) }
    }

    inner class VH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemHistoryBetBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: HistoryBet) {
            binding.run {

                tvDate.text = data.team?.scheduleMatch!!

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

                tvPlayerPoint1.text = data.team?.member1?.score.toString()
                tvPlayerPoint2.text = data.team?.member2?.score.toString()
                tvPlayerPoint3.text = data.team?.member3?.score.toString()
                tvPlayerPoint4.text = data.team?.member4?.score.toString()
                tvPlayerPoint5.text = data.team?.member5?.score.toString()

                tvScoreTeam.text = data.team?.endPoints.toString()
                if(data.team?.teamPoints!! > data.team?.endPoints!!){
                    if(data.coef_los == true){
                        tvScore.text = (data.coef!!.toDouble() * data.bet_value!!.toInt()).toString()
                        boxResult.setBackgroundColor(Color.parseColor("#69B600"))
                    }else if (data.coef_win == true){
                        tvScore.text = "-${data.bet_value}"
                        boxResult.setBackgroundColor(Color.parseColor("#F92525"))
                    }
                }else{
                    if(data.coef_los == true){
                        tvScore.text = "-${data.bet_value}"
                        boxResult.setBackgroundColor(Color.parseColor("#F92525"))
                    }else if (data.coef_win == true){
                        tvScore.text = (data.coef!!.toDouble() * data.bet_value!!.toInt()).toString()
                        boxResult.setBackgroundColor(Color.parseColor("#69B600"))
                    }
                }
            }
        }
    }
}