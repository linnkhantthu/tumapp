package com.example.tumplatform;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolder> {

    private ArrayList<Comments> comments = new ArrayList<>();
    private ArrayList<Posts> posts = new ArrayList<>();
    private Context context;

    public commentsAdapter(Context context, ArrayList<Posts> posts, ArrayList<Comments> comments) {
        this.comments = comments;
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public commentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comments_list_item,viewGroup,false);
        return new commentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final commentsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.username.setText(comments.get(i).getAuthor().getUsername());
        try {
            viewHolder.date.setText(comments.get(i).getDate_posted());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.content.setText(comments.get(i).getContent());
        Picasso.get().load("https://infinite-anchorage-45437.herokuapp.com/static/profile_pics/" + comments.get(i).getAuthor().getImage_file()).into(viewHolder.car_image);

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context,"Username clicked: " + comments.get(i).getAuthor().getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Profile.class);
                String post_id = "" + comments.get(i).getAuthor().getId();
                intent.putExtra("user_id", post_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView car_image;
        private TextView content, username, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image = (ImageView)itemView.findViewById(R.id.user_image);
            content = (TextView)itemView.findViewById(R.id.content);
            username = (TextView)itemView.findViewById(R.id.username);
            date = (TextView)itemView.findViewById(R.id.date);
        }
    }

    private String formatString(String stringToFormat, int STR_MAX_CHAR_COUNT) {
        if(stringToFormat.length() > STR_MAX_CHAR_COUNT){
            stringToFormat = stringToFormat.substring(0, STR_MAX_CHAR_COUNT - 1) + "...";
        }
        return stringToFormat;
    }
}
