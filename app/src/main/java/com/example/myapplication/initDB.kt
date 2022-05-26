package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.CharacterViewHolder

class initDB: SQLiteOpenHelper(CharacterViewHolder.CONTEXT, CharacterViewHolder.DB_NAME, null, CharacterViewHolder.version) {

    val qryCrearTabla = "CREATE TABLE ${CharacterViewHolder.TB_COMIC}(" +
            "${Contract.Comic.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${Contract.Comic.COMICID} INTEGER," +
            "${Contract.Comic.TITLE} TEXT NOT NULL," +
            "${Contract.Comic.PURCHASE} TEXT NOT NULL);"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(qryCrearTabla)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}