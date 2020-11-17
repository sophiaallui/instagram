package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.Post;
import com.example.instagram.PostsAdapter;
import com.example.instagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PostFragment extends Fragment {

    public static final String TAG = "PostFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        // Link to the PostsAdapter which is in charge of retrieving the data
        adapter = new PostsAdapter(getContext(), allPosts);
        // STEPS TO USE THE RECYCLER VIEW:
        //0: create layout for one row in the list
        //1: create the adapter: how to take the data
        //2: create the data source: get from parse backend
        //3: set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        //4: set the layout manager on the recycler view
        // default: provide cycler view layout manager
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
         //
        // Update data source that new data is fetched
        queryPosts();
    }

    // Changed private to protected, so it could be used in profile fragment
    // TAKE THE POST AND HAVE IT IN RECYCLER VIEW
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        // Chronological Order: new post = first older posts = last
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        // POWER OF PARSE API: SET LIMITS TO POST
        query.setLimit(20);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "post: " + post.getDescription() + ", Username: " + post.getUser());
                }
                allPosts.addAll(posts);
                Log.i("NUM OF POST", ": " + allPosts.size());
                adapter.notifyDataSetChanged();
            }

        });
    }
}