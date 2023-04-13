package com.example.pairmatch.data

import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.Bet
import com.example.pairmatch.entites.HistoryBet
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import kotlinx.coroutines.flow.Flow


class RepositoryImpl(
    private val dao: AppDao
) : Repository {
    override suspend fun getTeams(): List<Team> {
        return dao.getTeams()
    }

    override suspend  fun getBets(userId: String): List<Bet>{
       return dao.getBets(userId)
    }

    override fun getBetsF(userId: String): Flow<List<Bet>> {
        return dao.getBetsF(userId)
    }
    override fun getHistoryBets(userId: String): Flow<List<HistoryBet>> {
        return  dao.getHistoryBets(userId)
    }

    override suspend fun getPlayers(): MutableList<TeamMember> {
        return dao.getPlayers()
    }

    override suspend fun insertTeam(team: Team) {
        dao.insertTeam(team)
    }

    override suspend fun insertPlayer(teamMember: TeamMember) {
        dao.insertPlayer(teamMember)
    }

    override suspend fun insertPlayerStart(teamMember: TeamMember) {
        dao.insertPlayerStart(teamMember)
    }

    override  suspend fun insertHistoryBet(bet: HistoryBet) {
        dao.insertHistoryBet(bet)
    }

    override suspend fun insertBet(bet: Bet) {
        dao.insertBet(bet)
    }

    override suspend fun delete(bet: Bet) {
        dao.deleteBet(bet)
    }

}