package com.example.evaluacin03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.evaluacin03.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding : ActivityMainBinding

    //Firebase auth
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            // recuperar el correo y la password
            val correo = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(correo.isEmpty()){
                binding.etEmail.error ="Ingresa un Correo."
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.etPassword.error="Ingresa una contraseña."
                return@setOnClickListener
            }

            signIn(correo, password)
        }
    }

    private fun signIn(correo: String, password: String) {
        auth.signInWithEmailAndPassword(correo,password).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this,"Inicio de sesion valido", Toast.LENGTH_LONG).show()
                // Si el inicio de sesion es valido, manda al post login
                val intent = Intent (this,PostLoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Inicio de sesion invalido", Toast.LENGTH_LONG).show()
            }
        }
    }
}