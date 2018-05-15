package com.androidi.fos.alunoonline.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.androidi.fos.alunoonline.R

class Galeria @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    val TOTAL_IMAGEM = 2
    val DELAY: Long = 4000
    val IMAGENS = intArrayOf(R.drawable.img0, R.drawable.img1, R.drawable.img2)

    var contador: Int? = null
   

    private fun getContador(): Int =
            if (contador == null || contador == TOTAL_IMAGEM.toInt()) {
                contador = 0
                contador!!
            } else {
                contador = contador!! + 1
                contador!!
            }

    private fun getXBitmap(bmp: Bitmap): Float = width.toFloat() / bmp.width.toFloat()

    private fun getYBitmap(bmp: Bitmap): Float = height.toFloat() / bmp.height.toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val bmp = BitmapFactory.decodeResource(resources, IMAGENS[getContador()])
        val matrix = Matrix()
        matrix.postScale(getXBitmap(bmp), getYBitmap(bmp))
        canvas.drawColor(Color.TRANSPARENT)
        canvas.drawBitmap(Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true), 0f, 0f, Paint())


        postInvalidateDelayed(DELAY)


    }
}