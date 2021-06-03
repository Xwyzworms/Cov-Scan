package com.pritim.covscan.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.NetworkModule
import com.pritim.covscan.activity.NetworkAPI.ResponseAction
import com.pritim.covscan.activity.NetworkAPI.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.math.RoundingMode

class KontenActivity : AppCompatActivity() {
    private val mInputSize = 150
    private val mModelPath = "ctScan.tflite"
    private lateinit var classifier : Classifier
    private  val mLabelPath = "labelct.txt"
    private var user = User()
    lateinit var bottomNavigation : BottomNavigationView
    private val REQUEST_PICK_IMAGE = 2

    lateinit var  ivImagetapHere : ImageView
    lateinit var  btnKontenPeriksa : Button
    lateinit var  tvKontentAkurasi : TextView
    lateinit var  tvKontenStatusPemeriksaan : TextView
    private fun getUserFromIntent() {
        user =intent.getSerializableExtra("EXTRA_USER") as User
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also{ intent->
            intent.type ="image/*"

            intent.resolveActivity(packageManager).also {

                startActivityForResult(intent,REQUEST_PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_PICK_IMAGE) {
                Log.d("Sampis","Masuk Kesini")
                val selectedBitmap : Uri? = data?.data
                val Image : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedBitmap)
                ivImagetapHere.setImageBitmap(Image)
            }
        }
        else {

            Log.d("debug","UNDERTALe")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_utama)
        initClassifier()
        initViews()
        getUserFromIntent()
        user = intent.getSerializableExtra("EXTRA_USER") as User

        ivImagetapHere.setOnClickListener {
            openGallery()
        }
        btnKontenPeriksa.setOnClickListener {
            val image = (ivImagetapHere.drawable as BitmapDrawable).bitmap
            val res = classifier.recognizeImage(image)
            if(res.keys.contains( "notcovid")) {
                tvKontenStatusPemeriksaan.text = "Selamat Anda Sehat"
                tvKontenStatusPemeriksaan.setTextColor(ContextCompat.getColor(applicationContext,R.color.green))
                tvKontentAkurasi.text = "Akurasi Pemeriksaan : " + (res["notcovid"].toString().toBigDecimal().setScale(5,RoundingMode.UP).toDouble() * 100 ).toString()
                NetworkModule.service().updateConfidence(user.id,(res["notcovid"].toString().toBigDecimal().setScale(3,RoundingMode.UP).toFloat() * 100)).enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                            Toast.makeText(applicationContext, "Successfully update the confidence",Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failed Update the confidence",Toast.LENGTH_LONG).show()
                    }
                })

            }
            else if (res.keys.contains("covid")) {
                tvKontenStatusPemeriksaan.text = "Maaf Anda Positif Covid 19"
                tvKontenStatusPemeriksaan.setTextColor( ContextCompat.getColor(applicationContext,R.color.darkred))
                tvKontentAkurasi.text = "Akurasi Pemeriksaan : " + (( 100 - res["covid"].toString().toBigDecimal().setScale(3,RoundingMode.UP).toDouble()) * 100 ).toString()
                NetworkModule.service().updateConfidence(user.id,(res["covid"].toString().toBigDecimal().setScale(3,RoundingMode.UP).toFloat() * 100)).enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                        Toast.makeText(applicationContext,"SuccessFully Update the confiddence",Toast.LENGTH_LONG).show()

                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                        Toast.makeText(applicationContext,"Failed Update the confidence",Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        bottomNavigation.selectedItemId = R.id.nav_covScan
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.nav_settings ->{
                    val nav_settings = Intent(this,PengaturanActivity::class.java)
                    nav_settings.putExtra("EXTRA_USER",user as Serializable)
                    nav_settings.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(nav_settings)
                    finish()
                }
                R.id.nav_beranda->{
                    val intent = Intent(this,BerandaActivity::class.java)
                    intent.putExtra("EXTRA_USER",user as Serializable)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    private fun initClassifier(){
        classifier = Classifier(assets,mModelPath,mLabelPath,mInputSize)
    }

    private  fun initViews() {
        ivImagetapHere = findViewById(R.id.ImgTapDisini)
        btnKontenPeriksa = findViewById(R.id.btnKontenPeriksa)
        tvKontenStatusPemeriksaan = findViewById(R.id.tvKontenStatusPemeriksaan)
        tvKontentAkurasi = findViewById(R.id.tvKontenAkurasiPemeriksaan)
        bottomNavigation = findViewById(R.id.bottomNavigationView)

    }
}