package com.example.room1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentA = LoginFragment()
        val fragmentB = RegisterFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentB).commit()
    }
}