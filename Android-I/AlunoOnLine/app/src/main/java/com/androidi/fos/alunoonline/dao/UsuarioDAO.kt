package com.androidi.fos.alunoonline.dao

import android.arch.persistence.room.*
import com.androidi.fos.alunoonline.entity.Usuario

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun incluir(usuario: Usuario)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun atualizar(usuario:Usuario)

    @Query("SELECT * FROM Usuario WHERE email = :email ")
    fun getUsuario(email: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha ")
    fun getUsuario(email: String, senha: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE uid = :uid ")
    fun getUsuario(uid: Int): Usuario?


}