package cruz.dariel.com.roomwordssample.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import cruz.dariel.com.roomwordssample.R;
import cruz.dariel.com.roomwordssample.adapter.WordsAdapter;
import cruz.dariel.com.roomwordssample.model.Word;
import cruz.dariel.com.roomwordssample.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity implements WordsAdapter.ClickListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private WordViewModel mWordViewModel;
    private WordsAdapter mWordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.wordsRecyclerView);
        mWordsAdapter = new WordsAdapter(this, this);
        rv.setAdapter(mWordsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    Word word = mWordsAdapter.getWordAtPosition(position);
                    Toast.makeText(MainActivity.this, "Deleting " +
                            word.getWord(), Toast.LENGTH_LONG).show();
                    mWordViewModel.delete(word);
                }
            }
        );

        helper.attachToRecyclerView(rv);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                mWordsAdapter.setWords(words);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(i, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        }else if(requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);

            if(id != -1){
                mWordViewModel.update(new Word(id, data.getStringExtra(NewWordActivity.EXTRA_REPLY)));
            }else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View v, int position) {
        Word word = mWordsAdapter.getWordAtPosition(position);
        launchUpdateWordActivity(word);
    }

    private void launchUpdateWordActivity(Word word){
        Intent i = new Intent(MainActivity.this, NewWordActivity.class);
        i.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
        i.putExtra(EXTRA_DATA_ID, word.getId());
        startActivityForResult(i, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}
