package com.group5.admin_quizin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionDetailsActivity extends AppCompatActivity {

    EditText Question, oA, oB, oC, oD, ans;
    Button addBtn;

    DatabaseReference questionsRef;
    ImageView backBtn;
    ImageView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        Question=findViewById(R.id.question);
        oA=findViewById(R.id.optionA);
        oB=findViewById(R.id.optionB);
        oC=findViewById(R.id.optionC);
        oD=findViewById(R.id.optionD);
        ans=findViewById(R.id.answer);
        addBtn=findViewById(R.id.addQB);

        backBtn = findViewById(R.id.backBtn2);
        exitBtn = findViewById(R.id.exitBtn2);
        backBtn.setClickable(false);
        exitBtn.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionDetailsActivity.this, QuestionListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        questionsRef= FirebaseDatabase.getInstance().getReference().child("Question");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertQuestionData();
                Intent intent = new Intent(QuestionDetailsActivity.this, QuestionListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void insertQuestionData(){
        String questionIn=Question.getText().toString();
        String optionAIn=oA.getText().toString();
        String optionBIn=oB.getText().toString();
        String optionCIn=oC.getText().toString();
        String optionDin=oD.getText().toString();
        String answerIn=ans.getText().toString();

        QuestionModel questionModel = new QuestionModel(questionIn,optionAIn,optionBIn,optionCIn,optionDin,answerIn);

        questionsRef.push().setValue(questionModel);
        Toast.makeText(QuestionDetailsActivity.this, "Added Successfully!!",Toast.LENGTH_SHORT).show();
    }
}