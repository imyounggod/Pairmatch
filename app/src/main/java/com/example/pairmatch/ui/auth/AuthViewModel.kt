package com.example.pairmatch.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pairmatch.domain.Repository
import com.example.pairmatch.entites.TeamMember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseUser: FirebaseUser
    private var firebaseFirestore = FirebaseFirestore.getInstance()

    init {
//        viewModelScope.launch {
//            var result = firebaseFirestore.collection("players").get().await()
//            result.documents.forEach {
//                it.toObject(TeamMember::class.java)
//                    ?.let { it1 -> repository.insertPlayerStart(it1) }
//            }
//
//        }
    }

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        gender: String
    ): Boolean {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (authResult.user != null) {
                firebaseUser = authResult.user!!
                val currentUser = firebaseUser.uid
                val userProfile: MutableMap<String, Any> = HashMap()
                userProfile["user_uid"] = currentUser
                userProfile["user_name"] = name
                userProfile["user_email"] = email
                userProfile["user_gender"] = gender
                userProfile["user_balance"] = 100000
                firebaseFirestore.collection("users")
                    .document(currentUser)
                    .set(userProfile)
                    .await()
                return true
            } else {
                return false
            }
        } catch (e: java.lang.Exception) {
            return false
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        var isLogin = false
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        if (authResult.user != null) {
            isLogin = true
        }
        return isLogin
    }
}