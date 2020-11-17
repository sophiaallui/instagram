package com.example.instagram.fragments;

import android.util.Log;

import com.example.instagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

// Hierarchy from PostFragment: will take in the functions from PostFragment
public class ProfileFragment extends PostFragment{

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        // Chronological Order: new post = first older posts = last
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        // POWER OF PARSE API: SET LIMITS TO POST

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
