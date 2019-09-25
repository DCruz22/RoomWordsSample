package cruz.dariel.com.roomwordssample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cruz.dariel.com.roomwordssample.R;
import cruz.dariel.com.roomwordssample.model.Word;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Word> mWordList;
    private static ClickListener clickListener;

    public WordsAdapter(@NonNull Context context, ClickListener listener) {
        mInflater = LayoutInflater.from(context);
        clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word item = mWordList.get(position);
        holder.wordItemView.setText(item.getWord());
    }

    @Override
    public int getItemCount() {
        if (mWordList != null)
            return mWordList.size();
        else return 0;
    }

    public void setWords(List<Word> list){
        mWordList = list;
        notifyDataSetChanged();
    }

    public Word getWordAtPosition(int pos){
        return mWordList.get(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.wordTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClickListener(view, getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener{
        void onClickListener(View v, int position);
    }
}
