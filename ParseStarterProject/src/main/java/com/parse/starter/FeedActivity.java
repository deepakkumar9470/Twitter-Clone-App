package com.parse.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setTitle("Your Feed");
       final ListView feedListView=findViewById(R.id.feed_listview);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByAscending("createdAt");
        query.setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null)
                {
                    if (objects.size()>0)
                    {
                        List<Map<String,String>> tweetData=new ArrayList<Map<String, String>>();
                        for (ParseObject tweet:objects)
                        {
                            for (int i=1;i<=5;i++)
                            {
                                Map<String,String> tweetInfo=new HashMap<String, String>();
                                tweetInfo.put("content",tweet.getString("tweet"));
                                tweetInfo.put("username",tweet.getString("username"));
                                tweetData.add(tweetInfo);
                            }
                            SimpleAdapter simpleAdapter=new SimpleAdapter(FeedActivity.this,tweetData,android.R.layout.simple_list_item_2,new String[]{"content","username"},new int[]{android.R.id.text1,android.R.id.text2});
                            feedListView.setAdapter(simpleAdapter);

                        }
                    }
                }

            }
        });




    }
}
