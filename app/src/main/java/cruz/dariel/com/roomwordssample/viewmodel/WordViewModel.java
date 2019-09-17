package cruz.dariel.com.roomwordssample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cruz.dariel.com.roomwordssample.model.Word;
import cruz.dariel.com.roomwordssample.repositories.WordRepository;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mWordRepository;

    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository(application);
        mAllWords = mWordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        mWordRepository.insert(word);
    }

    public void delete(Word word){
        mWordRepository.delete(word);
    }

    public void deleteAll(){
        mWordRepository.deleteAll();
    }
}
