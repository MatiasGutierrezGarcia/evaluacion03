package com.example.evaluacin03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.evaluacin03.Models.Temperatura
import com.example.evaluacin03.databinding.ActivityPostLoginBinding
import com.example.evaluacin03.vistas.HomeFragment
import com.example.evaluacin03.vistas.SettingFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class PostLoginActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding : ActivityPostLoginBinding

    //Firebase auth
    private lateinit var auth : FirebaseAuth

    //Firebase database
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //Inicio BD y ruta de almacenamiento
        database = FirebaseDatabase.getInstance().getReference("Temperatura")

        //Obtener datos e insertar
        binding.btnSave.setOnClickListener {
            val temperatura = binding.etTemperatura.text.toString()
            val fecha = binding.etFecha.text.toString()

            //Generar id
            val id = database.child("Temperatura").push().key

            if(temperatura.isEmpty()){
                binding.etTemperatura.error = "Ingresar temperatura"
                return@setOnClickListener
            }
            if(fecha.isEmpty()){
                binding.etFecha.error = "Ingresar Fecha"
                return@setOnClickListener
            }

            val tempt = Temperatura(id,temperatura,fecha)

            database.child(id!!).setValue(tempt)
                .addOnSuccessListener {
                    Toast.makeText(this,"Temperatura ingresada.",Toast.LENGTH_SHORT).show()
                    binding.etTemperatura.setText("")
                    binding.etFecha.setText("")
                }.addOnFailureListener {
                    Toast.makeText(this,"No se ha ingresado la temperatura.",Toast.LENGTH_SHORT).show()
                }

        }

        binding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.nav_inicio -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment()).commit()
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,SettingFragment()).commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.nav_inicio -> Toast.makeText(this,"Ya estas en Inicio.",Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> Toast.makeText(this,"Ya estas en Settings.",Toast.LENGTH_SHORT).show()
                else -> false
            }
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}