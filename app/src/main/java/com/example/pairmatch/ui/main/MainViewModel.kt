package com.example.pairmatch.ui.main

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var userID = FirebaseAuth.getInstance().currentUser?.uid

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> = _userData

    private val _event = MutableLiveData<ValidateEvent>()
    val event: LiveData<ValidateEvent> = _event

    private val _selectedTeam = MutableLiveData<Team>(Team())
    val selectedTeam: LiveData<Team> = _selectedTeam

    private val _selectedPlayer = MutableLiveData<TeamMember>()
    val selectedPlayer: LiveData<TeamMember> = _selectedPlayer

    private val _players = MutableLiveData<List<TeamMember>>()
    val players: LiveData<List<TeamMember>> = _players

    private val _bets = MutableLiveData<List<Bet>>()
    val bets: LiveData<List<Bet>> = _bets


    init {
        viewModelScope.launch {
            repository.getPlayers().collectLatest {
                _players.postValue(it)
                println("PLAYErS" + it)
                checkBets()
            }
            _players.value!!.forEach {
                val dat = it.date
                println(dat)
                val date =
                    dat?.let { it1 ->
                        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(
                            it1
                        )
                    }
                if (date != null) {
                    if (date <= Calendar.getInstance().time) {
                        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        repository.insertPlayer(
                            it.copy(
                                date = format.format(
                                    format.parse(it.date).time + 86400000 * randInt(
                                        3,
                                        14
                                    )
                                ),
                                score = it.score + (kotlin.random.Random.nextDouble(
                                    -2.0,
                                    2.0
                                ) * 100).toInt() / 100.0
                            )
                        )
                    }

                }
            }
            checkBets()
        }

    }

    private fun checkBets() {
        viewModelScope.launch {
            repository.getBets(FirebaseAuth.getInstance().uid!!).collectLatest {
                println("BETS" + it)
                it.forEach { bet ->
                    val dat = bet.team!!.scheduleMatch?.substring(13)
                    println(dat)
                    val date =
                        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(
                            dat.toString()
                        )
                    if (date != null) {
                        if (date <= Calendar.getInstance().time) {
                            val score =
                                _players.value!![bet.team!!.member1!!.id!!-1].score + _players.value!![bet.team!!.member2!!.id!!-1].score + _players.value!![bet.team!!.member3!!.id!!-1].score + _players.value!![bet.team!!.member4!!.id!!-1].score + _players.value!![bet.team!!.member5!!.id!!-1].score
                            val newBet = HistoryBet(idUser = FirebaseAuth.getInstance().uid,
                                bet_value = bet.bet_value,
                                coef_win = bet.coef_win,
                                coef_los = bet.coef_los,
                                coef = bet.coef,
                                bet_is_ready = bet.bet_is_ready,
                                team = bet.team!!.copy(
                                    endPoints = score
                                ),
                            )
                            repository.insertHistoryBet(
                                newBet
                            )
                            if(newBet.team?.teamPoints!! > newBet.team?.endPoints!!){
                                if(newBet.coef_los == true){
                                    firebaseFirestore.collection("users").document(userID.toString())
                                        .update("user_balance", userBalance + newBet.bet_value!!.toDouble() * newBet.coef!!.toDouble())
                                }
                            }else{
                                if (newBet.coef_win == true){
                                    firebaseFirestore.collection("users").document(userID.toString())
                                        .update("user_balance", userBalance + newBet.bet_value!!.toDouble() * newBet.coef!!.toDouble())
                                }
                            }
                            repository.delete(bet)
                        }
                    }
                }
                _bets.value = it
            }
        }
    }

    var userBalance = 0.0
    fun randInt(min: Int, max: Int): Int = Random().nextInt(max - min + 1) + min


    fun setDate(dateEvent: String) {
        _selectedTeam.value = _selectedTeam.value?.copy(scheduleMatch = dateEvent)
    }

    fun selectPlayer(teamMember: TeamMember) {
        _selectedPlayer.value = teamMember
    }

    fun setBet(value: String, winOrLose: String) {
        if (value.toDouble() > userBalance || value.toDouble() < 0){
            _event.value = ValidateEvent.Error("Некорректная сумма")
            return
        }
        viewModelScope.launch {
            updateBalanceUser(value.toDouble())
            if (winOrLose == "high") {
                repository.insertBet(
                    Bet(idUser = FirebaseAuth.getInstance().uid,
                        coef_win = true,
                        bet_value = value.toInt(),
                        team = _selectedTeam.value,
                        coef = _selectedTeam.value?.coefHigh.toString()
                    )
                )
            } else {
                repository.insertBet(
                    Bet(idUser = FirebaseAuth.getInstance().uid,
                        coef_los = true,
                        bet_value = value.toInt(),
                        team = _selectedTeam.value,
                        coef = _selectedTeam.value?.coefHigh.toString()
                    )
                )
            }
        }
        _event.value = ValidateEvent.Success
    }

    fun isClearForm(){
        _selectedTeam.value = Team()
    }

    fun setMember1(member: TeamMember) {
        if (_selectedTeam.value?.member1 == member || _selectedTeam.value?.member2 == member ||
            _selectedTeam.value?.member3 == member || _selectedTeam.value?.member4 == member ||
            _selectedTeam.value?.member5 == member
        ) {
            _event.value = ValidateEvent.Error("Вы уже выбрали этого игрока")
        } else {
            _selectedTeam.value = _selectedTeam.value?.copy(
                member1 = member, teamPoints =
                ((member.score +
                        ((_selectedTeam.value?.member2?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member3?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member4?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member5?.score) ?: 0.0)
                        ) * 100).toInt() / 100.0
            )
            _event.value = ValidateEvent.Success
        }

    }

    fun setMember2(member: TeamMember) {
        if (_selectedTeam.value?.member1 == member || _selectedTeam.value?.member2 == member ||
            _selectedTeam.value?.member3 == member || _selectedTeam.value?.member4 == member ||
            _selectedTeam.value?.member5 == member
        ) {
            _event.value = ValidateEvent.Error("Вы уже выбрали этого игрока")
        } else {
            _selectedTeam.value = _selectedTeam.value?.copy(
                member2 = member, teamPoints =
                ((member.score +
                        ((_selectedTeam.value?.member1?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member3?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member4?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member5?.score) ?: 0.0)
                        ) * 100).toInt() / 100.0
            )
            _event.value = ValidateEvent.Success
        }
    }

    fun setMember3(member: TeamMember) {
        if (_selectedTeam.value?.member1 == member || _selectedTeam.value?.member2 == member ||
            _selectedTeam.value?.member3 == member || _selectedTeam.value?.member4 == member ||
            _selectedTeam.value?.member5 == member
        ) {
            _event.value = ValidateEvent.Error("Вы уже выбрали этого игрока")
        } else {
            _selectedTeam.value = _selectedTeam.value?.copy(
                member3 = member, teamPoints =
                ((member.score +
                        ((_selectedTeam.value?.member2?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member5?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member4?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member1?.score) ?: 0.0)
                        ) * 100).toInt() / 100.0
            )
            _event.value = ValidateEvent.Success
        }
    }

    fun setMember4(member: TeamMember) {
        if (_selectedTeam.value?.member1 == member || _selectedTeam.value?.member2 == member ||
            _selectedTeam.value?.member3 == member || _selectedTeam.value?.member4 == member ||
            _selectedTeam.value?.member5 == member
        ) {
            _event.value = ValidateEvent.Error("Вы уже выбрали этого игрока")
        } else {
            _selectedTeam.value = _selectedTeam.value?.copy(
                member4 = member, teamPoints =
                ((member.score +
                        ((_selectedTeam.value?.member2?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member3?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member5?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member1?.score) ?: 0.0)
                        ) * 100).toInt() / 100.0
            )
            _event.value = ValidateEvent.Success
        }
    }

    fun setMember5(member: TeamMember) {
        if (_selectedTeam.value?.member1 == member || _selectedTeam.value?.member2 == member ||
            _selectedTeam.value?.member3 == member || _selectedTeam.value?.member4 == member ||
            _selectedTeam.value?.member5 == member
        ) {
            _event.value = ValidateEvent.Error("Вы уже выбрали этого игрока")
        } else {
            _selectedTeam.value = _selectedTeam.value?.copy(
                member5 = member, teamPoints =
                ((member.score +
                        ((_selectedTeam.value?.member2?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member3?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member4?.score) ?: 0.0) +
                        ((_selectedTeam.value?.member1?.score) ?: 0.0)
                        ) * 100).toInt() / 100.0
            )
            _event.value = ValidateEvent.Success
        }
    }


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


    private fun updateBalanceUser(betValue: Double) {
        if (userBalance != 0.0) {
            userBalance -= betValue
            firebaseFirestore.collection("users").document(userID.toString())
                .update("user_balance", userBalance)

        }
    }

    sealed class ValidateEvent() {
        object Success : ValidateEvent()
        class Error(val text: String) : ValidateEvent()
    }
}