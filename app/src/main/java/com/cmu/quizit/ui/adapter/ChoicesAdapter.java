package com.cmu.quizit.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmu.quizit.R;
import com.cmu.quizit.model.option.Option;
import com.cmu.quizit.model.question.Choice;

import java.util.List;

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ViewHolder> {

    private List<Choice> choiceList;
    private Context context;
    private OnOptionClickListener listener;
    private int selectedPosition = -1;
    private boolean isAnswerChecked = false;

    public interface OnOptionClickListener {
        void onChoiceClick(Choice choice);
    }

    public ChoicesAdapter(Context context, List<Choice> choiceList, OnOptionClickListener listener) {
        this.context = context;
        this.choiceList = choiceList;
        this.listener = listener;
        this.selectedPosition = -1;
        this.isAnswerChecked = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_choice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Choice choice = choiceList.get(position);
        holder.choice_text.setText(choice.getChoice());

        // Reset the background and text color
        holder.itemView.setBackgroundResource(R.drawable.choice_answer_bg);
        holder.choice_text.setTextColor(context.getColor(R.color.primary_bg));

        // If answer is checked, show correct/incorrect feedback
        if (isAnswerChecked) {
            if (choice.isCorrect()) {
                holder.itemView.setBackgroundResource(R.drawable.correct_answer_bg);
                holder.choice_text.setTextColor(context.getColor(android.R.color.white));
            } else if (position == selectedPosition) {
                holder.itemView.setBackgroundResource(R.drawable.incorrect_answer_bg);
                holder.choice_text.setTextColor(context.getColor(android.R.color.white));
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (!isAnswerChecked) {
                selectedPosition = holder.getAdapterPosition();
                isAnswerChecked = true;
                notifyDataSetChanged();
                listener.onChoiceClick(choice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return choiceList.size();
    }

    public void setAnswerChecked(boolean checked) {
        isAnswerChecked = checked;
        notifyDataSetChanged();
    }

    public void resetState() {
        selectedPosition = -1;
        isAnswerChecked = false;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView choice_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            choice_text = itemView.findViewById(R.id.choice_text);
        }
    }
}

