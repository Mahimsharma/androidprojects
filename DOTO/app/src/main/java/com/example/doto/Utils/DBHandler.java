package com.example.doto.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.doto.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

public class DBHandler extends SQLiteOpenHelper {

    private static  final int VERSION =1;
    private static  final String NAME = "listDatabase";
    private static final String CARD_TABLE = "cardsTable";
    private static final String ID = "id";
    private static final String TITLE= "title";
    private static final String DESCRIPTION = "decsription";
    private static final String CREATE_CARD_TABLE = "CREATE TABLE "+ CARD_TABLE + "(" + ID +
                                                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +" TEXT,"+DESCRIPTION +" TEXT)";

    private SQLiteDatabase db;
    private static DBHandler handler_instance;

    public static synchronized DBHandler getInstance(Context context) {
        if (handler_instance == null) {
            handler_instance = new DBHandler(context.getApplicationContext());
        }
        return handler_instance;
    }
    public DBHandler(Context context)
    {
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CARD_TABLE);
        // CREATE TABLE cardsTable(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,decsription TEXT)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE);
            onCreate(db);
    }

    public void openDataBase(){
        db = this.getWritableDatabase();
    }
    public void insertCard(ToDoModel card){
        ContentValues cv = new ContentValues();
        cv.put(TITLE,card.getTitle());
        cv.put(DESCRIPTION,card.getDescription());
        db.insert(CARD_TABLE,null,cv);
    }

    public List<ToDoModel> getAllCards()
    {
        List<ToDoModel> cardList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(CARD_TABLE,null,null,null,null,null,null);
            if(cursor!=null){
                if (cursor.moveToFirst()){
                    do {
                        ToDoModel card = new ToDoModel();
                        card.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        card.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                        card.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                        cardList.add(card);
                    }while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return cardList;
    }
    public void dropTable()
    {
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE);
        onCreate(db);
    }

    public void updateCard(int id,ToDoModel card){
        ContentValues cv = new ContentValues();
        cv.put(TITLE,card.getTitle());
        cv.put(DESCRIPTION,card.getDescription());
        db.update(CARD_TABLE,cv,ID +"=?",new String[]{String.valueOf(id)});
    }

    public void deleteCard(int id){
        db.delete(CARD_TABLE, ID+"=?",new String[]{String.valueOf(id)});
    }
}
