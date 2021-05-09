package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.NetworkModule
import com.pritim.covscan.activity.NetworkAPI.ResponseAction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarActivity : AppCompatActivity() {


    lateinit var  etDaftarNama : EditText
    lateinit var  etDaftarPassword : EditText
    lateinit var  etDaftarEmail : EditText
    lateinit var  etDaftarTgl : EditText
    lateinit var  btnDaftar : Button
    lateinit var btnDaftarMasuk :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        etDaftarNama = findViewById(R.id.etDaftarNama)
        etDaftarEmail = findViewById(R.id.etDaftarEmail)
        etDaftarPassword = findViewById(R.id.etDaftarPassword)
        etDaftarTgl = findViewById(R.id.etDaftarEmail)
        btnDaftar = findViewById(R.id.btnDaftar)
        btnDaftarMasuk = findViewById(R.id.btnDaftarMasuk)

        btnDaftar.setOnClickListener {
             insertData()
        }

        btnDaftarMasuk.setOnClickListener {
            var intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    fun insertData() {

        var nama = etDaftarNama.text.toString()
        var email = etDaftarEmail.text.toString()
        var pass = etDaftarPassword.text.toString()
        var tgl = etDaftarTgl.text.toString()

        if (pass.isNotEmpty()) {
            var networkMod : Call<ResponseAction> = NetworkModule.service().insertData(nama,email,pass,tgl)

            networkMod.enqueue(object : Callback<ResponseAction> {
                override fun onResponse(
                    call: Call<ResponseAction>,
                    response: Response<ResponseAction>) {
                    Toast.makeText(applicationContext,"Insert Data SuccesFully",Toast.LENGTH_LONG).show()
                     val intent = Intent(applicationContext,LoginActivity::class.java)
                     startActivity(intent)
                     finish()
                }

                override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                }
            })
        }
        else {
            Toast.makeText(applicationContext,"Password tidak boleh kosong",Toast.LENGTH_LONG).show()
        }

    }
}