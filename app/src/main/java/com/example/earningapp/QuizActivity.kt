package com.example.earningapp

import Fragments.withdrawalFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.earningapp.databinding.ActivityQuizBinding
import com.example.earningapp.modelclass.Question
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore


class QuizActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    private var currentQuestion = 0
    private var score =0
    private var currentChance = 0L
    private  lateinit var questionList:ArrayList<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var currentcoins = snapshot.value as Long
                        binding.coinwithdraw.text= currentcoins.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        currentChance = snapshot.value as Long
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })

        questionList = ArrayList<Question>()
        val image = intent.getIntExtra("catogary_img", 0)
        val catText = intent.getStringExtra("QuestionType")
        Firebase.firestore.collection("Questions")
            .document(catText.toString())
            .collection("question2").get().addOnSuccessListener {
                questionData->
                questionList.clear()
                for(data in questionData.documents){
                    val question:Question ?= data.toObject(Question::class.java)
                    questionList.add(question!!)
                }
                if(questionList.size>0) {
                    binding.question.text = questionList[currentQuestion].question
                    binding.option1.text = questionList[currentQuestion].option1
                    binding.option2.text = questionList[currentQuestion].option2
                    binding.option3.text = questionList[currentQuestion].option3
                    binding.option4.text = questionList[currentQuestion].option4
                }
            }

        binding.catogaryImg.setImageResource(image)
        binding.coinswithdraw.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(this.supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.coinwithdraw.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(this.supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.option1.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option4.text.toString())
        }
    }

    private fun nextQuestionAndScoreUpdate(s:String) {
        if(s == this.questionList[currentQuestion].answer){
            score+=10
        }
        currentQuestion++
        if(currentQuestion>=questionList.size){
            if(((score/(questionList.size*10))*100)>=60){
                binding.reward.visibility = View.VISIBLE
                Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid).setValue(currentChance+1)
            }else{
                binding.nothing.visibility = View.VISIBLE
            }
        }else{
            binding.question.text = questionList[currentQuestion].question
            binding.option1.text = questionList[currentQuestion].option1
            binding.option2.text = questionList[currentQuestion].option2
            binding.option3.text = questionList[currentQuestion].option3
            binding.option4.text = questionList[currentQuestion].option4
        }
    }

}