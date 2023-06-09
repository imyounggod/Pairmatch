package com.example.pairmatch.ui.historyMatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.HistoryBet
import com.example.pairmatch.entites.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _bets = MutableLiveData<List<HistoryBet>>()
    val bets: LiveData<List<HistoryBet>> = _bets
    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var userID = FirebaseAuth.getInstance().currentUser?.uid

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> = _userData

    var userBalance = 0.0

    init {
        viewModelScope.launch {
            repository.getHistoryBets(FirebaseAuth.getInstance().uid!!).collectLatest {
                _bets.value = it
                println(it)
            }
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

}