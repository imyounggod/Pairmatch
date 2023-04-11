package com.example.pairmatch.domain

import com.example.pairmatch.entites.Bet
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getTeams(): Flow<List<Team>>
    fun getBets(): Flow<List<Bet>>
    fun getPlayers(): Flow<List<TeamMember>>
    suspend fun insertTeam(team: Team)
    suspend fun insertPlayer(teamMember: TeamMember)
    suspend fun insertPlayerStart(teamMember: TeamMember)
    suspend fun insertBet(bet: Bet)
}