package com.example.pairmatch.data

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pairmatch.entites.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Executors

@Database(
    entities = [TeamMember::class, Team::class, Bet::class, HistoryBet::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao


    companion object {
        const val DATABASE_NAME = "app_db"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun buildDatabase(context: Context): AppDatabase {
            val firebaseFirestore = FirebaseFirestore.getInstance()
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        //pre-populate data
                        GlobalScope.launch {
                            instance?.let { dbInstance ->
                                arrTeam.forEach {
                                    dbInstance.appDao.insertPlayerStart(it)
                                }
                            }
                        }

                    }
                })
                .build()
        }
    }
}