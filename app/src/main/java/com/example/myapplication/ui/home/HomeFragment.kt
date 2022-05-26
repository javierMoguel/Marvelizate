package com.example.myapplication.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ApiService
import com.example.myapplication.CharacterAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: CharacterAdapter
    private val strs = mutableListOf<String>()
    private val ids = mutableListOf<String>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
         val root: View = binding.root

        _binding!!.svChar.setOnQueryTextListener(this)

        initRecyclerView()

        return root
    }

    private fun initRecyclerView() {
        adapter = CharacterAdapter(strs, ids)
        _binding!!.rvChar.layoutManager = LinearLayoutManager(activity)
        _binding!!.rvChar.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchCharacter(query: String?){
        viewLifecycleOwner.lifecycleScope.launch{
            val call = getRetrofit().create(ApiService::class.java)
                .getCharacters("characters?name=$query&ts=12&apikey=d361a09e2152ec9079056ea43d6d524b&hash=9d011ed674ff2cb0e3ada029a8d14351")
            val characters = call.body()
            if( call.isSuccessful ) {
                val data = characters?.data?.results
                strs.clear()
                data?.get(0)?.comics?.items?.forEach { it ->
                    strs.add(it.name)
                    ids.add(it.resourceURI)
                }
                adapter.notifyDataSetChanged()
            }else{
                showError()
            }
        }
    }

    private fun showError(){
        Toast.makeText(activity, "Hola", Toast.LENGTH_SHORT).show()

    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()) {
            searchCharacter(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}