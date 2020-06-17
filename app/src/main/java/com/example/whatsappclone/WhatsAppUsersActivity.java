package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppUsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> waUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_users);

        setTitle("Users");

        final ListView listView = findViewById(R.id.listViwe);
        listView.setOnItemClickListener(this);
        waUsers = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, waUsers);

        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swipeContainer);


        try {

            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {

                        for (ParseUser user : objects) {

                            waUsers.add(user.getUsername());

                        }
                        listView.setAdapter(adapter);

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    
                    @Override
                    public void onRefresh() {
                        try {

                            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                            parseQuery.whereNotContainedIn("username", waUsers);
                            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> objects, ParseException e) {

                                    if (objects.size() > 0) {


                                        if (e == null) {

                                            for (ParseUser user : objects) {

                                                waUsers.add(user.getUsername());

                                            }
                                            adapter.notifyDataSetChanged();
                                            if (mySwipeRefreshLayout.isRefreshing()) {
                                                mySwipeRefreshLayout.setRefreshing(false);
                                            }
                                        }

                                    } else {
                                        if (mySwipeRefreshLayout.isRefreshing()) {
                                            mySwipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.logout_item:
                FancyToast.makeText(WhatsAppUsersActivity.this, ParseUser.getCurrentUser().getUsername() + " is logged out!", Toast.LENGTH_SHORT, FancyToast.DEFAULT, true).show();
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(WhatsAppUsersActivity.this, SignUp.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(WhatsAppUsersActivity.this, WhatsAppChatActivity.class);
        intent.putExtra("selectedUser", waUsers.get(position));
        startActivity(intent);
    }
}
