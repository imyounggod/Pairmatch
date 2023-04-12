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
    override fun getTeams(): Flow<List<Team>> {
        return dao.getTeams()
    }

    override fun getBets(): Flow<List<Bet>> {
       return dao.getBets()
    }

    override fun getHistoryBets(): Flow<List<HistoryBet>> {
        return  dao.getHistoryBets()
    }

    override fun getPlayers(): Flow<List<TeamMember>> {
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

    override suspend fun insertHistoryBet(bet: HistoryBet) {
        dao.insertHistoryBet(bet)
    }

    override suspend fun insertBet(bet: Bet) {
        dao.insertBet(bet)
    }

    override suspend fun delete(bet: Bet) {
        dao.deleteBet(bet)
    }

}