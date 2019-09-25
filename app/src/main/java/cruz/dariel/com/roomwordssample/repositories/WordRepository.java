package cruz.dariel.com.roomwordssample.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cruz.dariel.com.roomwordssample.dao.WordRoomDatabase;
import cruz.dariel.com.roomwordssample.dao.WordsDao;
import cruz.dariel.com.roomwordssample.model.Word;

public class WordRepository {

    private WordsDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application.getApplicationContext());
        mWordDao = db.wordsDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        new InsertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(mWordDao).execute();
    }

    public void delete(Word word){
        new DeleteAsyncTask(mWordDao).execute(word);
    }

    public void update(Word word){
        new UpdateAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordsDao mAsyncTaskDao;

        public InsertAsyncTask(WordsDao wordDao) {
            this.mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private WordsDao mAsyncTaskDao;

        public DeleteAllAsyncTask(WordsDao wordDao) {
            this.mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordsDao mAsyncTaskDao;

        public DeleteAsyncTask(WordsDao wordDao) {
            this.mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... items) {
            mAsyncTaskDao.deleteWord(items[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordsDao mAsyncTaskDao;

        public UpdateAsyncTask(WordsDao wordDao) {
            this.mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... items) {
            mAsyncTaskDao.update(items[0]);
            return null;
        }
    }
}
