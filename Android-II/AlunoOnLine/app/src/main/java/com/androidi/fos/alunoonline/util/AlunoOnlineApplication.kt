package com.androidi.fos.alunoonline.util

import android.app.Application
import android.arch.persistence.room.Room
import com.androidi.fos.alunoonline.db.AppDataBase
import com.androidi.fos.alunoonline.entity.Usuario

class AlunoOnlineApplication() : Application() {

    var usuarioLogado: Usuario? = null

    fun appDataBase(): AppDataBase? = Room.databaseBuilder<AppDataBase>(applicationContext, AppDataBase::class.java, AppDataBase.DB_NAME).allowMainThreadQueries().build()

}