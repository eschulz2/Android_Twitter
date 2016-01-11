/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

  ArrayList<String> scores;
  ArrayList<String> materials;
  ArrayAdapter arrayAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    scores = new ArrayList<String>();
    materials = new ArrayList<String>();
    arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, scores);


    final ListView userList = (ListView) findViewById(R.id.userList);

    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(getApplicationContext(), PostFeed.class);
        i.putExtra("content", materials.get(position));
        startActivity(i);

      }
    });

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");

    query.addDescendingOrder("createdAt");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          if (objects.size() > 0) {

            for (ParseObject score : objects) {
              scores.add(score.getString("text"));
              materials.add(score.getString("content"));
            }


            userList.setAdapter(arrayAdapter);
          }
        }
      }
    });




//    query.whereEqualTo("username", "Kirsten");
//    query.setLimit(1);
//
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        if (e == null) {
//          Log.i("findInBackground", "Retrieved " + objects.size() + " results" );
//          for (ParseObject object : objects) {
//            Log.i("findInBackgroundUser", String.valueOf(object.get("score")));
//          }
//        }
//      }
//    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
