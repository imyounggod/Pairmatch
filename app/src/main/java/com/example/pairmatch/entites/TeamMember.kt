package com.example.pairmatch.entites

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "teamMember")
@TypeConverters(Converters::class)
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null,
    val photo: String? = null,
    val score: Double? = null
)

val arrTeam = mutableListOf(

    TeamMember(
        name = "Chi Babu",
        photo = "https://lasmarket.ru/wp-content/uploads/2020/04/21v414g14t.png",
        score = 16.0
    ),
    TeamMember(
        name = "Chi Babu",
        photo = "https://lasmarket.ru/wp-content/uploads/2020/04/21v414g14t.png",
        score = 11.0
    ),
    TeamMember(
        name = "Chi Babu",
        photo = "https://lasmarket.ru/wp-content/uploads/2020/04/21v414g14t.png",
        score = 12.0
    ),
    TeamMember(
        name = "Chi Babu",
        photo = "https://lasmarket.ru/wp-content/uploads/2020/04/21v414g14t.png",
        score = 150.0
    ),
    TeamMember(
        name = "Chi Babu",
        photo = "https://lasmarket.ru/wp-content/uploads/2020/04/21v414g14t.png",
        score = 10.0
    )
)