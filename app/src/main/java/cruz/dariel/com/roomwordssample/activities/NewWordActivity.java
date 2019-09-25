package cruz.dariel.com.roomwordssample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cruz.dariel.com.roomwordssample.R;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    public static final String EXTRA_REPLY_ID =
            "com.example.android.roomwordssample.REPLY_ID";

    private EditText mEditWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.editWord);

        final Bundle extras = getIntent().getExtras();
        final Button button = findViewById(R.id.saveButton);

        if(extras != null){
            button.setText(R.string.edit_button);
            String word = extras.getString(MainActivity.EXTRA_DATA_UPDATE_WORD, "");
            if(!word.isEmpty()){
                mEditWordView.setText(word);
                mEditWordView.setSelection(word.length());
                mEditWordView.requestFocus();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);

                    if(extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)){
                        int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                        if(id != -1){
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
