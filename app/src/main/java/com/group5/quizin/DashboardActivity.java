package com.group5.quizin;

import static com.group5.quizin.SplashActivity.list;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timerValue = 20;
    RoundedHorizontalProgressBar progressBar;

    List<ModelClass> allQuestionsList;

    ModelClass modelClass;
    int index = 0;

    TextView card_question, optionA, optionB, optionC, optionD;
    CardView cardOA, cardOB, cardOC, cardOD;

    int correctCount = 0;
    int wrongCount = 0;
    int totalCount = 0;

    LinearLayout nextBtn;
    ImageView backBtn,exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Hooks();
        allQuestionsList = list;
        modelClass = list.get(index);
        totalCount = list.size()-1;

        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));

        backBtn = findViewById(R.id.ic_back);
        exitBtn = findViewById(R.id.ic_exit);
        backBtn.setClickable(false);
        exitBtn.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
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
        nextBtn.setClickable(false);
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);

                dialog.findViewById(R.id.btn_tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardActivity.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        }.start();


        setAllData();
    }

    private void setAllData() {
        card_question.setText(modelClass.getQuestion());
        optionA.setText(modelClass.getOptionA());
        optionB.setText(modelClass.getOptionB());
        optionC.setText(modelClass.getOptionC());
        optionD.setText(modelClass.getOptionD());
        timerValue = 20;
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private void Hooks() {
        progressBar = findViewById(R.id.quiz_timer);
        card_question = findViewById(R.id.card_question);
        optionA = findViewById(R.id.card_optionA);
        optionB = findViewById(R.id.card_optionB);
        optionC = findViewById(R.id.card_optionC);
        optionD = findViewById(R.id.card_optionD);

        cardOA = findViewById(R.id.cardA);
        cardOB = findViewById(R.id.cardB);
        cardOC = findViewById(R.id.cardC);
        cardOD = findViewById(R.id.cardD);

        nextBtn = findViewById(R.id.nextBtn);
    }

    public void Correct(CardView cardView) {

        cardView.setBackgroundColor(getResources().getColor(R.color.green));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                if (index < list.size() - 1) {
                    index++;
                    modelClass = list.get(index);
                    resetColor();
                    setAllData();
                    enableButton();
                } else {
                    GameWon();
                }
            }
        });


    }

    public void Wrong(CardView cardOA) {

        cardOA.setBackgroundColor(getResources().getColor(R.color.red));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCount++;
                if (index < list.size() - 1) {
                    index++;
                    modelClass = list.get(index);
                    setAllData();
                    resetColor();
                    enableButton();
                } else {
                    GameWon();
                }
            }
        });
    }

    private void GameWon() {
        Intent intent = new Intent(DashboardActivity.this, WonActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        intent.putExtra("totalQuestions",totalCount);
        startActivity(intent);
        finish();
    }

    public void enableButton() {
        cardOA.setClickable(true);
        cardOB.setClickable(true);
        cardOC.setClickable(true);
        cardOD.setClickable(true);
        nextBtn.setClickable(false);
    }

    public void disableButton() {
        cardOA.setClickable(false);
        cardOB.setClickable(false);
        cardOC.setClickable(false);
        cardOD.setClickable(false);
        nextBtn.setClickable(true);
    }

    public void resetColor() {
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void optionClickA(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if (modelClass.getOptionA().equals(modelClass.getAnswer())) {
            cardOA.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOA);
            } else {
                GameWon();
            }
        } else {
            Wrong(cardOA);
        }
    }

    public void optionClickB(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if (modelClass.getOptionB().equals(modelClass.getAnswer())) {
            cardOB.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOB);
            } else {
                GameWon();
            }
        } else {
            Wrong(cardOB);
        }
    }

    public void optionClickC(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if (modelClass.getOptionC().equals(modelClass.getAnswer())) {
            cardOC.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOC);
            } else {
                GameWon();
            }
        } else {
            Wrong(cardOC);
        }
    }

    public void optionClickD(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if (modelClass.getOptionD().equals(modelClass.getAnswer())) {
            cardOD.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOD);
            } else {
                GameWon();
            }
        } else {
            Wrong(cardOD);
        }
    }
}
