package cruz.dariel.com.roomwordssample.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import cruz.dariel.com.roomwordssample.model.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WordRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WordRoomDatabase.class, "word_table")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract WordsDao wordsDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordsDao mWordDao;
        private String[] words = {"guitar", "piano", "drums"};

        public PopulateDbAsync(WordRoomDatabase instance) {
            mWordDao = instance.wordsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(mWordDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mWordDao.insert(word);
                }
            }
            return null;
        }
    }
}
