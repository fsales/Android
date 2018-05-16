package com.androidi.fos.alunoonline.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.androidi.fos.alunoonline.dao.UsuarioDAO
import com.androidi.fos.alunoonline.entity.Usuario

@Database(entities = arrayOf(Usuario::class), version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun usuarioDAO(): UsuarioDAO

    companion object {
        val DB_NAME = "aluno_online_db"
        var dbInstance: AppDataBase? = null


        fun getDatabase(context: Context): AppDataBase? {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder<AppDataBase>(context.applicationContext, AppDataBase::class.java, DB_NAME).allowMainThreadQueries().build()
            }
            return dbInstance
        }
    }
}