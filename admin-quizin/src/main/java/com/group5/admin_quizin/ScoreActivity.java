package com.group5.admin_quizin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScoreAdapter adapter;
    ImageView backBtn;
    ImageView exitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        backBtn = findViewById(R.id.backBtn3);
        exitBtn = findViewById(R.id.exitBtn3);
        backBtn.setClickable(false);
        exitBtn.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, QuestionListActivity.class);
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

        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ScoreModel> options =
                new FirebaseRecyclerOptions.Builder<ScoreModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Score"), ScoreModel.class)
                        .build();

        adapter = new ScoreAdapter(options,this);
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