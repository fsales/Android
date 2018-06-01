package com.androidi.fos.alunoonline.extension

import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.androidi.fos.alunoonline.R
import org.jetbrains.anko.indeterminateProgressDialog

fun AppCompatActivity.load(delay: Long = 1000){
    val a = indeterminateProgressDialog(getString(R.string.msg_processando)).apply {
        title = "Aluno On Line"
        max = delay.toInt()
        setCancelable(false)
        show()
    }

    Handler().postDelayed({
        a.dismiss()
    }, delay)
}