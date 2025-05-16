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
import com.cmu.quizit.model.option.MainOption;
import com.cmu.quizit.model.option.Option;

import java.util.ArrayList;
import java.util.List;

public class CategoryOptionAdapter extends RecyclerView.Adapter<CategoryOptionAdapter.ViewHolder> {

    private List<Option> optionList;
    private Context context;
    private OnOptionClickListener listener;

    public interface OnOptionClickListener {
        void onOptionClick(Option option);
    }

    public CategoryOptionAdapter(Context context, List<? extends Option> optionList, OnOptionClickListener listener) {
        this.context = context;
        this.optionList = (List<Option>) optionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_option, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Option option = optionList.get(position);
        holder.option_title.setText(option.getTitle());
        holder.option_icn.setImageResource(option.getIconResourceID(context));

        holder.itemView.setOnClickListener(v -> listener.onOptionClick(option));
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView option_title;
        ImageView option_icn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            option_title = itemView.findViewById(R.id.option_title);
            option_icn = itemView.findViewById(R.id.option_icn);
        }
    }
}

