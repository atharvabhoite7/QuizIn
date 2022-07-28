package com.group5.admin_quizin;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class QuestionAdapter extends FirebaseRecyclerAdapter<QuestionModel, QuestionAdapter.QuestionViewHolder> {

    private Context context;

    public QuestionAdapter(@NonNull FirebaseRecyclerOptions<QuestionModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull QuestionViewHolder holder, int i, @NonNull QuestionModel questionModel) {
        holder.question.setText(questionModel.getQuestion());
        holder.optionA.setText(questionModel.getOptionA());
        holder.optionB.setText(questionModel.getOptionB());
        holder.optionC.setText(questionModel.getOptionC());
        holder.optionD.setText(questionModel.getOptionD());
        holder.answer.setText(questionModel.getAnswer());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Question").child(getRef(i).getKey())
                        .removeValue() //We can use setValue(null) to achieve the same result
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.content))
                        .setExpanded(false)
                        .create();

                View holderView = (LinearLayout) dialog.getHolderView();

                EditText question = holderView.findViewById(R.id.questionNew);
                EditText optionA = holderView.findViewById(R.id.oA);
                EditText optionB = holderView.findViewById(R.id.oB);
                EditText optionC = holderView.findViewById(R.id.oC);
                EditText optionD = holderView.findViewById(R.id.oD);
                EditText answer = holderView.findViewById(R.id.answerNew);

                question.setText(questionModel.getQuestion());
                optionA.setText(questionModel.getOptionA());
                optionB.setText(questionModel.getOptionB());
                optionC.setText(questionModel.getOptionC());
                optionD.setText(questionModel.getOptionD());
                answer.setText(questionModel.getAnswer());

                Button update = holderView.findViewById(R.id.update);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("question", question.getText().toString());
                        map.put("optionA", optionA.getText().toString());
                        map.put("optionB", optionB.getText().toString());
                        map.put("optionC", optionC.getText().toString());
                        map.put("optionD", optionD.getText().toString());
                        map.put("answer", answer.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Question").child(getRef(i).getKey())
                                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                Toast.makeText(context, "Updated Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();

            }
        });
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false);
        return new QuestionViewHolder(view);
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView question, optionA, optionB, optionC, optionD, answer;
        ImageView edit, delete;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.questionNew);
            optionA = itemView.findViewById(R.id.oA);
            optionB = itemView.findViewById(R.id.oB);
            optionC = itemView.findViewById(R.id.oC);
            optionD = itemView.findViewById(R.id.oD);
            answer = itemView.findViewById(R.id.answerNew);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}