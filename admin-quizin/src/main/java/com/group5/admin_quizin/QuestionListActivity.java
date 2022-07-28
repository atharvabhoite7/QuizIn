package com.group5.admin_quizin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private Button addQB;
    private Button resultBtn;
    ImageView backBtn;
    ImageView exitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        backBtn = findViewById(R.id.backBtn);
        exitBtn = findViewById(R.id.exitBtn);
        resultBtn = findViewById(R.id.resultBtn);
        backBtn.setClickable(false);
        exitBtn.setClickable(false);
        resultBtn.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, ScoreActivity.class);
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

        addQB = findViewById(R.id.addQuesBtn);
        addQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, QuestionDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<QuestionModel> options =
                new FirebaseRecyclerOptions.Builder<QuestionModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Question"), QuestionModel.class)
                        .build();

        adapter = new QuestionAdapter(options,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}