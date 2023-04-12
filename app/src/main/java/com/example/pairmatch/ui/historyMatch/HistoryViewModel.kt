package com.example.pairmatch.ui.historyMatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var userID = FirebaseAuth.getInstance().currentUser?.uid

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> = _userData

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

}