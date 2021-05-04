package com.hanseltritama.tictactoegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var o_turn: Boolean = false
    private var winningStreak: Int = 0
    private var turns: Int = 0
    private var gameArray: IntArray = IntArray(9)
    private val winningArray = arrayOf(
        intArrayOf(0,1,2),
        intArrayOf(3,4,5),
        intArrayOf(6,7,8),
        intArrayOf(0,3,6),
        intArrayOf(1,4,7),
        intArrayOf(2,5,8),
        intArrayOf(0,4,8),
        intArrayOf(2,4,6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        setupGrid()
        reset_button.setOnClickListener {
            resetGame()
        }
    }

    private fun setupGrid() {
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

    private fun resetGame() {
        grid_layout_container.removeAllViews()
        gameArray = IntArray(9)
        grid_layout_container.alpha = 1f
        game_over_tv.visibility = View.GONE
        game_over_subtitle.visibility = View.GONE
        setupUI()
        winningStreak = 0
        turns = 0
        o_turn = false
        reset_button.visibility = View.GONE
    }

    private fun onImageClick(imageIndex: Int) {
        val clickedImage: ImageView = findViewById(imageIndex)
        if (!o_turn) {
            Glide.with(this)
                .load(R.drawable.ximage)
                .into(clickedImage)
            gameArray[imageIndex] = 1
        } else {
            Glide.with(this)
                .load(R.drawable.oimage)
                .into(clickedImage)
            gameArray[imageIndex] = 2
        }
        turns += 1
        if (turns < 9) {
            checkGameWinning()
        } else {
            drawGame()
        }
        o_turn = !o_turn
    }

    private fun checkGameWinning() {
        for (winning in winningArray.indices) {
            winningStreak = 0
            for (winningIdx in winningArray[winning]) {
                if (o_turn) {
                    if (gameArray[winningIdx] != 2) break
                } else {
                    if (!o_turn && gameArray[winningIdx] != 1) break
                }
                winningStreak++
            }
            if (winningStreak == 3) {
                determineWinner()
            }
        }
    }

    private fun determineWinner() {
        if (o_turn) {
            game_over_subtitle.text = resources.getString(R.string.player_o_wins)
        } else {
            game_over_subtitle.text = resources.getString(R.string.player_x_wins)
        }
        displayGameOverView()
    }

    private fun drawGame() {
        game_over_subtitle.text = resources.getString(R.string.draw_game)
        displayGameOverView()
    }

    private fun displayGameOverView() {
        game_over_tv.visibility = View.VISIBLE
        game_over_subtitle.visibility = View.VISIBLE
        grid_layout_container.alpha = 0.10f
        reset_button.visibility = View.VISIBLE
    }
}