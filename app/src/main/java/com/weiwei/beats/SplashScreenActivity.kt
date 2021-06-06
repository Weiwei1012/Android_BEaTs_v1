package com.weiwei.beats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgroundImageView = findViewById<ImageView>(R.id.splashLogo)
        val animation= AnimationUtils.loadAnimation(this,R.anim.splash_anim)
        backgroundImageView.startAnimation(animation)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2500)
    }
}