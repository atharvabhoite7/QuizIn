package com.group5.quizin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WonActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    int correct;
    int wrong;
    int totalQuestions;
    LinearLayout btnShare;
    FirebaseUser user;
    DatabaseReference rootReference;
    ImageView backBtn,exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);
        totalQuestions = getIntent().getIntExtra("totalQuestions",0);

        backBtn = findViewById(R.id.ic_back2);
        exitBtn = findViewById(R.id.ic_exit2);
        backBtn.setClickable(false);
        exitBtn.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WonActivity.this, SignInActivity.class);
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

        user= FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String id = user.getUid();
        rootReference= FirebaseDatabase.getInstance().getReference();

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultText = findViewById(R.id.resultText);

        btnShare = findViewById(R.id.btnShare);

        circularProgressBar.setProgress(correct);

        resultText.setText(correct + "/"+totalQuestions);
        resultText.setTextSize(50);

        rootReference.child("Score");

        DataHolder obj =  new DataHolder(email, correct);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference node = db.getReference("Score");
        node.child(id).setValue(obj);


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "I got " + correct + " Out of " + totalQuestions + " You Can Also Try ";
//                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
    }
}