package es.manguca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import es.manguca.Person;
import es.manguca.R;
import es.manguca.Utils.LetterImageView;

public class AssistantAdapter extends RecyclerView.Adapter<AssistantAdapter.ViewHolder> {

    private ArrayList<Person> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public AssistantAdapter(Context context, ArrayList<Person> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_assistants_single_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person p = mData.get(position);
        holder.name.setText(p.getName() + " " + p.getLastname());
        holder.ivLogo.setOval(true);
        holder.ivLogo.setLetter(p.getName().charAt(0));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        LetterImageView ivLogo;
        Button delete_button;
        Button edit_button;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.NameAssistant);
            ivLogo = itemView.findViewById(R.id.iconAssistants);
            delete_button = itemView.findViewById(R.id.button_delete);
            edit_button = itemView.findViewById(R.id.button_edit);

            itemView.setOnClickListener(this);
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mClickListener.onDeleteClick(v, position);
                        }
                    }
                }
            });

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mClickListener.onEditClick(v, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Person getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onDeleteClick(View view, int position);
        void onEditClick(View view, int position);
    }
}