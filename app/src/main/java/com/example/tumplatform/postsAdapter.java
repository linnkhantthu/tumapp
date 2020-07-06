package com.example.tumplatform;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

public class postsAdapter extends RecyclerView.Adapter<postsAdapter.ViewHolder> {

    private ArrayList<Posts> posts=new ArrayList<>();
    private ArrayList<Comments> comments = new ArrayList<>();
    private Context context;


    public postsAdapter(Context context, ArrayList<Posts> posts, ArrayList<Comments> comments) {
        this.posts = posts;
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public postsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_list_item,viewGroup,false);
        return new postsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final postsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.username.setText(posts.get(i).getAuthor().getUsername());
        String tag = formatString(posts.get(i).getTag(), 13);
        viewHolder.tag.setText(tag);
        try {
            viewHolder.date.setText(posts.get(i).getDate_posted());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.title.setText(posts.get(i).getTitle());
        viewHolder.content.setText(posts.get(i).getContent());
        int numberOfComments = 0;
        for (int j=0; j<comments.size();j++){
            if (comments.get(j).getPost_id()==posts.get(i).getId()){
                numberOfComments++;
            }
        }
        String numCmt;
        numCmt = numberOfComments + " Comments";
        viewHolder.comment.setText(numCmt);
        Picasso.get().load("https://infinite-anchorage-45437.herokuapp.com/static/profile_pics/" + posts.get(i).getAuthor().getImage_file()).into(viewHolder.car_image);

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context,"Username clicked: " + posts.get(i).getAuthor().getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("user_id", posts.get(i).getUser_id());
                context.startActivity(intent);
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Comment Clicked" + posts.get(i).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intentc = new Intent(context, CommentActivity.class);
                String post_id = "" + posts.get(i).getId();
                intentc.putExtra("post_id", post_id);
                context.startActivity(intentc);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return Math.max(posts.size(), comments.size());
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView car_image;
        private TextView title, content, username, date, comment, tag;
        private LinearLayout username_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image = (ImageView)itemView.findViewById(R.id.user_image);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView)itemView.findViewById(R.id.content);
            username = (TextView)itemView.findViewById(R.id.username);
            date = (TextView)itemView.findViewById(R.id.date);
            comment = (TextView)itemView.findViewById(R.id.comment);
            tag = (TextView)itemView.findViewById(R.id.tag);
            username_layout = (LinearLayout)itemView.findViewById(R.id.username_layout);
        }
    }

    private String formatString(String stringToFormat, int STR_MAX_CHAR_COUNT) {
        if(stringToFormat.length() > STR_MAX_CHAR_COUNT){
            stringToFormat = stringToFormat.substring(0, STR_MAX_CHAR_COUNT - 1) + "...";
        }
        return stringToFormat;
    }
}
