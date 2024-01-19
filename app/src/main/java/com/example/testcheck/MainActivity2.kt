package com.example.testcheck
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.airbnb.lottie.LottieAnimationView

class MainActivity2 : AppCompatActivity() {
    private var imagePairs: MutableList<Pair<Bitmap, Bitmap>> = mutableListOf()
    private var isAnimationInProgress = false
    private var currentIndex = 0
    private val handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bitmapAfter1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after)
        val bitmapBefore1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before)
        imagePairs.add(Pair(bitmapAfter1, bitmapBefore1))

        val bitmapAfter2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after1)
        val bitmapBefore2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before1)
        imagePairs.add(Pair(bitmapAfter2, bitmapBefore2))

        val bitmapAfter3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after2)
        val bitmapBefore3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before2)
        imagePairs.add(Pair(bitmapAfter3, bitmapBefore3))

        upDataImageJson(imagePairs[2])
    }
    fun upDataImageJson(imagePair: Pair<Bitmap, Bitmap>) {
        if (isAnimationInProgress) {
            return // Avoid starting a new animation while the previous one is still in progress
        }
        isAnimationInProgress = true

        val (bitmapAfter, bitmapBefore) = imagePair

        val lavChest = findViewById<LottieAnimationView>(R.id.aimation)
        val density = resources.displayMetrics.density

        val desiredWidth = resources.displayMetrics.run { widthPixels / density }
        val desiredHeight = 221 // Replace with your desired height
        Log.d("hihi", "upDataImageJson:$desiredWidth ")

        val resizedBitmapAfter: Bitmap = Bitmap.createScaledBitmap(
            bitmapAfter,
            desiredWidth.toInt(), desiredHeight, true
        )
        val resizedBitmapBefore: Bitmap = Bitmap.createScaledBitmap(
            bitmapBefore,
            desiredWidth.toInt(), desiredHeight, true
        )
        // Execute AsyncTask to perform heavy work on a background thread
        handler.postDelayed({
            LoadImagesAsyncTask(lavChest, resizedBitmapAfter, resizedBitmapBefore, imagePair).execute()
            isAnimationInProgress = true
            lavChest.speed = 1f
            lavChest.playAnimation()
        },0)
    }
    @Suppress("DEPRECATION")
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