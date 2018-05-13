package com.androidi.fos.alunoonline.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.androidi.fos.alunoonline.R

class SplashScreen : AppCompatActivity() {

    private val DELAY = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, Login::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }, DELAY.toLong())
    }
}
