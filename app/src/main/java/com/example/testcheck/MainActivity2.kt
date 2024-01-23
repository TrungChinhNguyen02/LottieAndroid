package com.example.testcheck

//import android.animation.ObjectAnimator
//import android.animation.TypeConverter
//import android.content.Context
//import android.content.pm.PackageManager
//import android.content.res.AssetManager
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.media.MediaRecorder
//import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
//import android.os.Environment
//import android.os.Handler
//import android.util.Log
//import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.airbnb.lottie.LottieAnimationView
//import com.airbnb.lottie.LottieComposition
//import com.airbnb.lottie.LottieDrawable
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//import kotlin.math.log


class MainActivity2 : AppCompatActivity() {
    private lateinit var edtsdt: EditText
    private lateinit var btnchuyen: Button
    private lateinit var tv: TextView
    private lateinit var tvv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
//        setupImagePairs()
//        upDataImageJson(imagePairs[2])
        edtsdt = findViewById(R.id.phone)
        btnchuyen = findViewById(R.id.button)
        tv = findViewById(R.id.show)
        tvv = findViewById(R.id.showww)
        btnchuyen.setOnClickListener {
            val inputPhoneNumber = edtsdt.text.toString()
            val phoneNumberUtil = PhoneNumberUtil.getInstance()

            try {
                val phoneNumberProto: Phonenumber.PhoneNumber =
                    phoneNumberUtil.parse(inputPhoneNumber, null)
                val countryCode = phoneNumberProto.countryCode
                val country = phoneNumberUtil.getRegionCodeForNumber(phoneNumberProto)
                if (country != null){
                    val formattedPhoneNumber = phoneNumberUtil.format(phoneNumberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
                    tvv.text = formattedPhoneNumber
                }
                tv.text = "Country Code: $countryCode, Country: $country"
            } catch (e: Exception) {
                e.printStackTrace()
                tv.text = "Invalid Phone Number"
            }
        }
    }
}