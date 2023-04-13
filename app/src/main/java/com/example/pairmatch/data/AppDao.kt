package com.example.pairmatch.data

import androidx.room.*
import com.example.pairmatch.entites.Bet
import com.example.pairmatch.entites.HistoryBet
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM team")
    suspend fun getTeams(): List<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Query("SELECT * FROM teamMember")
    suspend fun getPlayers(): MutableList<TeamMember>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(teamMember: TeamMember)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayerStart(teamMember: TeamMember)

    @Query("SELECT * FROM bet WHERE idUser = :idUser")
     suspend fun getBets(idUser: String): List<Bet>
    @Query("SELECT * FROM bet WHERE idUser = :idUser")
     fun getBetsF(idUser: String): Flow<List<Bet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBet(bet: Bet)

    @Query("SELECT * FROM historyBet WHERE idUser = :idUser")
    fun getHistoryBets(idUser:String): Flow<List<HistoryBet>>

    @Delete
    suspend fun deleteBet(bet: Bet)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryBet(bet: HistoryBet)
}
