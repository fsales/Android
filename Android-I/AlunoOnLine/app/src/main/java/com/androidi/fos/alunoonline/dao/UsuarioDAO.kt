package com.androidi.fos.alunoonline.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.androidi.fos.alunoonline.entity.Usuario

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun incluir(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE email = :email ")
    fun getUsuario(email: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha ")
    fun getUsuario(email: String, senha: String): Usuario?
}