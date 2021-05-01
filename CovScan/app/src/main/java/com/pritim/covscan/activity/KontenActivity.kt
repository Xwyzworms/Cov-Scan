package com.pritim.covscan.activity

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pritim.covscan.R

class KontenActivity : AppCompatActivity(), View.OnClickListener {
    private val mInputSize = 150
    private val mModelPath = "ctScan.tflite"
    private lateinit var classifier : Classifier
    private  val mLabelPath = "labelct.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_utama)
        initClassifier()
        initViews()
    }

    private fun initClassifier(){
        classifier = Classifier(assets,mModelPath,mLabelPath,mInputSize)
    }

    private  fun initViews() {
        findViewById<ImageView>(R.id.ImgTapDisini).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val bitmap = ((v as ImageView ).drawable as BitmapDrawable).bitmap
        val res = classifier.recognizeImage(bitmap)
        runOnUiThread {
            Toast.makeText(this,res.toString(),Toast.LENGTH_LONG).show()
        }
    }
}