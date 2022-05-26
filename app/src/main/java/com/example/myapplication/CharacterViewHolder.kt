package com.example.myapplication

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.AdminDB
import com.example.myapplication.Comic
import com.example.myapplication.Contract
import com.example.myapplication.databinding.ItemCharacterBinding
import com.example.myapplication.initDB
import com.example.myapplication.ui.utils.modal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterViewHolder ( view:View): RecyclerView.ViewHolder(view) {

    companion object{
        val DB_NAME = "ComicsDb.db"
        val version = 1
        val TB_COMIC = "tcComic"
        lateinit var CONTEXT: Context
        lateinit var DB: initDB
        val adminDb = AdminDB()
    }

    private val binding = ItemCharacterBinding.bind(view)
    private var id: String = ""
    private var title: String = ""
    private var purchase: String = ""

    init{
        CONTEXT = view.context
        DB = initDB()
        view.setOnClickListener{ v: View ->
            val idQuery = id.substring(id.indexOf("/comics/")+8, id.length)
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(ApiService::class.java)
                    .getComic("$idQuery?ts=12&apikey=d361a09e2152ec9079056ea43d6d524b&hash=9d011ed674ff2cb0e3ada029a8d14351")
                val comic = call.body()
                Handler(Looper.getMainLooper()).post(Runnable {
                    if( call.isSuccessful ) {
                        val data = comic?.data?.results
                        purchase = data?.get(0)?.urls?.filter { it -> it.type === "purchase" }.toString()

                        val modal = modal( view, idQuery.toInt(),data?.get(0)?.title.toString(),
                            data?.get(0)?.description.toString(), "Home", purchase).createModal()

                        modal.show()

                    }else{
                        showError()
                    }
                })

            }
            }
    }

    private fun showError(){
        Toast.makeText(CONTEXT, "Hola", Toast.LENGTH_SHORT).show()
    }

    fun bind(dataResponse: String, itemId: String) {
        binding.icText.text = dataResponse.toString()
        id = itemId
        title = dataResponse.toString()
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(id.substring(0, id.indexOf("/comics/")+8))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

