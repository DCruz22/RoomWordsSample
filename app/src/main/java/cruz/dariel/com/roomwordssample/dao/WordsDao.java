package cruz.dariel.com.roomwordssample.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cruz.dariel.com.roomwordssample.model.Word;

@Dao
public interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("Delete from word_table")
    void deleteAll();

    @Delete
    void deleteWord(Word word);

    @Query("Select * from word_table Order By word ASC")
    LiveData<List<Word>> getAllWords();

    @Query("Select * from word_table LIMIT 1")
    Word[] getAnyWord();

    @Update
    void update(Word... words);
}
