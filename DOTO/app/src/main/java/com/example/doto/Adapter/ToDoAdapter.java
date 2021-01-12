package com.example.doto.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doto.CardView;
import com.example.doto.MainActivity;
import com.example.doto.Model.ToDoModel;
import com.example.doto.R;
import com.example.doto.Utils.DBHandler;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> cardList;
    private MainActivity activity;
    private ImageButton imageButton;

    public ToDoAdapter(MainActivity activity)
    {
        this.activity = activity;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_layout,parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onClick(v);
            }
        });
        imageButton = itemView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.deleteCard(itemView);
            }
        });


        return new ViewHolder(
                itemView);
    }
    public void onBindViewHolder(ViewHolder holder, int position){

        ToDoModel item = cardList.get(position);
        holder.cardTitle.setText(item.getTitle());
        holder.cardDescription.setText(item.getDescription());

    }
    public int getItemCount()
    {
        return cardList.size();
    }

    public void setCards(List<ToDoModel> cardList){
        this.cardList =  cardList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cardTitle,cardDescription;

        ViewHolder(View view)
        {
            super(view);
            cardTitle = view.findViewById(R.id.cardTitle);
            cardDescription = view.findViewById(R.id.cardDescription);
        }
    }
}
