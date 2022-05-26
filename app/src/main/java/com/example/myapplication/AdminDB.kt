package com.example.myapplication

import android.database.DatabaseUtils
import android.widget.Toast
import com.example.myapplication.CharacterViewHolder
import kotlinx.coroutines.channels.ticker
import java.lang.Exception
import java.util.ArrayList

data class Comic( var id: Int, var comicId: Int, var title: String, var purchase: String)

class AdminDB {

    fun getAllTitles(): ArrayList<String>{
        val titles = arrayListOf<String>()
        try {
            val db = CharacterViewHolder.DB.readableDatabase
            val numDatos = DatabaseUtils.queryNumEntries( db, CharacterViewHolder.TB_COMIC).toInt()
            if( numDatos > 0 ) {
                val qry = "SELECT ${Contract.Comic.TITLE} FROM ${CharacterViewHolder.TB_COMIC}"
                val results = db.rawQuery(qry, null)
                results.moveToFirst()
                do {
                    titles.add(results.getString(results.getColumnIndex(Contract.Comic.TITLE).toInt()))
                }while (results.moveToNext())
            }else {
                Toast.makeText(CharacterViewHolder.CONTEXT, "++ErrorDb Vacía", Toast.LENGTH_SHORT).show()
            }
            db.close()
            return titles
        }catch (ex: Exception){
            Toast.makeText(CharacterViewHolder.CONTEXT, "++ErrorDb2", Toast.LENGTH_SHORT).show()
            return titles
        }
    }

    fun addComic( comic: Comic){
        try {
            val db = CharacterViewHolder.DB.writableDatabase
            val qry = "INSERT INTO ${CharacterViewHolder.TB_COMIC} (" +
                    "${Contract.Comic.COMICID}, ${Contract.Comic.TITLE}, ${Contract.Comic.PURCHASE})" +
                    "VALUES('${comic.comicId}', '${comic.title}', '${comic.purchase}')";
            db.execSQL(qry)
            db.close()
        }catch (ex: Exception){
            Toast.makeText(CharacterViewHolder.CONTEXT, "++ErrorDb", Toast.LENGTH_SHORT).show()
        }
    }
}