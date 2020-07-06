package com.example.tumplatform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.ViewHolder> {

    private ArrayList<author> authors = new ArrayList<>();
    private ArrayList<Posts> posts = new ArrayList<>();
    private Context context;


    public profileAdapter(Context context, ArrayList<author> authors) {
        this.context = context;
        this.authors = authors;
    }

    @NonNull
    @Override
    public profileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item,viewGroup,false);
        return new profileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull profileAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.username.setText(authors.get(i).getUsername());
        viewHolder.mail.setText(authors.get(i).getEmail());
        Picasso.get().load("https://infinite-anchorage-45437.herokuapp.com/static/profile_pics/" + authors.get(i).getImage_file()).into(viewHolder.car_image);
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView car_image;
        private TextView username, mail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image = (ImageView)itemView.findViewById(R.id.user_image);
            username = (TextView)itemView.findViewById(R.id.username);
            mail = (TextView)itemView.findViewById(R.id.mail);
        }
    }

    private String formatString(String stringToFormat, int STR_MAX_CHAR_COUNT) {
        if(stringToFormat.length() > STR_MAX_CHAR_COUNT){
            stringToFormat = stringToFormat.substring(0, STR_MAX_CHAR_COUNT - 1) + "...";
        }
        return stringToFormat;
    }
}
