package com.example.pairmatch.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: Repository) : ViewModel() {
    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var userID = FirebaseAuth.getInstance().currentUser?.uid

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> = _userData

//    private val _matches: MutableLiveData<MutableList<Matches?>?> =
//        MutableLiveData<MutableList<Matches?>?>(mutableListOf())
//    val matches: MutableLiveData<MutableList<Matches?>?> = _matches
//
//    private val _bets: MutableLiveData<MutableList<Bet?>?> =
//        MutableLiveData<MutableList<Bet?>?>(mutableListOf())
//    val bets: MutableLiveData<MutableList<Bet?>?> = _bets

    init {
        viewModelScope.launch {
            repository.getPlayers().collect {
                println(it)
            }

        }
    }

    var userBalance = 0.0

    fun getUserData() {
        firebaseFirestore.collection("users").document(userID.toString()).get()
            .addOnCompleteListener { task ->
                if (task.isComplete) {
                    val document: DocumentSnapshot = task.result
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        userBalance = user?.user_balance!!
                        _userData.value = user
                    }
                }
            }.addOnFailureListener {
                println(it)
            }
    }

    fun getMatchesData() {
//        firebaseFirestore.collection("matches").get().addOnCompleteListener { task ->
//            if (task.isComplete) {
//                val document: QuerySnapshot? = task.result
//                val matches = document?.toObjects(Matches::class.java)
//                _matches.value = matches
//            }
//            checkMatches()
//        }.addOnFailureListener {
//            println(it)
//        }
    }

    private fun checkMatches() {
//        _matches.value?.forEach { match ->
//            if (match != null) {
//                match.match_start_date?.let { date ->
//                    if (date.toLong() <= System.currentTimeMillis()) {
//                        val randDays = 86400000 * randInt(7, 14)
//                        match.id_match?.let {
//                            firebaseFirestore.collection("matches").document(it).update(
//                                "match_start_date",
//                                (match.match_start_date!!.toLong() + randDays).toString()
//                            )
//                        }
//                        match.match_start_date =
//                            (match.match_start_date!!.toLong() + randDays).toString()
//                    }
//                }
//            }
//        }
//        _matches.value = _matches.value

    }

    fun getBetData() {
//        firebaseFirestore.collection("users").document(userID.toString()).collection("bets")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isComplete) {
//                    val document: QuerySnapshot? = task.result
//                    val bets = document?.toObjects(Bet::class.java)
//                    _bets.value = bets
//                }
//                checkBet()
//            }.addOnFailureListener {
//                println(it)
//            }

    }

    private fun checkBet() {
//        val listBet = _bets.value
//        listBet?.forEach { bet ->
//            bet?.date_event?.let { date ->
//                if (date.toLong() <= System.currentTimeMillis()) {
//                    calculate(bet)
//                    firebaseFirestore.collection("users").document(userID.toString())
//                        .collection("bets").document(bet.id_match!!).delete()
//                    getBetData()
//                }
//            }
//        }
//        getMatchesData()
    }


//    private fun calculate(bet: Bet) {
////        viewModelScope.launch(Dispatchers.IO) {
////            val rand = randInt(1, 101)
////            if (rand >= 50) {
////                println("HIGHT")
////                firebaseFirestore.collection("matches").document(bet.id_match!!).update(
////                    "player_points", FieldValue.increment(
////                        randInt(0, 5).toDouble() / 10.0
////                    )
////                )
////
////                if (bet.coef_win!!) {
////                    firebaseFirestore.collection("users").document(userID.toString()).update(
////                        "user_balance",
////                        userBalance + bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                    ).addOnCompleteListener {
////                        if (it.isSuccessful) {
////                            _userData.value?.user_balance =
////                                userBalance + bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                            _userData.value = _userData.value
////                            userBalance += bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                        }
////                    }
////                }
////            } else {
////                println("LOW")
////                firebaseFirestore.collection("matches").document(bet.id_match!!).update(
////                    "player_points", FieldValue.increment(
////                        randInt(-5, 0).toDouble() / 10.0
////                    )
////                )
////                if (bet.coef_los!!) {
////                    firebaseFirestore.collection("users").document(userID.toString()).update(
////                        "user_balance",
////                        userBalance + bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                    ).addOnCompleteListener {
////                        if (it.isSuccessful) {
////                            _userData.value?.user_balance =
////                                userBalance + bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                            _userData.value = _userData.value
////                            userBalance += bet.bet_value!!.toDouble() * bet.coef!!.toDouble()
////                        }
////                    }
////
////                }
////            }
////        }
//    }

    @SuppressLint("SuspiciousIndentation")
    fun sendBet(
        idMath: String,
        betValue: Double,
        coef_win: Boolean,
        coef_los: Boolean,
        coef: String,
        dateEvent: String
    ) {
        var stateBet: Boolean

        val bet: MutableMap<String, Any> = HashMap()
        bet["id_user"] = userID.toString()
        bet["bet_value"] = betValue
        bet["coef_los"] = coef_los
        bet["coef_win"] = coef_win
        bet["coef"] = coef
        bet["date_event"] = dateEvent
        bet["bet_is_ready"] = true
        bet["id_match"] = idMath

        val docRef = firebaseFirestore
            .collection("users")
            .document(userID.toString()).collection("bets")

        docRef.document(idMath).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {
                    stateBet = document.getBoolean("bet_is_ready")!!
                    if (!stateBet) {
                        docRef.document(idMath).set(bet)
                        updateBalanceUser(betValue)
                    }
                } else {
                    docRef.document(idMath).set(bet)
                    updateBalanceUser(betValue)
                    Log.d("LOGGER", "No such document")
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.exception)
            }
        }

    }

    private fun updateBalanceUser(betValue: Double) {
        if (userBalance != 0.0) {
            userBalance -= betValue
            firebaseFirestore.collection("users")
                .document(userID.toString())
                .update("user_balance", userBalance)

        }
    }
}