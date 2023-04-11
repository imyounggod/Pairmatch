package com.example.pairmatch.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity()
@TypeConverters(Converters::class)
data class Team(@PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val scheduleMatch: String? = null,
    val member1: TeamMember? = null,
    val member2: TeamMember? = null,
    val member3: TeamMember? = null,
    val member4: TeamMember? = null,

    val member5: TeamMember? = null,
    val teamPoints: Double? = null,
    val coefLow: Double? = null,
    val coefHigh: Double? = null
)


class Converters {
    @TypeConverter
    fun mapTeamMemberToString(value: TeamMember): String {
        val gson = Gson()
        val type = object : TypeToken<TeamMember>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToTeamMember(value: String): TeamMember {
        val gson = Gson()
        val type = object : TypeToken<TeamMember>() {}.type
        return gson.fromJson(value, type)
    }
}