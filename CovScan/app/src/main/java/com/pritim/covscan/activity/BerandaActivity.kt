package com.pritim.covscan.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pritim.covscan.R
import com.pritim.covscan.activity.NetworkAPI.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*



class BerandaActivity : AppCompatActivity() {
    lateinit var  spn_Beranda_Loc : Spinner
    lateinit var bottomNavigation : BottomNavigationView
    lateinit var  btn_plih : Button
    lateinit var  jumlahKasus_Total : TextView
    lateinit var  jumlahSembuh_Total : TextView
    lateinit var  jumlahMeninggal_Total : TextView
    private var ICall : Int = 0
    private lateinit var user : User
    private fun getUserFromIntent() {
        user =intent.getSerializableExtra("EXTRA_USER") as User
    }
    private fun initView() {
        spn_Beranda_Loc = findViewById(R.id.spn_Beranda_Loc)
        btn_plih  = findViewById(R.id.btn_pilih)
        jumlahKasus_Total = findViewById(R.id.jumlahkasus_total)
        jumlahSembuh_Total = findViewById(R.id.sembuh_total)
        jumlahMeninggal_Total  = findViewById(R.id.meninggal_total)
        bottomNavigation = findViewById(R.id.BerandabottomNavigationView)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        val webkemenkes : CardView = findViewById(R.id.web_kemenkes)
        val websatgascovid : CardView = findViewById(R.id.crd_kasus)
        initView()
        getUserFromIntent()
            spn_Beranda_Loc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("PoS", position.toString())
                    if( position == 0) {
                        NetworkServiceKorona.service().getMeninggalGlobal().enqueue(object :
                                Callback<ResponseGetKawalKorona> {
                            override fun onResponse(
                                    call: Call<ResponseGetKawalKorona>,
                                    response: Response<ResponseGetKawalKorona>
                            ) {
                                Log.d("Sampis",response.body().toString())
                                jumlahMeninggal_Total.text = response.body()?.total!!
                            }

                            override fun onFailure(call: Call<ResponseGetKawalKorona>, t: Throwable) {
                                Toast.makeText(applicationContext,"Failed to get Data ${t.message.toString()} ",Toast.LENGTH_LONG ).show()
                            }
                        })

                        NetworkServiceKorona.service().getPositifGlobal().enqueue(object : Callback<ResponseGetKawalKorona> {
                            override fun onResponse(
                                    call: Call<ResponseGetKawalKorona>,
                                    response: Response<ResponseGetKawalKorona>
                            ) {
                                jumlahKasus_Total.text = response.body()?.total!!
                            }

                            override fun onFailure(call: Call<ResponseGetKawalKorona>, t: Throwable) {
                                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                            }

                        })

                        NetworkServiceKorona.service().getSembuhGlobal().enqueue(object : Callback<ResponseGetKawalKorona> {
                            override fun onResponse(
                                    call: Call<ResponseGetKawalKorona>,
                                    response: Response<ResponseGetKawalKorona>
                            ) {
                                jumlahSembuh_Total.text = response.body()?.total!!
                            }

                            override fun onFailure(call: Call<ResponseGetKawalKorona>, t: Throwable) {
                            }
                        } )
                    }
                    else if ( position == 1) {
                        NetworkServiceKorona.service().getDataIndonesia().enqueue(object : Callback<List<IndonesiaKawalAPI>> {
                            override fun onResponse(call: Call<List<IndonesiaKawalAPI>>, response: Response<List<IndonesiaKawalAPI>>) {
                                Toast.makeText(applicationContext,"ASDA",Toast.LENGTH_LONG).show()
                                val data = response.body()?.get(0)
                                val sembuh = data?.sembuh
                                val meninggal = data?.meninggal
                                val kasus = data?.positif

                                jumlahMeninggal_Total.text = meninggal.toString()
                                jumlahKasus_Total.text = kasus.toString()
                                jumlahSembuh_Total.text = sembuh.toString()
                            }

                            override fun onFailure(call: Call<List<IndonesiaKawalAPI>>, t: Throwable) {
                                Log.d("MESsADas",t.message.toString())
                                Toast.makeText(applicationContext,"ASDAaasdsadsa",Toast.LENGTH_LONG).show()
                            }

                        })
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }



        webkemenkes.setOnClickListener{
            val Intentkemenkes = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.p2ptm.kemkes.go.id/profil-p2ptm/daftar-informasi-publik/covid-19"))
            startActivity(Intentkemenkes)
        }

        websatgascovid.setOnClickListener{
            val Intentsatgas = Intent(Intent.ACTION_VIEW, Uri.parse("https://covid19.go.id/peta-risiko"))
            startActivity(Intentsatgas)
        }

        val mainSlider = findViewById<View>(R.id.corousel_Beranda) as ImageSlider

        val remoteimages: MutableList<SlideModel> = ArrayList()

        FirebaseDatabase.getInstance().reference.child("Slider")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) remoteimages.add(
                                SlideModel(
                                        data.child("url").value.toString(),
                                        data.child("title").value.toString(),
                                        ScaleTypes.FIT
                                )
                        )
                        mainSlider.setImageList(remoteimages, ScaleTypes.FIT)
                        mainSlider.setItemClickListener(object : ItemClickListener {
                            override fun onItemSelected(i: Int) {
                                Toast.makeText(
                                        applicationContext,
                                        remoteimages[i].title.toString(),
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        bottomNavigation.selectedItemId = R.id.nav_beranda
        bottomNavigation.itemBackground = ColorDrawable(resources.getColor(R.color.white))

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            item.setIcon(R.drawable.ic_baseline_home_24)
            when(item.itemId){
                R.id.nav_settings -> {
                    val nav_settings = Intent(this,PengaturanActivity::class.java)
                    nav_settings.putExtra("EXTRA_USER",user as Serializable)
                    nav_settings.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(nav_settings)
                }
                R.id.nav_covScan->{
                    val intent = Intent(this,KontenActivity::class.java)
                    intent.putExtra("EXTRA_USER",user as Serializable)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    item.setIcon(R.drawable.ic_baseline_camera_24)

                }
            }
           true
        }
    }
}

