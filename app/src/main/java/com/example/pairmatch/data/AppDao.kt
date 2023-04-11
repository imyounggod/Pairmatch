package com.example.pairmatch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pairmatch.entites.Bet
import com.example.pairmatch.entites.Team
import com.example.pairmatch.entites.TeamMember
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM team")
    fun getTeams(): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Query("SELECT * FROM teamMember")
    fun getPlayers(): Flow<List<TeamMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(teamMember: TeamMember)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayerStart(teamMember: TeamMember)

    @Query("SELECT * FROM bet")
    fun getBets(): Flow<List<Bet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBet(bet: Bet)
}
