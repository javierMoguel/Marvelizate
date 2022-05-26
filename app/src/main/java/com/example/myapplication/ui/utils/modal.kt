package com.example.myapplication.ui.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.AdminDB
import com.example.myapplication.CharacterViewHolder
import com.example.myapplication.Comic
import com.example.myapplication.Contract

class modal(view: View, id: Int, title: String, description: String, origin: String, purchase: String ) {
    val view = view
    val title = title
    val description = description
    val purchase = purchase
    val origin = origin
    val id = id

    public fun createModal(): AlertDialog.Builder {

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(title)
        builder.setMessage(description)
        if ( origin === "Home" ){
            builder.setPositiveButton("++Guardar") { dialog, which ->
                val comic = Comic(0, id.toInt(), title, purchase)
                val db = CharacterViewHolder.DB.readableDatabase
                var qry = "SELECT * FROM ${CharacterViewHolder.TB_COMIC} WHERE ${Contract.Comic.COMICID}='$id'"
                var result = db.rawQuery(qry, null)
                if(result.count > 0) {
                    Toast.makeText( view.context, "++Ya existe", Toast.LENGTH_SHORT).show()
                } else {
                    CharacterViewHolder.adminDb.addComic(comic)
                }
                db.close()

            }
            builder.setNegativeButton("``Cerrar") { dialog, which ->
                dialog.dismiss()
            }
        } else {
            builder.setPositiveButton("++Cerrar") { dialog, which ->
                dialog.dismiss()
            }
            builder.setNegativeButton("``Elimminar") { dialog, which ->
                deleteComic(title)
                dialog.dismiss()
            }
            if (purchase.isNotEmpty()) {
                builder.setNeutralButton("Comprar"){ dialog, wich ->
                    println("```comprar")
                    dialog.dismiss()
                }
            }
        }

        return builder
    }

    fun deleteComic( title: String ){
        try {
            val db = CharacterViewHolder.DB.writableDatabase
            var qry = "DELETE FROM ${CharacterViewHolder.TB_COMIC} " +
                    "WHERE ${Contract.Comic.TITLE} = '$title'"
            db.execSQL(qry)
            db.close()
        }catch (ex: Exception ) {
            Toast.makeText( view.context, "++No se pudo eliminar", Toast.LENGTH_SHORT).show()
        }
    }
}