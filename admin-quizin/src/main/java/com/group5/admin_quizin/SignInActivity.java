package com.group5.admin_quizin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.signInBtn);

        loadingDialog = new Dialog(SignInActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().isEmpty()) {
                    email.setError("Enter Email ID");
                    return;
                }
                else
                {
                    email.setError(null);
                }

                if(pass.getText().toString().isEmpty()) {
                    pass.setError("Enter Password");
                    return;
                }
                else
                {
                    pass.setError(null);
                }

                firebaseLogin();

            }
        });

//        If user is already logged in then skip the sign in page
//        if(firebaseAuth.getCurrentUser() != null)
//        {
//            Intent intent = new Intent(SignInActivity.this,QuestionListActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
    private void firebaseLogin()
    {
        loadingDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, take to the next activity
                    Toast.makeText(SignInActivity.this,"Sign In Successful!!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this,QuestionListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignInActivity.this,"Sign In Failed!!",Toast.LENGTH_SHORT).show();
                }

                loadingDialog.dismiss();
            }
        });

    }
}