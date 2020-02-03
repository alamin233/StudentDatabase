package com.example.worddemo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class  WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public void update(Word word){
        new updateWordAsyncTask(mWordDao).execute(word);
    }
    public void  deleteAll(){
        new deleteAllWordAsyncTask(mWordDao).execute();
    }
    public void deleteword(Word word){
        new deletewordAsyncTask(mWordDao).execute(word);
    }

    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
    private static class deleteAllWordAsyncTask extends AsyncTask<Void, Void, Void>{
        private WordDao   mAsyncTaskDao;

        deleteAllWordAsyncTask(WordDao dao){
            mAsyncTaskDao=dao;
        }
        @Override
        protected Void  doInBackground(Void... voids){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class updateWordAsyncTask extends AsyncTask<Word, Void,Void>{
        private WordDao  mAsyncTaskDao;

        updateWordAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Word... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
    private static class deletewordAsyncTask extends AsyncTask<Word, Void,Void>{
        private WordDao mAsyncTaskDao;

        deletewordAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }


}
