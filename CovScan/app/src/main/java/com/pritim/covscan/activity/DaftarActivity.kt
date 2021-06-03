package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.pritim.covscan.activity.NetworkAPI.ResponseGet
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


    private fun validateEmail (email : String) : Boolean{
        return  email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validateDate(date : String) : Boolean {
        val regex =  Regex("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))\$")
        return regex.matches(date)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        etDaftarNama = findViewById(R.id.etDaftarNama)
        etDaftarEmail = findViewById(R.id.etDaftarEmail)
        etDaftarPassword = findViewById(R.id.etDaftarPassword)
        etDaftarTgl = findViewById(R.id.etDaftarLahir)
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
            NetworkModule.service().getDataByEmail(email).enqueue(object : Callback<ResponseGet> {
                override fun onResponse(call: Call<ResponseGet>, response: Response<ResponseGet>) {
                    if(response.body()?.data?.size != 0) {
                        Toast.makeText(
                            applicationContext,
                            "Email Telah terdaftar ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else {


                        if (validateDate(tgl) && validateEmail(email)) {
                            var networkMod: Call<ResponseAction> =
                                NetworkModule.service().insertData(nama, email, pass, tgl)

                            networkMod.enqueue(object : Callback<ResponseAction> {
                                override fun onResponse(
                                    call: Call<ResponseAction>,
                                    response: Response<ResponseAction>
                                ) {
                                    Toast.makeText(
                                        applicationContext,
                                        "SuccesFully Registered",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent =
                                        Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                                        .show()
                                }
                            })

                        }
                        else if (!validateEmail(email)) {
                            Toast.makeText(applicationContext,"Email Tidak benar",Toast.LENGTH_SHORT).show()
                        }
                        else if(!validateDate(tgl)) {
                            Toast.makeText(applicationContext,"Tanggal kelahiran YYYY-mm-dd",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseGet>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                }
            })

        }
        else {
            Toast.makeText(applicationContext,"Password tidak boleh kosong",Toast.LENGTH_LONG).show()
        }

    }
}