package com.example.pairmatch.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

@Entity()
@TypeConverters(Converters::class)
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val scheduleMatch: String? = null,
    var member1: TeamMember? = null,
    var member2: TeamMember? = null,
    var member3: TeamMember? = null,
    var member4: TeamMember? = null,
    var member5: TeamMember? = null,
    val teamPoints: Double? = 0.0,
    var endPoints: Double? = 0.0,
    val coefLow: Double? = (Random.nextDouble(0.5, 7.0) * 100).toInt() / 100.0,
    val coefHigh: Double? = (Random.nextDouble(0.5, 7.0) * 100).toInt() / 100.0
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