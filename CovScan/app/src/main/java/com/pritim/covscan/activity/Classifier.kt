package com.pritim.covscan.activity

import android.content.res.AssetManager
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Classifier(assetManager: AssetManager , modelPath : String, labelPath : String,
input_size:Int) {

    private lateinit var interpreter : Interpreter
    private lateinit var labelListCT : List<String>
    private val INPUT_SIZE : Int = input_size
    private val PIXEL_SIZE : Int = 3
    private val IMAGE_MEAN : Int = 0
    private val IMAGE_STD : Float = 255.0f
    private val MAX_RESULTS = 2
    private val THRESHOLD = 0.4f

    data class Recognition (
        var id : String = "" ,
        var title : String = "",
        var confidience : Float = 0f) {
        override fun toString(): String {
            return "Title =$title, Confidence = $confidience"
        }
    }

    init {

        val options = Interpreter.Options()
        options.setNumThreads(5)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assetManager,modelPath),options)
        labelListCT = loadLabelListCT(assetManager,labelPath)

    }

    private fun loadModelFile(assetManager: AssetManager, modelPath : String) : MappedByteBuffer {

        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel

        val startOffSet = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffSet,declaredLength)

    }

    private fun loadLabelListCT(assetManager: AssetManager, labelPath : String) : List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }

    }

    fun recognizeImage(bitmap : Bitmap) : Array<FloatArray>{
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap,INPUT_SIZE,INPUT_SIZE,false)
        val byteBuffer = convertBitMapToByteBuffer(scaledBitmap)
        val res = Array(1) {FloatArray(1)}
        interpreter.run(byteBuffer, res)
        return res
    }

    private fun convertBitMapToByteBuffer(bitmap: Bitmap) :ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)

        bitmap.getPixels(intValues, 0, bitmap.width, 0,0,bitmap.width,bitmap.height)
        var pixel = 0
        for (i in 0 until  INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val input = intValues[pixel++]
                byteBuffer.putFloat((((input.shr(16)  and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((input.shr(8) and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((input and 0xFF) - IMAGE_MEAN) / IMAGE_STD))

            }
        }
    return byteBuffer
    }


}