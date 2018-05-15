package com.androidi.fos.alunoonline.entity

import android.graphics.Bitmap
import java.util.*

class Noticia(var id: Int? = 0,
              var titulo: String? = null,
              var dataNoticia: Date? = Date(),
              var descricaoCurta: String? = null,
              var descricao: String? = null,
              var imagem:Bitmap? = null)