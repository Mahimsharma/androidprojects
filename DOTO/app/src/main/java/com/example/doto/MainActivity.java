package com.example.doto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doto.Adapter.ToDoAdapter;
import com.example.doto.Model.ToDoModel;
import com.example.doto.Utils.DBHandler;
import com.example.doto.Utils.LogInHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ToDoAdapter cardAdapter;
    private List<ToDoModel> cardList;
    boolean flag;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Tasks");
        dbHandler =  DBHandler.getInstance(this);
        dbHandler.openDataBase();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        cardAdapter = new ToDoAdapter(MainActivity.this);
        recyclerView.setAdapter(cardAdapter);
        flag=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag=true;
        cardList = dbHandler.getAllCards();
        Collections.reverse(cardList);
        cardAdapter.setCards(cardList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logOut && flag)
        {
            LogInHandler logInHandler = LogInHandler.getInstance(this);
            logInHandler.logOut();
            dbHandler.dropTable();
            startActivity(new Intent(this,Signn.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if(flag) {
            flag=false;
            Intent intent = new Intent(MainActivity.this, CardView.class);
            if (v instanceof FloatingActionButton) {

            } else {
                ToDoModel item = cardList.get(recyclerView.getChildLayoutPosition(v));
                intent.putExtra("Title", item.getTitle());
                intent.putExtra("Description", item.getDescription());
                intent.putExtra("Index", item.getId());
            }
            startActivity(intent);
        }
    }

    public void deleteCard(View v) {
        if(flag)
        { flag=false;
            int position = recyclerView.getChildLayoutPosition(v);
            ToDoModel card = cardList.get(position);
            dbHandler.deleteCard(card.getId());
            cardList.remove(position);
            Toast.makeText(this,"Note Deleted!",Toast.LENGTH_SHORT).show();
            cardAdapter.notifyItemRemoved(position);
            cardAdapter.notifyItemRangeChanged(position,cardList.size());
         }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flag=true;
            }
        },400);

    }

}
