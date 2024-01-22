package com.example.testcheck

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.ImageAssetDelegate
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieImageAsset
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var lineView: View
    private var currentImageIndex = 1

    private val totalWidth: Float by lazy {
        imageView1.width.toFloat() + imageView2.width.toFloat()
    }

    private val duration = 5000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        lineView = findViewById(R.id.lineView)


        imageView2.visibility = View.INVISIBLE

        val fadeIn = ObjectAnimator.ofFloat(imageView1, "alpha", 1f, 0f)
        fadeIn.duration = 1000L // Thời gian di chuyển của ảnh (ms)

        val fadeOut = ObjectAnimator.ofFloat(imageView2, "alpha", 0f, 1f)
        fadeOut.duration = 1000L

        val lineMoveAnimator = ValueAnimator.ofFloat(0f, totalWidth)
        lineMoveAnimator.duration = 1000L
        lineMoveAnimator.addUpdateListener { valueAnimator ->
            val translationX = valueAnimator.animatedValue as Float
            lineView.translationX = translationX

            val alpha = 1f - (translationX / totalWidth)
            imageView2.alpha = alpha

            // Kiểm tra điều kiện để ẩn hiện ảnh khi thanh line đi qua một vị trí cụ thể
            val conditionPosition = totalWidth / 2
            if (translationX >= conditionPosition) {
                imageView1.visibility = View.INVISIBLE
                imageView2.visibility = View.VISIBLE
            } else {
                imageView1.visibility = View.VISIBLE
                imageView2.visibility = View.INVISIBLE
            }
        }

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeIn, fadeOut)
        animatorSet.start()

        lineMoveAnimator.start()
    }
    private fun imageSwap() {
        val temp = imageView1
        imageView1 = imageView2
        imageView2 = temp

        imageView1.alpha = 1f
        imageView2.alpha = 0f

        // Đặt lại vị trí và alpha của thanh line khi chuyển ảnh
        lineView.translationX = 0f
        lineView.alpha = 1f
    }
}


