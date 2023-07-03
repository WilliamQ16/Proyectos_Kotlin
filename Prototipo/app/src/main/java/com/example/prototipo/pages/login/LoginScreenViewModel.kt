package com.example.prototipo.pages.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

//ViewModel para generar el funcionamiento del login.
class LoginScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)

    // Crear o loguear usuario mediante una credencial de Google.
    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("LoginFirebase", "¡Logueado con Google Exitoso!")
                        home()
                    }
                }
                .addOnFailureListener {
                    Log.d("LoginFirebase", "¡Fallo al loguear con Google!")
                }
        }catch (ex: Exception){
            Log.d("LoginFirebase", "Excepción al loguear con Google: " + ex.localizedMessage)
        }
    }

    // Loguear un usuario mediante el correo electronico y la contraseña.
    fun signInWithEmailAndPassword(email: String, password: String, home: ()->Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    /*
                    if(task.isSuccessful){
                        Log.d("LoginFirebase", "signInWithEmailAndPassword logueado!")
                        home()
                    } else{
                        Log.d("LoginFirebase", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                     */

                    if(task.isSuccessful){
                        Log.d("LoginFirebase", "signInWithEmailAndPassword logueado!")
                        home()
                    } else if(!task.isSuccessful){
                        task.exception
                    } else {
                        Log.d("LoginFirebase", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }
        }
        catch (ex:Exception){
            Log.d("LoginFirebase", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    // Crear un usuario mediante el correo electronico y la contraseña.
    fun createUserWithEmailAndPassword(
        email:String,
        password: String,
        home: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        home()
                    }
                    else{
                        Log.d("LoginFirebase", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }
}