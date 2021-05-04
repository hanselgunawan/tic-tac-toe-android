package com.hanseltritama.tictactoegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var o_turn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }

    private fun setupUI() {
        for (imageId in 0..8) {
            val scale: Float = applicationContext.resources.displayMetrics.density
            val imageView = ImageView(this)
            val param: GridLayout.LayoutParams = GridLayout.LayoutParams()
            Glide.with(this)
                .load(R.drawable.ic_launcher_background)
                .into(imageView)
            param.height = (100 * scale + 0.5f).toInt()
            param.width = (100 * scale + 0.5f).toInt()
            param.setMargins(5,5,5,5)
            imageView.id = imageId
            imageView.layoutParams = param
            imageView.setOnClickListener {
                onImageClick(imageId)
            }
            grid_layout_container.addView(imageView)
        }
    }

    private fun onImageClick(imageIndex: Int) {
        val clickedImage: ImageView = findViewById(imageIndex)
        if (!o_turn) {
            Glide.with(this)
                .load(R.drawable.ximage)
                .into(clickedImage)
        } else {
            Glide.with(this)
                .load(R.drawable.oimage)
                .into(clickedImage)
        }
        o_turn = !o_turn
    }
}