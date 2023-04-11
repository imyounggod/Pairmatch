package com.example.pairmatch.ui.leaderBoard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pairmatch.entites.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class LeaderBoardViewModel: ViewModel() {
    private var firebaseFirestore = FirebaseFirestore.getInstance().collection("users")
    private var UID = FirebaseAuth.getInstance().currentUser?.uid

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> = _userData

    private val _users = MutableLiveData<MutableList<User?>?>()
    val users: MutableLiveData<MutableList<User?>?> = _users

    fun getUserData() {
        firebaseFirestore.document(UID.toString()).get().addOnCompleteListener { task ->
            if(task.isComplete){
                val document: DocumentSnapshot = task.result
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    _userData.value = user
                }
            }
        }.addOnFailureListener {
            println(it)
        }
    }

    fun getUsers() {
        firebaseFirestore.get().addOnCompleteListener { task ->
            if (task.isComplete) {
                val document: QuerySnapshot? = task.result
                val users = document?.toObjects(User::class.java)
                users?.sortByDescending {
                    it.user_balance
                }
                _users.value = users
            }
        }.addOnFailureListener {
            println(it)
        }
    }
}