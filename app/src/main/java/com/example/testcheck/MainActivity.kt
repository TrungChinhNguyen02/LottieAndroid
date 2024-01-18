package com.example.testcheck

import android.animation.Animator
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.ImageAssetDelegate
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieImageAsset
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private var imagePairs: MutableList<Pair<Bitmap, Bitmap>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmapAfter1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after)
        val bitmapBefore1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before)
        imagePairs.add(Pair(bitmapAfter1, bitmapBefore1))

        val bitmapAfter2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after1)
        val bitmapBefore2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before1)
        imagePairs.add(Pair(bitmapAfter2, bitmapBefore2))

        val bitmapAfter3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after2)
        val bitmapBefore3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before2)
        imagePairs.add(Pair(bitmapAfter3, bitmapBefore3))

        // Initialize the animation with the first pair
        upDataImageJson(imagePairs[0])
    }

    fun upDataImageJson(imagePair: Pair<Bitmap, Bitmap>) {
        val (bitmapAfter, bitmapBefore) = imagePair

        val lavChest = findViewById<LottieAnimationView>(R.id.aimation)

        val density = resources.displayMetrics.density

        val desiredWidth = resources.displayMetrics.run { widthPixels / density }
        val desiredHeight = 221 // Replace with your desired height
        Log.d("hihi", "upDataImageJson:$desiredWidth ")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.after)
//        val bitmap1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.before)

        val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(
            bitmapAfter,
            desiredWidth.toInt(), desiredHeight, true
        )
        val resizedBitmap1: Bitmap = Bitmap.createScaledBitmap(
            bitmapBefore,
            desiredWidth.toInt(), desiredHeight, true
        )
        lavChest.addAnimatorUpdateListener {
            lavChest.updateBitmap("image_0", resizedBitmap)
            lavChest.updateBitmap("image_1", resizedBitmap1)
        }
        lavChest.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator) {
                Log.d("Animation", "Animation repeated")

            }

            override fun onAnimationEnd(animation: Animator) {
                val nextImagePairIndex = (imagePairs.indexOf(imagePair) + 1) % imagePairs.size
                upDataImageJson(imagePairs[nextImagePairIndex])

            }

            override fun onAnimationCancel(animation: Animator) {
                Log.d("Animation", "Animation repeated")

            }

            override fun onAnimationStart(animation: Animator) {
                Log.d("Animation", "Animation repeated")

            }
        })
        lavChest.setImageAssetDelegate(object : ImageAssetDelegate {
            override fun fetchBitmap(asset: LottieImageAsset): Bitmap? {
                return when (asset.id) {
                    "image_0" -> bitmapAfter
                    "image_1" -> bitmapBefore
                    else -> {
                        val am: AssetManager = this@MainActivity.assets
                        try {
                            BitmapFactory.decodeStream(am.open("aep/${asset.dirName}${asset.fileName}"))
                        } catch (e: IOException) {
                            e.printStackTrace()
                            null
                        }
                    }
                }
            }
        })
        lavChest.playAnimation()
    }
}
