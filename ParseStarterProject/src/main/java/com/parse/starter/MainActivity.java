/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

  ArrayList<Integer> scores;
  ArrayAdapter arrayAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    scores = new ArrayList<Integer>();
    arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_expandable_list_item_1, scores);

    final ListView userList = (ListView) findViewById(R.id.userList);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");

    query.addAscendingOrder("createdAt");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          if (objects.size() > 0) {

            for (ParseObject score : objects) {
              scores.add(score.getInt("score"));
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
