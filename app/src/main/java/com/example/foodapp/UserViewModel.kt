package com.example.foodapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {
    var userName = mutableStateOf("name")

    fun loginUser(email: String, password: String){
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userName.value = email
            }
    }

    fun logoutUser(){
        Firebase.auth.signOut()
        userName.value = ""
    }
}