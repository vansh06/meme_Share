package com.example.meme_share

import MySingleton
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var currentMemeUrl: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()
    }

    private fun loadmeme(){
        findViewById<ProgressBar>(R.id.pgbar).visibility=View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                currentMemeUrl = response.getString("url")
                Glide.with(this).load(currentMemeUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.pgbar).visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.pgbar).visibility=View.GONE
                        return false
                    }

                }).into(findViewById<ImageView>(R.id.memeImageView))
            }
        ) {
            findViewById<ProgressBar>(R.id.pgbar).visibility = View.GONE
            Toast.makeText(this, "Something went wrong" , Toast.LENGTH_LONG).show()
        }

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(req = jsonObjectRequest)

    }


    fun shareMeme(view: View) {
        val i=Intent(Intent.ACTION_SEND)
        i.type="text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "Hi, lookat this cool meme $currentMemeUrl")
        startActivity(Intent.createChooser(i,"Share this meme with"))
    }
    fun nextMeme(view: View) {
        loadmeme()
    }
}