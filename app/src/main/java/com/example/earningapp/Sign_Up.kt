package com.example.earningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.earningapp.databinding.ActivitySignUpBinding
import com.example.earningapp.modelclass.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class Sign_Up : AppCompatActivity() {
    private  val binding by lazy {
            ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signup.setOnClickListener{
            if(binding.name.text.toString().equals("")||binding.age.text.toString().equals("")
                || binding.email.text.toString().equals("")||binding.password.text.toString().equals("")){
                Toast.makeText(this, "please enter your details first", Toast.LENGTH_SHORT).show()
            }else{
                Firebase.auth.createUserWithEmailAndPassword(binding.email.text.toString(),
                    binding.password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                            var user =  User(binding.name.text.toString(),
                                binding.age.text.toString().toInt(),
                                binding.email.text.toString(),
                                binding.password.text.toString())
                        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                            intent = Intent(this, Homeactivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }else{
                        Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
            super.onStart()
            if(Firebase.auth.currentUser!=null){
                intent = Intent(this, Homeactivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}