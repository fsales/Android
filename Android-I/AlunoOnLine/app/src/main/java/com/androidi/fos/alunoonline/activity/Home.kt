package com.androidi.fos.alunoonline.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.androidi.fos.alunoonline.R
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar);
    }
}
