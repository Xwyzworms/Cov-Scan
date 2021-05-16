package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.User

class Tutorial2Activity : AppCompatActivity() {
    private lateinit var btnTutorial2Selanjutnya : Button
    private  lateinit var btnTutorial2Kembali : Button
    private lateinit var  btnTutorial2btn1 : Button
    private lateinit var  btnTutorial2btn2 : Button
    private lateinit var  btnTutorial2btn3 : Button
    private lateinit var  user : User
    private fun initViews() {

        btnTutorial2Kembali = findViewById(R.id.btnTutorial2BtnKembali)
        btnTutorial2Selanjutnya = findViewById(R.id.btnTutorial2Selanjutnya)
        btnTutorial2btn1 = findViewById(R.id.tutorial2Btn1)
        btnTutorial2btn2 = findViewById(R.id.tutorial2Btn2)
        btnTutorial2btn3 = findViewById(R.id.tutorial2Btn3)
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
        setContentView(R.layout.activity_tutorial2)
        initViews()
        getUser()

        btnTutorial2Kembali.setOnClickListener {
            val intent = Intent(this,Tutorial1Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial2Selanjutnya.setOnClickListener {
            val intent = Intent(this,Tutorial3Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial2btn1.setOnClickListener {
            val intent = Intent(this,Tutorial1Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial2btn2.setOnClickListener {
            val intent = Intent(this,Tutorial2Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }
        btnTutorial2btn3.setOnClickListener {
            val intent = Intent(this,Tutorial3Activity::class.java)
            modifiedIntent(intent)
            startActivity(intent)
        }

    }
}