package com.example.pairmatch.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemPlayerBinding
import com.example.pairmatch.entites.TeamMember

class PlayerAdapter (private val onClick: (index: Int, player: TeamMember) -> Unit):
    RecyclerView.Adapter<PlayerAdapter.VH>() {

    private var lastCheckedPosition = -1

    var items: MutableList<TeamMember?> = mutableListOf()
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
                .inflate(R.layout.item_player, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        items[position]?.let { holder.bind(it) }
    }

    inner class VH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPlayerBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: TeamMember) {
            binding.run {
                Glide.with(itemView.context).load(data.photo).into(photoPlayer)
                namePlayer.text = data.name
                dateSchedule.text = data.date
                leftTeam.text = data.teamLeft
                rightTeam.text = data.teamRight
                playerPoint.text = "%.1f".format( data.score)
                if (position == lastCheckedPosition) {
                    boxMain.setCardBackgroundColor(Color.parseColor("#FFFCC605"))
                } else{
                    boxMain.setCardBackgroundColor(Color.WHITE)
                }

                itemView.setOnClickListener {
                    onClick(position, data)
                    lastCheckedPosition = position
                    notifyItemRangeChanged(0, items.size)
                }
            }
        }
    }
}