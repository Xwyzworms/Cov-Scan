package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.User

class Tutorial3Activity : AppCompatActivity() {
    private lateinit var btnTutorial3Selesai : Button
    private  lateinit var btnTutorial3Kembali : Button
    private lateinit var  btnTutorial3btn1 : ImageView
    private lateinit var  btnTutorial3btn2 : ImageView
    private lateinit var  btnTutorial3btn3 : ImageView
    private lateinit var  user : User
    private fun initViews() {

        btnTutorial3Kembali = findViewById(R.id.btnTutorial3BtnKembali)
        btnTutorial3Selesai = findViewById(R.id.btnTutorial3BtnSelesai)
        btnTutorial3btn1 = findViewById(R.id.tutorial3Btn1)
        btnTutorial3btn2 = findViewById(R.id.tutorial3Btn2)
        btnTutorial3btn3 = findViewById(R.id.tutorial3Btn3)
    }
    private fun getUser() {
        user = intent.getSerializableExtra("EXTRA_USER") as User
    }
    private fun modifiedIntent(intent: Intent) {
        intent.putExtra("EXTRA_USER",user)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial3)
        initViews()
        getUser()
        btnTutorial3Kembali.setOnClickListener {
            val intent = Intent(this,Tutorial2Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
            finish()
        }
        btnTutorial3Selesai.setOnClickListener {
            val intent = Intent(this,BerandaActivity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
            finish()
        }
        btnTutorial3btn1.setOnClickListener {
            val intent = Intent(this,Tutorial1Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
            finish()
        }
        btnTutorial3btn2.setOnClickListener {
            val intent = Intent(this,Tutorial2Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
            finish()
        }
        btnTutorial3btn3.setOnClickListener {
            val intent = Intent(this,Tutorial3Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
            finish()
        }
    }
}