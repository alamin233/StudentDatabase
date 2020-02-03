package com.example.worddemo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase  extends RoomDatabase {

    abstract WordDao wordDao();
    private  static  volatile  WordRoomDatabase INSTANCE;
    private  static  final int NUMBER_OF_THREADS = 4;
    static  final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (WordRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordRoomDatabase.class,"word_database").addCallback(sRoomDatabaseCallback).build();

                }
            }

        }
        return INSTANCE;
    }
private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback()
{
    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db)
    {
        super.onOpen(db);

        databaseWriteExecutor.execute(()->{

            WordDao dao = INSTANCE.wordDao();
            dao.deleteAll();


        });

    }

};
    private static class PopuateDbAsync extends AsyncTask<Void, Void,Void>{
private final WordDao mDao;

private static String[] words = {"amin","pabel","saimun"};
PopuateDbAsync (WordRoomDatabase db){
    mDao = db.wordDao();
}
        @Override
        protected Void doInBackground(final Void... params) {
    if (mDao.getAnyWord().length<1){
        for (int i= 0; i <=words.length - 1; i++){
            Word word = new Word(words[i]);
            mDao.insert(word);
        }
    }
            return null;
        }
    }


}

