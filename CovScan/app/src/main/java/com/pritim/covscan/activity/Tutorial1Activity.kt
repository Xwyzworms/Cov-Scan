package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.User

class Tutorial1Activity : AppCompatActivity() {
    private lateinit var btnTutorial1Selanjutnya : Button
    private  lateinit var btnTutorial1Lewati : Button
    private lateinit var  btnTutorial1btn1 : Button
    private lateinit var  btnTutorial1btn2 : Button
    private lateinit var  btnTutorial1btn3 : Button
    private lateinit var  user : User
    private fun initViews() {
        btnTutorial1Lewati = findViewById(R.id.btnTutorial1Lewati)
        btnTutorial1Selanjutnya = findViewById(R.id.btnTutorial1Selanjutnya)
        btnTutorial1btn1 = findViewById(R.id.tutorial1Btn1)
        btnTutorial1btn2 = findViewById(R.id.tutorial1Btn2)
        btnTutorial1btn3 = findViewById(R.id.tutorial1Btn3)
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
        setContentView(R.layout.activity_tutorial1)
        initViews()
        getUser()

        btnTutorial1Selanjutnya.setOnClickListener {
            val intent = Intent(this, Tutorial2Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }

        btnTutorial1Lewati.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial1btn1.setOnClickListener {
            val intent = Intent(this, Tutorial1Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial1btn2.setOnClickListener {
            val intent = Intent(this, Tutorial2Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial1btn3.setOnClickListener {
            val intent = Intent(this, Tutorial3Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
    }
}