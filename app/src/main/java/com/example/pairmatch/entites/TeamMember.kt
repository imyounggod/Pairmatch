package com.example.pairmatch.entites

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pairmatch.R

@Keep
@Entity(tableName = "teamMember")
@TypeConverters(Converters::class)
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null,
    val photo: Int? = null,
    val teamLeft: String? = null,
    val teamRight: String? = null,
    val date: String? = null,
    val score: Double = 0.0
)

val arrTeam = mutableListOf(

    TeamMember(
        name = "Андрей Николаев",
        photo = R.drawable.player1,
        teamLeft = "Реал Корона",
        teamRight = "Крепость ФК",
        date = "20.04.2023",
        score = 23.5
    ),
    TeamMember(
        name = "Иван Петров",
        teamLeft = "Бавария Львы",
        teamRight = "Манчестер Элита",
        photo = R.drawable.player2,
        date = "20.04.2023",
        score = 27.8
    ),
    TeamMember(
        name = "Михаил Гришин",
        teamLeft = "Барселона Юнайтед",
        teamRight = "Реал Футбол",
        photo = R.drawable.player3,
        date = "20.04.2023",
        score = 32.2
    ),
    TeamMember(
        name = "Дмитрий Козлов",
        teamLeft = "Клуб 100",
        teamRight = "Реал Корона",
        photo = R.drawable.player4,
        date = "20.04.2023",
        score = 18.9
    ),
    TeamMember(
        name = "Евгений Белов",
        teamLeft = "Манчестер Элита",
        teamRight = "Бавария Замок",
        photo = R.drawable.player5,
        date = "20.04.2023",
        score = 20.4
    ),
    TeamMember(
        name = "Александр Смирнов",
        teamLeft = "Бавария Швейцария",
        teamRight = "Канада ФК",
        photo = R.drawable.player6,
        date = "20.04.2023",
        score = 21.7
    ),
    TeamMember(
        name = "Артем Федоров",
        teamLeft = "Манчестер Новая Зеландия",
        teamRight = "Реал Йемен",
        photo = R.drawable.player7,
        date = "20.04.2023",
        score = 19.3
    ),
    TeamMember(
        name = "Глеб Беляев",
        teamLeft = "Крепость ФК",
        teamRight = "Барселона Юнайтед",
        photo = R.drawable.player8,
        date = "20.04.2023",
        score = 28.1
    ),
    TeamMember(
        name = "Никита Иванов",
        teamLeft = "Бавария Львы",
        teamRight = "Реал Корона",
        photo = R.drawable.player9,
        date = "20.04.2023",
        score = 24.6
    ),
    TeamMember(
        name = "Виктор Зайцев",
        teamLeft = "Клуб 100",
        teamRight = "Манчестер Элита",
        photo = R.drawable.player10,
        date = "20.04.2023",
        score = 15.7
    ),
    TeamMember(
        name = "Роман Лебедев",
        teamLeft = "Бавария Замок",
        teamRight = "Барселона Юнайтед",
        photo = R.drawable.player11,
        date = "20.04.2023",
        score = 31.2
    ),
    TeamMember(
        name = "Сергей Кузнецов",
        teamLeft = "Реал Футбол",
        teamRight = "Крепость ФК",
        photo = R.drawable.player12,
        date = "20.04.2023",
        score = 17.8
    ),
    TeamMember(
        name = "Антон Ковалев",
        teamLeft = "Манчестер Элита",
        teamRight = "Бавария Львы",
        photo = R.drawable.player13,
        date = "20.04.2023",
        score = 26.9
    ),
    TeamMember(
        name = "Павел Соколов",
        teamLeft = "Реал Корона",
        teamRight = "Барселона Юнайтед",
        photo = R.drawable.player14,
        date = "20.04.2023",
        score = 29.5
    ),
    TeamMember(
        name = "Владимир Тихонов",
        teamLeft = "Клуб 100",
        teamRight = "Бавария Замок",
        photo = R.drawable.player15,
        date = "20.04.2023",
        score = 22.3
    ),
)
