package com.androidi.fos.alunoonline.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "usuario", indices = arrayOf(Index(value = "email")))
class Usuario(

        @PrimaryKey(autoGenerate = true) var uid: Int = 0,

        @ColumnInfo(name = "email") var email: String? = "",

        @ColumnInfo(name = "senha") var senha: String? = ""
)