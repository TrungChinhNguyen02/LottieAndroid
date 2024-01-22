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
import kotlinx.coroutines.Job
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
        addImagePair(R.drawable.after, R.drawable.before)
        addImagePair(R.drawable.after1, R.drawable.before1)
        addImagePair(R.drawable.after2, R.drawable.before2)
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
        val desiredWidth = 360
        val lavChest = findViewById<LottieAnimationView>(R.id.aimation)

        val desiredHeight = 221 // Replace with your desired height

        val resizedBitmapAfter: Bitmap = Bitmap.createScaledBitmap(
            imagePair.first,
            desiredWidth.toInt(), desiredHeight, true
        )
        val resizedBitmapBefore: Bitmap = Bitmap.createScaledBitmap(
            imagePair.second,
            desiredWidth.toInt(), desiredHeight, true
        )


        // Execute AsyncTask to perform heavy work on a background thread
        handler.postDelayed({
            LoadImagesAsyncTask(lavChest, resizedBitmapAfter, resizedBitmapBefore, imagePair).execute()
            isAnimationInProgress = true
            lavChest.speed = 1f
            lavChest.playAnimation()
        }, 100)
    }

    inner class LoadImagesAsyncTask(
        private val lavChest: LottieAnimationView,
        private val resizedBitmapAfter: Bitmap,
        private val resizedBitmapBefore: Bitmap,
        private val imagePair: Pair<Bitmap, Bitmap>
    ) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            // Perform heavy work here (e.g., applying animation)
            try {
                Thread.sleep(2500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            publishProgress()
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            // Update UI with the results (e.g., update LottieAnimationView)
            lavChest.updateBitmap("image_0", resizedBitmapAfter)
            lavChest.updateBitmap("image_1", resizedBitmapBefore)

        }
        override fun onPostExecute(result: Void?) {
            // AsyncTask finished, you can initiate the next task or perform other actions
            isAnimationInProgress = false
            // Start the next animation after the current one ends
            val nextImagePairIndex = (imagePairs.indexOf(imagePair) + 1) % imagePairs.size
            upDataImageJson(imagePairs[nextImagePairIndex])
        }
    }
}