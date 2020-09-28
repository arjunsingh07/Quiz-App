package com.example.myquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*


class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(Constants.USER_NAME)
        tv_username.text = username
        val score = intent.getIntExtra(Constants.CORRECT_ANSWER,0)
        tv_finalScore.text = "$score"

        tv_finalScore.text = "Your total score is $score out of 10"

        btn_result_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }





    }
}