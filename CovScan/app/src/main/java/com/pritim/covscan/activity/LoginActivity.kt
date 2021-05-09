package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.NetworkModule
import com.pritim.covscan.activity.NetworkAPI.ResponseGet
import com.pritim.covscan.activity.NetworkAPI.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class LoginActivity : AppCompatActivity() {
    lateinit var etLoginEmail : EditText
    lateinit var etLoginPassword : EditText
    lateinit var btnLoginMasuk : Button
    lateinit var tvLoginDaftar : TextView

    private fun initView() : Unit{
        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLoginMasuk = findViewById(R.id.btnLoginMasuk)
        tvLoginDaftar = findViewById(R.id.btnLoginDaftar)
    }

    private fun validateInfo() {

        var email = etLoginEmail.text.toString()
        var password = etLoginPassword.text.toString()
        var user = User()
        var network = NetworkModule.service().getDataByEmail(email)
        network.enqueue(object : Callback<ResponseGet> {
            override fun onResponse(call: Call<ResponseGet>, response: Response<ResponseGet>) {
                Toast.makeText(applicationContext, "Berhasil Mendapatkan Email",Toast.LENGTH_LONG).show()
                user = response.body()?.data?.get(0)!!

                if (password == user.password) {
                    var intent = Intent(applicationContext,BerandaActivity::class.java)
                    intent.putExtra("EXTRA_USER", user )
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(applicationContext, "Password atau email salah",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseGet>, t: Throwable) {
                Toast.makeText(applicationContext, "Cant Fetch Data " + t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()

        btnLoginMasuk.setOnClickListener {
            validateInfo()
        }

    }
}