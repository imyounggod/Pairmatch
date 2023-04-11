package com.example.pairmatch.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
@TypeConverters(ConvertersTeam::class)
data class Bet(
    @PrimaryKey(autoGenerate = true) val id : Int,
    var bet_value: Int? = null,
    var coef_win: Boolean? = null,
    var coef_los: Boolean? = null,
    var coef: String? = null,
    var bet_is_ready: Boolean? = null,
    var team: Team? = null
)

class ConvertersTeam {
    @TypeConverter
    fun mapTeamToString(value: Team): String {
        val gson = Gson()
        val type = object : TypeToken<Team>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToTeam(value: String): Team {
        val gson = Gson()
        val type = object : TypeToken<Team>() {}.type
        return gson.fromJson(value, type)
    }
}