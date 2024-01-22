package com.example.testcheck

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

import kotlin.math.log


class MainActivity2 : AppCompatActivity() {
    private val imagePairs: MutableList<Pair<Bitmap, Bitmap>> = mutableListOf()
    private var isAnimationInProgress = false
    private val handler = Handler()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setupImagePairs()
        upDataImageJson(imagePairs[2])

    }
//    thêm ảnh vào bitmap
    private fun setupImagePairs() {
        addImagePair(R.drawable.b, R.drawable.a)
        addImagePair(R.drawable.b1, R.drawable.a1)
        addImagePair(R.drawable.b2, R.drawable.a2)
    }

    private fun addImagePair(afterResourceId: Int, beforeResourceId: Int) {
        val bitmapAfter: Bitmap = BitmapFactory.decodeResource(resources, afterResourceId)
        val bitmapBefore: Bitmap = BitmapFactory.decodeResource(resources, beforeResourceId)
        imagePairs.add(Pair(bitmapAfter, bitmapBefore))
    }

    private fun upDataImageJson(imagePair: Pair<Bitmap, Bitmap>) {

        if (isAnimationInProgress) {
            return // Avoid starting a new animation while the previous one is still in progress
        }
        isAnimationInProgress = true

//        val desiredWidth = resources.displayMetrics.run { widthPixels / density }
        val desiredWidth = 1000
        val lavChest = findViewById<LottieAnimationView>(R.id.aimation)

        val desiredHeight = 614 // Replace with your desired height

        val resizedBitmapAfter: Bitmap = Bitmap.createScaledBitmap(
            imagePair.first,
            desiredWidth.toInt(), desiredHeight, true
        )
        val resizedBitmapBefore: Bitmap = Bitmap.createScaledBitmap(
            imagePair.second,
            desiredWidth.toInt(), desiredHeight, true
        )
        job = CoroutineScope(Dispatchers.Default).launch {
            delay(2500) // Simulate heavy work
            withContext(Dispatchers.Main) {
                // Update UI with the results (e.g., update LottieAnimationView)
                lavChest.updateBitmap("image_0", resizedBitmapAfter)
                lavChest.updateBitmap("image_1", resizedBitmapBefore)
                lavChest.speed = 1f
                lavChest.playAnimation()
                isAnimationInProgress = false
                // Start the next animation after the current one ends
                val nextImagePairIndex = (imagePairs.indexOf(imagePair) + 1) % imagePairs.size
                upDataImageJson(imagePairs[nextImagePairIndex])
            }
        }
    }

}