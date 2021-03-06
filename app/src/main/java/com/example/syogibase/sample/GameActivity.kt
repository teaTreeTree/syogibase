package com.example.syogibase.sample

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.syogibase.R
import com.example.syogibase.domain.value.BoardMode
import com.example.syogibase.domain.value.Turn
import com.example.syogibase.presentation.contact.GameViewContact
import com.example.syogibase.presentation.view.GameView


class GameActivity : AppCompatActivity() {

    var frame: FrameLayout? = null

    private lateinit var gameView: GameView
    private lateinit var winView: WinLoseModal
    private lateinit var endView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        frame = this.findViewById(R.id.frame) as FrameLayout
        gameView = GameView(this)
        gameView.setGameEndListener(object : GameViewContact.GameEndListener {
            override fun onGameEnd(winner: Turn?) {
                AlertDialog.Builder(this@GameActivity)
                    .setTitle("終了")
                    .setMessage("勝ち")
                    .setPositiveButton("終了") { dialog, which ->
                        finish()
                    }
                    .setNeutralButton("もう一度") { dialog, which ->
                        gameView.reset()
                    }
                    .setNegativeButton("感想戦") { dialog, which ->
                    }
                    .show()
            }
        }
        )
        frame!!.addView(gameView, 0)
    }

    fun gameEnd(winner: Int) {
        val viewGroup = this.findViewById(R.id.constraint_layout) as ConstraintLayout
        winView = WinLoseModal(
            this,
            winner,
            frame!!.width.toFloat(),
            frame!!.height.toFloat()
        )

        frame!!.addView(winView, 1)
        endView = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        endView.startAnimation(animation)
        endView.visibility = View.VISIBLE
    }

    //決着後の選択ボダン
    fun end(v: View) {
        val intent = Intent(application, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //もう一度
    fun restart(v: View) {
        val intent = Intent()
        intent.setClass(this, this.javaClass)
        this.startActivity(intent)
        this.finish()
    }

    fun replay(v: View) {
        winView.visibility = View.GONE
        frame!!.removeViewAt(1)
        endView.visibility = View.GONE
        gameView.setBoardMove(BoardMode.REPLAY)
    }
}
