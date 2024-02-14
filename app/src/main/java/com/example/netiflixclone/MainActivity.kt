package com.example.netiflixclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netiflixclone.model.Category
import com.example.netiflixclone.model.Movie
import com.example.netiflixclone.util.CategoryTask

class MainActivity : AppCompatActivity(),CategoryTask.Callback {

    private lateinit var progressBar:ProgressBar
    private  val categories = mutableListOf<Category>()
    private lateinit var adapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         progressBar=findViewById(R.id.progress_main)




        val rc_main:RecyclerView =findViewById(R.id.rc_main)
        rc_main.layoutManager=LinearLayoutManager(this)
        adapter =CategoryAdapter(categories) { id->

            val intent =Intent(this,MovieActivity2::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        rc_main.adapter =adapter

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=8b68cf2d-8f51-41d9-8e65-4d4491d7379d")
    }

    override fun OnPressExcute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
      //here when call the callback

        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()

        progressBar.visibility =View.GONE
    }

    override fun onFailure(message: String) {

        Toast.makeText( this, message,Toast.LENGTH_SHORT).show()
        progressBar.visibility =View.GONE
    }
}
