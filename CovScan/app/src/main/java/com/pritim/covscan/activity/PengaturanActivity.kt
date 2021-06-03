package com.pritim.covscan.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.NetworkModule
import com.pritim.covscan.activity.NetworkAPI.ResponseAction
import com.pritim.covscan.activity.NetworkAPI.ResponseGet
import com.pritim.covscan.activity.NetworkAPI.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class PengaturanActivity : AppCompatActivity() {

    private lateinit var tvPengaturanEmail : TextView
    private lateinit var tvStatusPengaturan : TextView
    private lateinit var cvPengaturanPassword : CardView
    private lateinit var cvPengaturanPetunjuk : CardView
    private lateinit var cvPengaturanLogOut : CardView
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var user : User

    private fun setUser() {
        user = intent.getSerializableExtra("EXTRA_USER") as User
    }
    private fun initView() {

        tvPengaturanEmail = findViewById(R.id.tvPengaturanEmail)
        tvStatusPengaturan = findViewById(R.id.tvStatusPengaturan)
        cvPengaturanLogOut = findViewById(R.id.cvPengaturanLogOut)
        cvPengaturanPassword = findViewById(R.id.cvPengaturanpassword)
        cvPengaturanPetunjuk = findViewById(R.id.cvPengaturanPetunjuk)
        bottomNavigation = findViewById(R.id.bottomNavigationViewPengaturan)

    }
    private fun  initInformation() {
        if ( user != null) {
            NetworkModule.service().getDataByEmail(user.email.toString()).enqueue(object : Callback<ResponseGet>    {
                override fun onResponse(call: Call<ResponseGet>, response: Response<ResponseGet>) {
                    if( response.body() != null ) {
                        tvPengaturanEmail.text = user.email
                        val confidence : Float  = response.body()!!.data?.get(0)?.confidence!!
                        if (confidence >= 0.1 && confidence <= 0.6)  {

                            tvStatusPengaturan.text = "Pemeriksaan Terakhir anda Positif covid 19"
                        }
                        else if (confidence > 0.6) {
                            tvStatusPengaturan.text = "Pemeriksaan Terakhir anda Sehat"
                        }
                        else {
                            tvStatusPengaturan.text="Anda belum melakukan pemeriksaan"
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseGet>, t: Throwable) {
                    Toast.makeText(applicationContext,"Can't Get information + ${t.message.toString()}",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
    private fun moveToUbahPassword() {

        val intent = Intent(applicationContext, UbahInformasiActivity::class.java)
        intent.putExtra("EXTRA_USER", user as Serializable)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()

    }
    private fun ubahPassword(pass : String,newPass : String) {
        if(user.password == pass) {
            user.password = newPass
            NetworkModule.service().updatePassword(newPass,user.id).enqueue(object : Callback<ResponseAction> {
                override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                    Toast.makeText(applicationContext, "SucessFully Update the Password",Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                    Toast.makeText(applicationContext,"Failed Updating Password + ${t.message}",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
    private fun moveToPetunjuk () {
        val intent = Intent(applicationContext,Tutorial1Activity::class.java)
        intent.putExtra("EXTRA_USER",user as Serializable)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)
        setUser()
        initView()
        initInformation()

        cvPengaturanPetunjuk.setOnClickListener {
            moveToPetunjuk()
        }

        cvPengaturanPassword.setOnClickListener {
            moveToUbahPassword()
        }

        cvPengaturanLogOut.setOnClickListener {
            var intent = Intent(applicationContext,LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
        bottomNavigation.selectedItemId = R.id.nav_settings
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            item.setIcon(R.drawable.ic_baseline_home_24)
            when(item.itemId){
                R.id.nav_beranda-> {
                    val intent = Intent(this,BerandaActivity::class.java)
                    intent.putExtra("EXTRA_USER",user as Serializable)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_covScan->{
                    val intent = Intent(this,KontenActivity::class.java)
                    intent.putExtra("EXTRA_USER",user as Serializable)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    item.setIcon(R.drawable.ic_baseline_camera_24)
                    finish()
                }
            }
            true
        }

    }


}