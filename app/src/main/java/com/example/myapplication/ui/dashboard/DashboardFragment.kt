package com.example.myapplication.ui.dashboard

import android.database.CharArrayBuffer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.AdminDB
import com.example.myapplication.CharacterViewHolder
import com.example.myapplication.Contract
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.ui.utils.modal

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    val comicAdmin = AdminDB()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onResume() {
        super.onResume()
        mostrarDB()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mostrarDB(){
        val titles = comicAdmin.getAllTitles()
        val adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, titles) }
        _binding?.listComics?.adapter = adapter
        _binding?.listComics?.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val item = titles.get(i)

            try {
                val db = CharacterViewHolder.DB.readableDatabase
                var qry = "SELECT * FROM ${CharacterViewHolder.TB_COMIC} WHERE ${Contract.Comic.TITLE}='$item'"
                var result = db.rawQuery(qry, null)
                if(result.count != 0){
                    if( result.moveToFirst()){
                        do{
                            var idQuery = result.getLong(result.getColumnIndex("ComicId").toInt())
                            var title = result.getString(result.getColumnIndex("title").toInt())
                            var description = ""
                            var purchase = result.getString(result.getColumnIndex("purchase").toInt())
                            val modal = modal( view, idQuery.toInt(),title, description,"", purchase).createModal()
                            modal.show()
                        }while(result.moveToNext())
                    }
                }
                db.close()
            }catch (ex: Exception ) {
                println(ex)
                Toast.makeText( view.context, "++No se pudo", Toast.LENGTH_SHORT).show()
            }

        }
    }
}