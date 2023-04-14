package com.example.pairmatch.ui.leaderBoard

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemLeadersBinding
import com.example.pairmatch.entites.User

class LeaderBoardAdapter : RecyclerView.Adapter<LeaderBoardAdapter.VH>() {

    var items: MutableList<User?> = mutableListOf()
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
                .inflate(R.layout.item_leaders, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        items[position]?.let { holder.bind(it, position) }
    }

    class VH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemLeadersBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: User, position: Int) {
            binding.run {

                if (data.user_gender == "male")
                    userAvatar.background = ContextCompat.getDrawable(itemView.context, R.drawable.male_user_avatar)
                else
                    userAvatar.background = ContextCompat.getDrawable(itemView.context, R.drawable.female_user_avatar)

                userPosition.text = (position+1).toString()
                userName.text = data.user_name
                tvUserBalance.text = data.user_balance?.toInt().toString()
            }
        }
    }
}