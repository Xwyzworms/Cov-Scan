package com.pritim.covscan.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pritim.covscan.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            var intent = Intent(applicationContext,DaftarActivity::class.java)
            startActivity(intent)
            finish()
        },3000)


    }
}