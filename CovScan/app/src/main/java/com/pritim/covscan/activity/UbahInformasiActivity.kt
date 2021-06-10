package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.NetworkModule
import com.pritim.covscan.activity.NetworkAPI.ResponseAction
import com.pritim.covscan.activity.NetworkAPI.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UbahInformasiActivity : AppCompatActivity() {
    lateinit var btnUbahPassword : Button
    lateinit var passwordLama : EditText
    lateinit var passwordBaru : EditText
    lateinit var passwordBaruUlang : EditText
    lateinit var user : User
    fun initUser() {
            user = intent.getSerializableExtra("EXTRA_USER") as User
    }
    fun initViews() {
            btnUbahPassword = findViewById(R.id.btnUbahPassword)
            passwordBaru = findViewById(R.id.passwordbarutext)
            passwordBaruUlang = findViewById(R.id.passwordbarutext)
            passwordLama = findViewById(R.id.passwordlamatext)
    }

    fun updatePasswordNow() {
        val newPassword = passwordBaru.text.toString()
        val repeatedPassword = passwordBaruUlang.text.toString()

        if (user.password == passwordLama.text.toString()) {
            if (newPassword == repeatedPassword) {
                NetworkModule.service().updatePassword(newPassword, user.id.toString()).enqueue(
                        object : Callback<ResponseAction> {
                            override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                                Toast.makeText(applicationContext,"Successfully Update Password",Toast.LENGTH_LONG).show()
                            }

                            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                                Toast.makeText(applicationContext,"Failed to Update Password : ${t.message}",Toast.LENGTH_LONG).show()
                            }
                        }
                )
            }
            else if (newPassword != repeatedPassword) {
                Toast.makeText(applicationContext,"Ulangi Password Baru",Toast.LENGTH_LONG).show()
            }
        }
        else if (passwordLama.text.toString() != user.password) {
            Toast.makeText(applicationContext, "Password yang anda masukan tidak sesuai",Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubahpassword)
        initViews()
        initUser()
        btnUbahPassword.setOnClickListener {
            updatePasswordNow()
            val intent = Intent(applicationContext,BerandaActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("EXTRA_USER",user)
            startActivity(intent)
            finish()
        }
    }
}