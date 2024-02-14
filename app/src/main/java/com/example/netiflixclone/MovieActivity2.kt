package com.example.netiflixclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netiflixclone.model.Movie

class MovieActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie2)

        val txtTitle:TextView =findViewById(R.id.movie_txt_title)
        val txtDes :TextView =findViewById(R.id.txt_des)
        val txtCost :TextView =findViewById(R.id.txt_cost)
        val rv :RecyclerView =findViewById(R.id.rvmovie_similar)

        txtTitle.text ="Os vingadores"
        txtDes.text = "Essa é a descrição do filme Os vingadores " +
                "Loki (Tom Hiddleston) retorna à Terra enviado pelos chitauri, uma raça alienígena que pretende dominar os humanos. Com a promessa de que será o soberano do planeta," +
                " ele rouba o cubo cósmico dentro de instalações da S.H.I.E.L.D. e, com isso, adquire grandes poderes. Loki os usa para controlar o dr. "
        txtCost.text ="Ator A , Ator B, Atriz A, Atriz B"

        val movies = mutableListOf<Movie>()

        rv.layoutManager =GridLayoutManager(this,3)
        rv.adapter =MovieAdapter(movies,R.layout.movie_iten_similar)


        val toolbar:Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)


        supportActionBar?.setHomeAsUpIndicator(R.drawable.seta)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =null



    }
    override fun  onOptionsItemSelected(item: MenuItem):Boolean {
       if (item.itemId == android.R.id.home) {
           finish()
       }
        return super.onOptionsItemSelected(item)
    }

}