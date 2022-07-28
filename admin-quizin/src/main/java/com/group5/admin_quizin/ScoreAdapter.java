package com.group5.admin_quizin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreAdapter extends FirebaseRecyclerAdapter<ScoreModel, ScoreAdapter.ScoreViewHolder> {

    private Context context;

    public ScoreAdapter(@NonNull FirebaseRecyclerOptions<ScoreModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ScoreViewHolder holder, int i, @NonNull ScoreModel scoreModel) {
        holder.email.setText(scoreModel.getEmail());
        holder.result.setText(String.valueOf(scoreModel.getResult()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Score").child(getRef(i).getKey())
                        .removeValue()               //We can use setValue(null) to achieve the same result
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score, parent, false);
        return new ScoreViewHolder(view);
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView email, result;
        ImageView delete;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.emailNew);
            result = itemView.findViewById(R.id.result);
            delete = itemView.findViewById(R.id.deleteBtn2);
        }
    }
}