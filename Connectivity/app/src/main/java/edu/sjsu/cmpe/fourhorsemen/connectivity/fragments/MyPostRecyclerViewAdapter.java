package edu.sjsu.cmpe.fourhorsemen.connectivity.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sjsu.cmpe.fourhorsemen.connectivity.R;
import edu.sjsu.cmpe.fourhorsemen.connectivity.beans.Post;
import edu.sjsu.cmpe.fourhorsemen.connectivity.fragments.PostFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private final List<Post> posts;
    private final OnListFragmentInteractionListener mListener;

    public MyPostRecyclerViewAdapter(List<Post> posts, OnListFragmentInteractionListener listener) {
        this.posts = posts;
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView userName;
        TextView timestamp;
        TextView postContent;
        ImageView userPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_post);
            userName = (TextView)itemView.findViewById(R.id.user_name);
            timestamp = (TextView)itemView.findViewById(R.id.timestamp);
            postContent = (TextView)itemView.findViewById(R.id.post_content);
            userPhoto = (ImageView)itemView.findViewById(R.id.user_photo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.userName.setText(posts.get(position).getUserScreenName());
        holder.timestamp.setText(posts.get(position).getTimestamp());
        holder.postContent.setText(posts.get(position).getContent());
        byte[] decodedString = Base64.decode(posts.get(position).getUserPhoto(), Base64.DEFAULT);
        holder.userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
