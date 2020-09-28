package com.example.myquiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myquiz.R.drawable.default_background
import com.example.myquiz.R.drawable.selected_background
import com.example.myquiz.R.drawable.wrong_background
import com.example.myquiz.R.drawable.correct_background
import kotlinx.android.synthetic.main.activity_quiz_question.*

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition :Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOption:Int = 0
    private var score:Int = 0
    private var username: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        username = intent.getStringExtra(Constants.USER_NAME)


        mQuestionList = Constants.getQuestions()
        setQuestion()
        tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        btn_submit.setOnClickListener(this)


    }



    private fun setQuestion(){

        val question = mQuestionList!![mCurrentPosition - 1]
        defaultOptionView()
        if (mCurrentPosition == mQuestionList!!.size){
            btn_submit.text = "FINISH"
        }else{
            btn_submit.text = "SUBMIT"
        }




        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max

        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option1.text = question.optionOne
        tv_option2.text = question.optionTwo
        tv_option3.text = question.optionThree
        tv_option4.text = question.optionFour
    }
    private fun defaultOptionView(){
        val options = ArrayList<TextView>()
        options.add(0,tv_option1)
        options.add(1,tv_option2)
        options.add(2,tv_option3)
        options.add(3,tv_option4)

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,default_background)

        }


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option1 ->{
                selectedOptionView(tv_option1, 1)
            }
            R.id.tv_option2 ->{
                selectedOptionView(tv_option2,2)
            }
            R.id.tv_option3->{
                selectedOptionView(tv_option3,3)
            }
            R.id.tv_option4->{
                selectedOptionView(tv_option4,4)
            }
            R.id.btn_submit ->{
                if (mSelectedOption == 0){
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }else ->{
                            val intent = Intent(this,Result::class.java)
                            intent.putExtra(Constants.USER_NAME, username)
                            intent.putExtra(Constants.CORRECT_ANSWER, score)
                            intent.putExtra(Constants.TOTAL_QUESTION, mQuestionList!!.size)
                        startActivity(intent)
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOption){
                        answerView(mSelectedOption, wrong_background)
                    }else{
                        score++
                    }
                    answerView(question.correctAnswer, correct_background)
                    if (mCurrentPosition == mQuestionList!!.size){
                        btn_submit.text = "FINISH"
                    }else{
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOption = 0
                }
            }

        }

    }
    private fun selectedOptionView(tv: TextView, selectedOptionNumber:Int){
        defaultOptionView()
        mSelectedOption = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#7A8089"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,selected_background)
    }
    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                tv_option1.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                tv_option2.background = ContextCompat.getDrawable(this,drawableView)
            }
            3 ->{
                tv_option3.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> tv_option4.background = ContextCompat.getDrawable(this,drawableView)
        }
    }
}